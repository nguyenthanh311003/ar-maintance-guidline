package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.Model.ModelCreationRequest;
import com.capstone.ar_guideline.dtos.requests.RequestRevision.RequestRevisionRequest;
import com.capstone.ar_guideline.dtos.requests.RequestRevision.RequestRevisionResponse;
import com.capstone.ar_guideline.entities.*;
import com.capstone.ar_guideline.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestRevisionService {

    private final RequestRevisionRepository requestRevisionRepository;
    private final CompanyRequestRepository companyRequestRepository;
    private final UserRepository userRepository;
    private final ModelServiceImpl modelService;
    private final ModelRepository modelRepository;
    private final WalletServiceImpl walletService;
    private final ServicePricerRepository servicePricerRepository;

    @Transactional
    public RequestRevisionResponse create(RequestRevisionRequest request) {

       try {
           CompanyRequest companyRequest = companyRequestRepository.findByRequestId(request.getCompanyRequestId());

           RequestRevision requestRevision = new RequestRevision();
           requestRevision.setCompanyRequest(companyRequest);
           requestRevision.setReason(request.getReason());
           requestRevision.setStatus(ConstStatus.PENDING);


           requestRevision = requestRevisionRepository.save(requestRevision);
           final RequestRevision savedRequestRevision = requestRevision;
           List<RevisionFile> revisionFiles = request.getRevisionFiles().stream()
                   .map(file -> {
                       RevisionFile revisionFile = new RevisionFile();
                       revisionFile.setRequestRevision(savedRequestRevision);
                       revisionFile.setFileName(FileStorageService.storeFile(file));
                       return revisionFile;
                   })
                   .collect(Collectors.toList());


              requestRevision.setRevisionFiles(revisionFiles);
              requestRevision = requestRevisionRepository.save(requestRevision);

           return mapToResponse(requestRevision);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid price proposal format");
        }

    }

    @Transactional(readOnly = true)
    public List<RequestRevisionResponse> getAllByCompanyRequestId(String companyRequestId) {
        List<RequestRevision> revisions = requestRevisionRepository.findAllByCompanyRequestRequestId(companyRequestId);
        return revisions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public RequestRevisionResponse update(String id, RequestRevisionRequest request) {
        RequestRevision requestRevision = requestRevisionRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("RequestRevision not found"));

        switch (request.getStatus()) {

            case ConstStatus.PRICE_PROPOSED :
                requestRevision.setStatus(request.getStatus());
                requestRevision.setPriceProposal(request.getPriceProposal());
                break;
            case ConstStatus.REJECTED:
                User user = userRepository.findUserById(request.getRejectionReason());
                requestRevision.setRejectionReason(request.getRejectionReason());
                requestRevision.setUserReject(user);
                requestRevision.setStatus(request.getStatus());
                break;
            case ConstStatus.DELIVERED:
                requestRevision.setStatus(request.getStatus());
                requestRevision.setModelFile(FileStorageService.storeFile(request.getModelFile()));
                break;

            case ConstStatus.APPROVED:
                requestRevision.setStatus(request.getStatus());
                Model model =  new Model();
                model.setFile(requestRevision.getModelFile());
                model.setStatus(ConstStatus.ACTIVE_STATUS);
                model.setModelType(ModelType.builder().id(requestRevision.getCompanyRequest().getMachineType().getId()).build());
                model.setName(request.getModelName());
                model.setDescription(request.getDescription());
                model.setCompany(requestRevision.getCompanyRequest().getCompany());
                       model.setPosition("0,0,0");
                model.setRotation("0,0,0");
                model.setScale("1");

                modelRepository.save(model);
                CompanyRequest companyRequest =companyRequestRepository.findByRequestId(requestRevision.getCompanyRequest().getRequestId());
                companyRequest.setStatus(ConstStatus.APPROVED);
                companyRequestRepository.save(companyRequest);
                break;
            case ConstStatus.PROCESSING:
                User company = userRepository.findUserByCompanyIdAndAdminRole(
                        requestRevision.getCompanyRequest().getCompany().getId());
              ServicePrice servicePrice =  servicePricerRepository.findByName("Model Request");

                walletService.updateBalance(
                        company.getWallet().getId(),
                        Long.parseLong(requestRevision.getPriceProposal().toString()),
                        false,
                        servicePrice.getId(), company.getId(),
                        null,null);
                requestRevision.setStatus(request.getStatus());
                break;
        }

        requestRevision = requestRevisionRepository.save(requestRevision);

        return mapToResponse(requestRevision);
    }

    @Transactional
    public void delete(String id) {
        RequestRevision requestRevision = requestRevisionRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("RequestRevision not found"));

        requestRevisionRepository.delete(requestRevision);
    }

    private RequestRevisionResponse mapToResponse(RequestRevision requestRevision) {
        return RequestRevisionResponse.builder()
                .id(requestRevision.getId().toString())
                .companyRequestId(requestRevision.getCompanyRequest().getRequestId())
                 .reason(requestRevision.getReason())
                .rejectionReason(requestRevision.getRejectionReason())
                .priceProposal(requestRevision.getPriceProposal())
                .status(requestRevision.getStatus())
                .revisionFiles(requestRevision.getRevisionFiles().stream()
                        .map(RevisionFile::getFileName)
                        .collect(Collectors.toList()))
                .createdDate(requestRevision.getCreatedDate())
                .build();
    }

}