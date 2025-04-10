package com.capstone.ar_guideline.services.impl;

import static com.capstone.ar_guideline.constants.ConstStatus.*;

import com.capstone.ar_guideline.dtos.requests.CompanyRequestCreation.CompanyRequestCreation;
import com.capstone.ar_guideline.dtos.requests.RequestRevision.RequestRevisionRequest;
import com.capstone.ar_guideline.dtos.responses.CompanyRequest.CompanyRequestResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.entities.*;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.CompanyMapper;
import com.capstone.ar_guideline.mappers.CompanyRequestMapper;
import com.capstone.ar_guideline.repositories.CompanyRequestRepository;
import com.capstone.ar_guideline.services.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyRequestService implements ICompanyRequestService {
    private final CompanyRequestRepository companyRequestRepository;
    private final ICompanyService companyService;
    private final IUserService userService;
    private final IMachineTypeService machineTypeService;
    private final IModelService assetModelService;
    private final EmailService emailService;
    private final RequestRevisionService requestRevisionService;
    private final ChatBoxService chatBoxService;

    @Override
    public PagingModel<CompanyRequestResponse> findAllForDesigner(
            int page, int size, String status, String companyName, String designerEmail) {
        try {
            PagingModel<CompanyRequestResponse> pagingModel = new PagingModel<>();
            Pageable pageable = PageRequest.of(page - 1, size);

            Page<CompanyRequest> companyRequests =
                    companyRequestRepository.findAllForDesigner(pageable, status, companyName, designerEmail);

            List<CompanyRequestResponse> companyRequestResponses =
                    companyRequests.getContent().stream()
                            .map(CompanyRequestMapper::fromEntityToResponse)
                            .toList();

            pagingModel.setPage(page);
            pagingModel.setSize(size);
            pagingModel.setTotalItems((int) companyRequests.getTotalElements());
            pagingModel.setTotalPages(companyRequests.getTotalPages());
            pagingModel.setObjectList(companyRequestResponses);
            return pagingModel;
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.COMPANY_REQUEST_FAILED);
        }
    }

    @Override
    public PagingModel<CompanyRequestResponse> findByCompanyId(
            int page, int size, String companyId, String status) {
        try {
            PagingModel<CompanyRequestResponse> pagingModel = new PagingModel<>();
            Pageable pageable = PageRequest.of(page - 1, size);

            Page<CompanyRequest> companyRequests =
                    companyRequestRepository.findByCompanyId(pageable, companyId, status);

            List<CompanyRequestResponse> companyRequestResponses =
                    companyRequests.stream().map(CompanyRequestMapper::fromEntityToResponse).toList();

            pagingModel.setPage(page);
            pagingModel.setSize(size);
            pagingModel.setTotalItems((int) companyRequests.getTotalElements());
            pagingModel.setTotalPages(companyRequests.getTotalPages());
            pagingModel.setObjectList(companyRequestResponses);
            return pagingModel;
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.COMPANY_REQUEST_FAILED);
        }
    }

    @Override
    public List<CompanyRequestResponse> findByDesignerId(String designerId) {
        try {
            return companyRequestRepository.findByDesigner_IdOrderByCreatedAtDesc(designerId).stream()
                    .map(CompanyRequestMapper::fromEntityToResponse)
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.COMPANY_REQUEST_FAILED);
        }
    }

    @Override
    public CompanyRequestResponse findById(String id) {
        try {
            return CompanyRequestMapper.fromEntityToResponse(
                    companyRequestRepository.findByRequestId(id));
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.COMPANY_REQUEST_FAILED);
        }
    }

    @Override
    @Transactional
    public CompanyRequestResponse create(CompanyRequestCreation request) {
        try {
            Company company =
                    CompanyMapper.fromCompanyResponseToEntity(
                            companyService.findById(request.getCompanyId()));
            User requester = userService.findById(request.getRequesterId());
            ModelType modelType = machineTypeService.findById(request.getMachineTypeId());
            CompanyRequest companyRequest = CompanyRequestMapper.fromCreationRequestToEntity(request);

            companyRequest.setCompany(company);
            companyRequest.setMachineType(modelType);
            companyRequest.setDesigner(null);
            companyRequest.setStatus(PENDING);
            companyRequest.setRequester(requester);
            companyRequest = companyRequestRepository.save(companyRequest);

            RequestRevisionRequest requestRevisionRequest =
                    new RequestRevisionRequest();
            requestRevisionRequest.setCompanyRequestId(companyRequest.getRequestId());

            requestRevisionRequest.setRevisionFiles(request.getRequestRevision().getRevisionFiles());
            requestRevisionService.create(requestRevisionRequest);
            chatBoxService.createChatBox(
                    List.of(UUID.fromString(request.getRequesterId())), UUID.fromString(companyRequest.getRequestId()));
            ;
            return CompanyRequestMapper.fromEntityToResponse(companyRequest);
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.COMPANY_REQUEST_FAILED);
        }
    }

    @Override
    public CompanyRequestResponse update(String requestId, CompanyRequestCreation request) {
        try {
            CompanyRequest companyRequest = companyRequestRepository.findByRequestId(requestId);
            if (request.getStatus() != null
                    && !request.getStatus().equalsIgnoreCase(DESIGNER_CANCELLED)
                    && !request.getStatus().equalsIgnoreCase(COMPANY_CANCELLED))
                companyRequest.setStatus(request.getStatus());
            if (request.getDesignerId() != null)
                companyRequest.setDesigner(userService.findById(request.getDesignerId()));

            String modelNeedToDelete = null;
            if (request.getAssetModelId() != null) {
                if (companyRequest.getAssetModel() != null) {
                    modelNeedToDelete = companyRequest.getAssetModel().getId();
                }
                companyRequest.setAssetModel(assetModelService.findById(request.getAssetModelId()));
            }

            if (request.getStatus().equalsIgnoreCase(DRAFTED) && companyRequest.getAssetModel() != null) {
                Model assetModel = companyRequest.getAssetModel();
                assetModel.setStatus(DRAFTED);
                assetModelService.update(assetModel);
            }

            if (request.getStatus().equalsIgnoreCase(PROCESSING)
                    && Objects.nonNull(request.getRequesterId())
                    && Objects.nonNull(companyRequest.getRequester())) {
                emailService.sendCompanyRequestEmail(request.getRequesterId(), companyRequest);
            }

            if (request.getStatus().equalsIgnoreCase(APPROVED)
                    && companyRequest.getAssetModel() != null) {
                Model assetModel = companyRequest.getAssetModel();
                assetModel.setStatus(ACTIVE_STATUS);
                assetModelService.update(assetModel);
            }

            if (request.getStatus().equalsIgnoreCase(COMPANY_CANCELLED)
                    || request.getStatus().equalsIgnoreCase(DESIGNER_CANCELLED)) {
                companyRequest.setCancelledAt(LocalDateTime.now());
            }

            if (request.getStatus().equalsIgnoreCase(DESIGNER_CANCELLED)) {
                emailService.sendDesignerCancelledEmail(
                        companyRequest.getRequester().getEmail(), companyRequest);
                companyRequest.setStatus(PENDING);
            }

            if (request.getStatus().equalsIgnoreCase(COMPANY_CANCELLED)) {
                emailService.sendRequesterCancelledEmail(
                        companyRequest.getDesigner().getEmail(), companyRequest);
                companyRequest.setStatus(CANCEL);
                if (companyRequest.getAssetModel() != null) {
                    modelNeedToDelete = companyRequest.getAssetModel().getId();
                    companyRequest.setAssetModel(null);
                }
            }

            companyRequest = companyRequestRepository.save(companyRequest);

            if ((request.getStatus().equalsIgnoreCase(COMPANY_CANCELLED)
                    || request.getStatus().equalsIgnoreCase(DRAFTED))
                    && modelNeedToDelete != null) {
                assetModelService.delete(modelNeedToDelete);
            }

            return CompanyRequestMapper.fromEntityToResponse(companyRequest);
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.COMPANY_REQUEST_FAILED);
        }
    }

    @Override
    public void delete(Long id) {
    }
}
