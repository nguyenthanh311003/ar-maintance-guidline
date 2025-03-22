package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.Course.CourseCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Instruction.InstructionCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Machine.MachineCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Machine.MachineModifyRequest;
import com.capstone.ar_guideline.dtos.requests.MachineType.MachineTypeCreationRequest;
import com.capstone.ar_guideline.dtos.requests.MachineTypeAttribute.MachineTypeAttributeCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Model.ModelCreationRequest;
import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.dtos.responses.Instruction.InstructionResponse;
import com.capstone.ar_guideline.dtos.responses.InstructionDetail.InstructionDetailResponse;
import com.capstone.ar_guideline.dtos.responses.Machine.MachineResponse;
import com.capstone.ar_guideline.dtos.responses.MachineType.MachineTypeResponse;
import com.capstone.ar_guideline.dtos.responses.MachineTypeAttribute.MachineTypeAttributeResponse;
import com.capstone.ar_guideline.dtos.responses.MachineTypeValue.MachineTypeValueResponse;
import com.capstone.ar_guideline.dtos.responses.Machine_QR.Machine_QRResponse;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.entities.*;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.*;
import com.capstone.ar_guideline.services.*;
import com.capstone.ar_guideline.util.UtilService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ARGuidelineServiceImpl implements IARGuidelineService {
    IInstructionService instructionService;
    IInstructionDetailService instructionDetailService;
    IModelService modelService;
    ICourseService courseService;
    IModelTypeService modelTypeService;
    IMachineService machineService;
    ICompanyService companyService;
    IMachineTypeAttributeService machineTypeAttributeService;
    IMachineTypeValueService machineTypeValueService;
    IMachineTypeService machineTypeService;
    IMachine_QRService machineQrService;
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public InstructionResponse createInstruction(InstructionCreationRequest request) {
        try {
            Course course = courseService.findById(request.getCourseId());
            Instruction newInstruction =
                    InstructionMapper.fromInstructionCreationRequestToEntity(request);
            Integer highestOrderNumber = instructionService.getHighestOrderNumber(course.getId());

            if (Objects.isNull(highestOrderNumber)) {
                newInstruction.setOrderNumber(1);
            } else {
                newInstruction.setOrderNumber(highestOrderNumber + 1);
            }
            newInstruction = instructionService.create(newInstruction);

            if (newInstruction.getId() == null) {
                throw new AppException(ErrorCode.INSTRUCTION_CREATE_FAILED);
            }
            return InstructionMapper.fromEntityToInstructionResponse(newInstruction);
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.INSTRUCTION_CREATE_FAILED);
        }
    }

    @Override
    public PagingModel<InstructionResponse> getInstructionsByCourseId(
            int page, int size, String courseId) {
        try {
            PagingModel<InstructionResponse> pagingModel = new PagingModel<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Instruction> instructions = instructionService.findByCourseIdPaging(pageable, courseId);

            List<InstructionResponse> instructionResponses =
                    instructions.stream()
                            .map(
                                    i -> {
                                        InstructionResponse instructionResponse =
                                                InstructionMapper.fromEntityToInstructionResponse(i);
                                        List<InstructionDetailResponse> instructionDetailResponses =
                                                instructionDetailService.findByInstructionId(i.getId()).stream()
                                                        .map(
                                                                ide ->
                                                                        InstructionDetailResponse.builder()
                                                                                .id(ide.getId())
                                                                                .instructionId(ide.getInstruction().getId())
                                                                                .orderNumber(ide.getOrderNumber())
                                                                                .description(ide.getDescription())
                                                                                .name(ide.getName())
                                                                                .build())
                                                        .toList();
                                        instructionResponse.setInstructionDetailResponse(instructionDetailResponses);
                                        return instructionResponse;
                                    })
                            .toList();
            pagingModel.setPage(page);
            pagingModel.setSize(size);
            pagingModel.setTotalItems((int) instructions.getTotalElements());
            pagingModel.setTotalPages(instructions.getTotalPages());
            pagingModel.setObjectList(instructionResponses);
            return pagingModel;
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.INSTRUCTION_NOT_EXISTED);
        }
    }

    @Override
    public CourseResponse findCourseById(String modelId) {
        try {
            Course course = courseService.findById(modelId);

            return CourseMapper.fromEntityToCourseResponse(course);

        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MODEL_NOT_EXISTED);
        }
    }

    @Override
    public PagingModel<ModelResponse> findModelByCompanyId(
            int page, int size, String companyId, String type, String name, String code) {
        try {
            PagingModel<ModelResponse> pagingModel = new PagingModel<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Model> models = modelService.findByCompanyId(pageable, companyId, type, name, code);
            List<ModelResponse> modelResponses =
                    models.stream()
                            .map(
                                    m -> {
                                        ModelResponse modelResponse =
                                                ModelResponse.builder()
                                                        .id(m.getId())
                                                        .modelCode(m.getModelCode())
                                                        .status(m.getStatus())
                                                        .name(m.getName())
                                                        .description(m.getDescription())
                                                        .imageUrl(m.getImageUrl())
                                                        .version(m.getVersion())
                                                        .scale(m.getScale())
                                                        .isUsed(m.getIsUsed())
                                                        .file(m.getFile())
                                                        .build();

                                        Course courseByModelId = courseService.findByModelId(m.getId());
                                        if (Objects.isNull(courseByModelId)) {
                                            modelResponse.setCourseName("No Course");
                                        } else {
                                            modelResponse.setCourseName(courseByModelId.getTitle());
                                        }

                                        return modelResponse;
                                    })
                            .toList();

            pagingModel.setPage(page);
            pagingModel.setSize(size);
            pagingModel.setTotalItems((int) models.getTotalElements());
            pagingModel.setTotalPages(models.getTotalPages());
            pagingModel.setObjectList(modelResponses);
            return pagingModel;
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MODEL_NOT_EXISTED);
        }
    }

    @Override
    public ModelResponse findModelById(String id) {
        try {
            Model modelById = modelService.findById(id);
            ModelResponse modelResponse = ModelMapper.fromEntityToModelResponse(modelById);
            Course courseByModelId = courseService.findByModelId(modelById.getId());
            if (Objects.isNull(courseByModelId)) {
                modelResponse.setCourseName("No Course");
            } else {
                modelResponse.setCourseName(courseByModelId.getTitle());
            }

            return modelResponse;
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MODEL_NOT_EXISTED);
        }
    }

    @Override
    public Boolean deleteInstructionById(String instructionId) {
        try {
            Boolean isInstructionDeleted = instructionService.delete(instructionId);

            if (isInstructionDeleted) {
                instructionDetailService.deleteByInstructionId(instructionId);
                return true;
            }

            return false;
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.INSTRUCTION_DELETE_FAILED);
        }
    }

    @Override
    public Boolean deleteModelById(String modelId) {
        try {
            Model modelById = modelService.findById(modelId);

            if (Objects.isNull(modelById)) {
                return false;
            }

            modelService.delete(modelById.getId());
            return true;
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MODEL_DELETE_FAILED);
        }
    }

    @Override
    public void deleteCourseById(String courseId) {
        try {
            modelService.updateIsUsedByCourseId(courseId);
            courseService.delete(courseId);
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.COURSE_DELETE_FAILED);
        }
    }

    @Override
    public ModelResponse updateModel(String id, ModelCreationRequest request) {
        try {
            Model modelById = modelService.findById(id);
            modelById.setStatus(request.getStatus());
            modelById.setModelCode(request.getModelCode());
            modelById.setName(request.getName());
            modelById.setDescription(request.getDescription());
            modelById.setScale(request.getScale());

            modelById.setPosition(
                    request.getPosition().stream().map(String::valueOf).collect(Collectors.joining(",")));

            modelById.setRotation(
                    request.getRotation().stream().map(String::valueOf).collect(Collectors.joining(",")));

            if (request.getImageUrl() != null) {
                modelById.setImageUrl(FileStorageService.storeFile(request.getImageUrl()));
            }
            if (request.getFile() != null) {
                modelById.setFile(FileStorageService.storeFile(request.getFile()));
            }
            ModelType modelTypeById = modelTypeService.findById(request.getModelTypeId());

            if (modelById.getId() != null && request.getStatus().equals(ConstStatus.INACTIVE_STATUS)) {
                Course courseByModelId = courseService.findByModelId(modelById.getId());
                if (!Objects.isNull(courseByModelId)) {
                    if (courseByModelId.getStatus().equals(ConstStatus.ACTIVE_STATUS)) {
                        courseService.changeStatusByCourseId(courseByModelId.getId());
                    }
                }
            }
            //            else if (modelById.getId() != null
            //                    && request.getStatus().equals(ConstStatus.ACTIVE_STATUS)) {
            //                Course courseByModelId = courseService.findByModelId(modelById.getId());
            //
            //                if (!Objects.isNull(courseByModelId)) {
            //                    if (courseByModelId.getStatus().equals(ConstStatus.INACTIVE_STATUS)) {
            //                        courseService.changeStatusByCourseId(courseByModelId.getId());
            //                    }
            //                }
            //            }
            if (modelById.getVersion() != null) {
                int currentVersion = Integer.parseInt(modelById.getVersion());
                modelById.setVersion(String.valueOf(currentVersion + 1));
            } else {
                modelById.setVersion("1");
            }

            modelById = modelService.update(modelById);
            return ModelMapper.fromEntityToModelResponse(modelById);
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MODEL_UPDATE_FAILED);
        }
    }

    @Override
    public void changeStatusCourse(String courseId) {
        try {
            Course courseById = courseService.findById(courseId);

            if (courseById.getStatus().equals(ConstStatus.INACTIVE_STATUS)) {
                ModelResponse modelByCourseId = modelService.getByCourseId(courseId);
                Integer numberOfInstructionDetail =
                        instructionDetailService.countInstructionDetailByCourseId(courseById.getId());
                if (Objects.isNull(modelByCourseId)) {
                    throw new AppException(ErrorCode.MODEL_NOT_EXISTED);
                }

                if (Objects.isNull(numberOfInstructionDetail)) {
                    throw new AppException(ErrorCode.INSTRUCTION_DETAIL_COUNT_FAILED);
                }

                if (modelByCourseId.getStatus().equals(ConstStatus.INACTIVE_STATUS)) {
                    throw new AppException(ErrorCode.UPDATE_GUIDELINE_FAIL_MODEL_INACTIVE_STATUS);
                } else if (numberOfInstructionDetail <= 0) {
                    throw new AppException(ErrorCode.UPDATE_GUIDELINE_FAIL_INSTRUCTION_COUNT);
                } else {
                    courseById.setStatus(ConstStatus.ACTIVE_STATUS);
                }

            } else {
                courseById.setStatus(ConstStatus.INACTIVE_STATUS);
            }

            courseService.save(courseById);
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.COURSE_UPDATE_FAILED);
        }
    }

    @Override
    public PagingModel<MachineResponse> getMachinesByCompanyId(int page, int size, String companyId) {
        try {
            PagingModel<MachineResponse> pagingModel = new PagingModel<>();
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Machine> machines = machineService.getMachineByCompanyId(pageable, companyId);

            List<MachineResponse> machineResponses =
                    machines.stream().map(MachineMapper::fromEntityToMachineResponse).toList();

            pagingModel.setPage(page);
            pagingModel.setSize(size);
            pagingModel.setTotalItems((int) machines.getTotalElements());
            pagingModel.setTotalPages(machines.getTotalPages());
            pagingModel.setObjectList(machineResponses);
            return pagingModel;
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MACHINE_NOT_EXISTED);
        }
    }

    @Override
    public MachineResponse createMachine(MachineCreationRequest request) {
        try {
            ModelType modelTypeById = modelTypeService.findById(request.getModelTypeId());

            Company companyById = companyService.findByIdReturnEntity(request.getCompanyId());

            if (Objects.isNull(companyById)) {
                throw new AppException(ErrorCode.COMPANY_NOT_EXISTED);
            }

            Machine newMachine = MachineMapper.fromMachineCreationRequestToEntity(request);
            newMachine.setModelType(modelTypeById);
            newMachine.setCompany(companyById);
            newMachine.setMachineCode(generateMachineCode());

            try {
                String headerJson = objectMapper.writeValueAsString(request.getHeaderRequests());
                newMachine.setHeader(headerJson);
            } catch (Exception e) {
                throw new AppException(ErrorCode.JSON_PROCESSING_ERROR);
            }

            newMachine = machineService.create(newMachine);

            List<MachineTypeValueResponse> machineTypeValueResponses = new ArrayList<>();

//            if (!request.getMachineTypeValueCreationRequest().isEmpty()) {
//                machineTypeValueResponses =
//                        request.getMachineTypeValueCreationRequest().stream()
//                                .map(
//                                        mtr -> {
//                                            MachineTypeAttribute machineTypeAttributeById =
//                                                    machineTypeAttributeService.findById(mtr.getMachineTypeAttributeId());
//
//                                            MachineTypeValue newMachineTypeValue =
//                                                    MachineTypeValueMapper.fromMachineTypeValueCreationRequestToEntity(mtr);
//                                            newMachineTypeValue.setMachineTypeAttribute(machineTypeAttributeById);
//                                            newMachineTypeValue = machineTypeValueService.create(newMachineTypeValue);
//
//                                            return MachineTypeValueMapper.fromEntityToMachineTypeValueResponse(
//                                                    newMachineTypeValue);
//                                        })
//                                .toList();
//            }

            return MachineMapper.fromEntityToMachineResponseForCreate(
                    newMachine, machineTypeValueResponses);
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MACHINE_CREATE_FAILED);
        }
    }

    // Hàm generate machineCode ngẫu nhiên
    private String generateMachineCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }

    @Override
    public MachineTypeAttributeResponse createMachineTypeAttribute(
            MachineTypeAttributeCreationRequest request) {
        try {
            ModelType modelTypeById = modelTypeService.findById(request.getMachineTypeId());

            MachineTypeAttribute newMachineTypeAttribute =
                    MachineTypeAttributeMapper.fromMachineTypeAttributeCreationRequestToEntity(request);
            newMachineTypeAttribute.setModelType(modelTypeById);

            newMachineTypeAttribute = machineTypeAttributeService.create(newMachineTypeAttribute);

            return MachineTypeAttributeMapper.fromEntityToMachineTypeAttributeResponse(
                    newMachineTypeAttribute);
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MACHINE_TYPE_ATTRIBUTE_CREATE_FAILED);
        }
    }

    @Override
    public List<MachineTypeValueResponse> getMachineTypeAttributeByMachineTypeId(
            String machineTypeId, String machineId) {
        try {
            List<MachineTypeAttribute> machineTypeAttributes =
                    machineTypeAttributeService.getByMachineTypeId(machineTypeId);

            return machineTypeAttributes.stream()
                    .map(
                            mvr -> {
                                MachineTypeValueResponse machineTypeValueResponse = new MachineTypeValueResponse();
                                machineTypeValueResponse.setMachineTypeAttributeName(mvr.getAttributeName());
                                machineTypeValueResponse.setMachineTypeAttributeId(mvr.getId());

                                MachineTypeValue machineTypeValueByMachineTypeAttributeId =
                                        machineTypeValueService.findByMachineTypeAttributeIdAndMachineId(
                                                mvr.getId(), machineId);

                                if (Objects.isNull(machineTypeValueByMachineTypeAttributeId)) {
                                    machineTypeValueResponse.setId("N/A");
                                    machineTypeValueResponse.setValueAttribute("N/A");
                                } else {
                                    machineTypeValueResponse.setId(machineTypeValueByMachineTypeAttributeId.getId());
                                    machineTypeValueResponse.setValueAttribute(
                                            machineTypeValueByMachineTypeAttributeId.getValueAttribute());
                                }

                                return machineTypeValueResponse;
                            })
                    .toList();
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MACHINE_TYPE_ATTRIBUTE_NOT_EXISTED);
        }
    }

    @Override
    public MachineResponse getMachineById(String machineId) {
        try {
            Machine machineById = machineService.findById(machineId);
            List<MachineTypeValueResponse> machineTypeValueResponses =
                    getMachineTypeAttributeByMachineTypeId(
                            machineById.getModelType().getId(), machineById.getId());

            List<Machine_QRResponse> machineQrResponses = machineQrService.getByMachineId(machineById.getId())
                    .stream().map(Machine_QRMapper::fromEntityToMachine_QRResponse).toList();

            MachineResponse machineResponse = MachineMapper.fromEntityToMachineResponseForCreate(
                    machineById, machineTypeValueResponses);

            machineResponse.setMachineQrsResponses(machineQrResponses);

            return machineResponse;
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MACHINE_TYPE_ATTRIBUTE_CREATE_FAILED);
        }
    }

    @Override
    public MachineResponse updateMachineById(String machineId, MachineModifyRequest request) {
        try {
            Machine machineById = machineService.findById(machineId);

            machineById.setName(request.getMachineName());
            machineById.setApiUrl(request.getApiUrl());
            machineById.setRequestToken(request.getToken());

            Machine finalMachineById = machineById;
            List<MachineTypeValueResponse> machineTypeValueResponses =
                    request.getMachineTypeValueModifyRequests().stream()
                            .map(
                                    mtr -> {
                                        MachineTypeValueResponse machineTypeValueResponse =
                                                new MachineTypeValueResponse();
                                        MachineTypeAttribute machineTypeAttributeById =
                                                machineTypeAttributeService.findById(mtr.getMachineTypeAttributeId());
                                        if (mtr.getMachineTypeValueId().equals("N/A")) {
                                            MachineTypeValue newMachineTypeValue =
                                                    MachineTypeValueMapper.fromMachineTypeValueModifyRequestToEntity(mtr);
                                            newMachineTypeValue.setMachineTypeAttribute(machineTypeAttributeById);
                                            newMachineTypeValue = machineTypeValueService.create(newMachineTypeValue);
                                            machineTypeValueResponse.setId(newMachineTypeValue.getId());
                                            machineTypeValueResponse.setMachineTypeAttributeId(
                                                    machineTypeAttributeById.getId());
                                            machineTypeValueResponse.setMachineTypeAttributeName(
                                                    machineTypeAttributeById.getAttributeName());
                                            machineTypeValueResponse.setValueAttribute(
                                                    newMachineTypeValue.getValueAttribute());
                                        } else {
                                            MachineTypeValue machineTypeValueById =
                                                    machineTypeValueService.findById(mtr.getMachineTypeValueId());
                                            machineTypeValueById.setValueAttribute(mtr.getValueAttribute());
                                            machineTypeValueById =
                                                    machineTypeValueService.update(
                                                            machineTypeValueById.getId(), machineTypeValueById);
                                            machineTypeValueResponse.setId(machineTypeValueById.getId());
                                            machineTypeValueResponse.setMachineTypeAttributeId(
                                                    machineTypeAttributeById.getId());
                                            machineTypeValueResponse.setMachineTypeAttributeName(
                                                    machineTypeAttributeById.getAttributeName());
                                            machineTypeValueResponse.setValueAttribute(
                                                    machineTypeValueById.getValueAttribute());
                                        }

                                        return machineTypeValueResponse;
                                    })
                            .toList();

            machineById = machineService.update(machineById.getId(), machineById);

            return MachineMapper.fromEntityToMachineResponseForCreate(
                    machineById, machineTypeValueResponses);
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MACHINE_UPDATE_FAILED);
        }
    }

    @Override
    public MachineTypeResponse createMachineType(MachineTypeCreationRequest request) {
        try {
            Company companyById = companyService.findByIdReturnEntity(request.getCompanyId());

            ModelType newMachineType = new ModelType();
            newMachineType.setName(request.getMachineTypeName());
            newMachineType.setCompany(companyById);

            newMachineType = machineTypeService.create(newMachineType);

            ModelType finalNewMachineType = newMachineType;
            List<MachineTypeAttributeResponse> machineTypeAttributeResponses =
                    request.getMachineTypeAttributeCreationRequestList().stream()
                            .map(
                                    mtr -> {
                                        MachineTypeAttribute newMachineTypeAttribute =
                                                MachineTypeAttributeMapper.fromMachineTypeAttributeCreationRequestToEntity(
                                                        mtr);
                                        newMachineTypeAttribute.setModelType(finalNewMachineType);
                                        newMachineTypeAttribute =
                                                machineTypeAttributeService.create(newMachineTypeAttribute);
                                        MachineTypeValue newMachineTypeValue = new MachineTypeValue();
                                        newMachineTypeValue.setValueAttribute(mtr.getAttributeValue());
                                        newMachineTypeValue.setMachineTypeAttribute(newMachineTypeAttribute);
                                        newMachineTypeValue = machineTypeValueService.create(newMachineTypeValue);
                                        MachineTypeAttributeResponse machineTypeAttributeResponse =
                                                MachineTypeAttributeMapper.fromEntityToMachineTypeAttributeResponse(
                                                        newMachineTypeAttribute);

                                        machineTypeAttributeResponse.setValueAttribute(
                                                newMachineTypeValue.getValueAttribute());

                                        return machineTypeAttributeResponse;
                                    })
                            .toList();

            return MachineTypeMapper.fromEntityToMachineTypeResponse(
                    newMachineType, machineTypeAttributeResponses);

        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MODEL_TYPE_CREATE_FAILED);
        }
    }

    @Override
    public PagingModel<MachineTypeResponse> getMachineTypesByCompanyId(
            int page, int size, String companyId) {
        try {
            PagingModel<MachineTypeResponse> pagingModel = new PagingModel<>();
            Pageable pageable = PageRequest.of(page - 1, size);

            Page<ModelType> machineTypes =
                    machineTypeService.getMachineTypeByCompanyId(pageable, companyId);
            List<MachineTypeResponse> machineTypeResponses =
                    machineTypes.getContent().stream()
                            .map(
                                    mt -> {
                                        MachineTypeResponse machineTypeResponse =
                                                MachineTypeResponse.builder()
                                                        .machineTypeId(mt.getId())
                                                        .machineTypeName(mt.getName())
                                                        .build();
                                        Integer numOfAttribute =
                                                machineTypeAttributeService.countNumOfAttributeByMachineTypeId(mt.getId());

                                        if (!Objects.isNull(numOfAttribute)) {
                                            machineTypeResponse.setNumOfAttribute(numOfAttribute);
                                        }
                                        return machineTypeResponse;
                                    })
                            .toList();

            pagingModel.setPage(page);
            pagingModel.setSize(size);
            pagingModel.setTotalItems((int) machineTypes.getTotalElements());
            pagingModel.setTotalPages(machineTypes.getTotalPages());
            pagingModel.setObjectList(machineTypeResponses);
            return pagingModel;
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MODEL_TYPE_NOT_EXISTED);
        }
    }

    @Override
    public MachineTypeResponse getMachineTypesById(String machineTypeId) {
        try {
            ModelType modelTypeById = machineTypeService.findById(machineTypeId);

            List<MachineTypeAttribute> machineTypeAttributesByMachineTypeId =
                    machineTypeAttributeService.getByMachineTypeId(machineTypeId);

            List<MachineTypeAttributeResponse> machineTypeAttributeResponses =
                    machineTypeAttributesByMachineTypeId.stream()
                            .map(
                                    mta -> {
                                        MachineTypeAttributeResponse machineTypeAttributeResponse =
                                                MachineTypeAttributeMapper.fromEntityToMachineTypeAttributeResponse(mta);
                                        MachineTypeValue machineTypeValueByMachineTypeAttributeId =
                                                machineTypeValueService.findByMachineTypeAttributeId(mta.getId());

                                        if (!Objects.isNull(machineTypeValueByMachineTypeAttributeId)) {
                                            machineTypeAttributeResponse.setValueAttribute(
                                                    machineTypeValueByMachineTypeAttributeId.getValueAttribute());
                                        }

                                        return machineTypeAttributeResponse;
                                    })
                            .toList();

            return MachineTypeMapper.fromEntityToMachineTypeResponse(
                    modelTypeById, machineTypeAttributeResponses);
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MODEL_TYPE_NOT_EXISTED);
        }
    }

    @Override
    public MachineTypeResponse updateMachineType(
            String machineTypeId, MachineTypeCreationRequest request) {
        try {
            ModelType modelTypeById = machineTypeService.findById(machineTypeId);

            modelTypeById.setName(request.getMachineTypeName());

            List<MachineTypeAttributeResponse> machineTypeAttributeResponses =
                    request.getMachineTypeAttributeCreationRequestList().stream()
                            .map(
                                    mtr -> {
                                        MachineTypeAttribute machineTypeAttributeById =
                                                machineTypeAttributeService.findByIdNotThrowException(
                                                        mtr.getMachineTypeAttributeId());

                                        if (Objects.isNull(machineTypeAttributeById)) {
                                            MachineTypeAttribute newMachineTypeAttribute = new MachineTypeAttribute();
                                            newMachineTypeAttribute.setAttributeName(mtr.getAttributeName());
                                            newMachineTypeAttribute.setModelType(modelTypeById);
                                            newMachineTypeAttribute =
                                                    machineTypeAttributeService.create(newMachineTypeAttribute);

                                            MachineTypeValue newMachineTypeValue = new MachineTypeValue();
                                            newMachineTypeValue.setMachineTypeAttribute(newMachineTypeAttribute);
                                            newMachineTypeValue.setValueAttribute(mtr.getAttributeValue());

                                            newMachineTypeValue = machineTypeValueService.create(newMachineTypeValue);
                                            return MachineTypeAttributeMapper.fromEntityToMachineTypeAttributeResponse(
                                                    newMachineTypeAttribute, newMachineTypeValue);
                                        } else {
                                            machineTypeAttributeById.setAttributeName(mtr.getAttributeName());
                                            machineTypeAttributeById =
                                                    machineTypeAttributeService.update(
                                                            machineTypeAttributeById.getId(), machineTypeAttributeById);

                                            MachineTypeValue machineTypeValueByMachineTypeAttributeId =
                                                    machineTypeValueService.findByMachineTypeAttributeId(
                                                            mtr.getMachineTypeAttributeId());

                                            if (Objects.isNull(machineTypeValueByMachineTypeAttributeId)) {
                                                MachineTypeValue newMachineTypeValue = new MachineTypeValue();
                                                newMachineTypeValue.setMachineTypeAttribute(machineTypeAttributeById);
                                                newMachineTypeValue.setValueAttribute(mtr.getAttributeValue());

                                                newMachineTypeValue = machineTypeValueService.create(newMachineTypeValue);
                                                return MachineTypeAttributeMapper.fromEntityToMachineTypeAttributeResponse(
                                                        machineTypeAttributeById, newMachineTypeValue);
                                            }

                                            machineTypeValueByMachineTypeAttributeId.setValueAttribute(
                                                    mtr.getAttributeValue());

                                            machineTypeValueByMachineTypeAttributeId =
                                                    machineTypeValueService.update(
                                                            machineTypeValueByMachineTypeAttributeId.getId(),
                                                            machineTypeValueByMachineTypeAttributeId);

                                            return MachineTypeAttributeMapper.fromEntityToMachineTypeAttributeResponse(
                                                    machineTypeAttributeById, machineTypeValueByMachineTypeAttributeId);
                                        }
                                    })
                            .toList();

            return MachineTypeMapper.fromEntityToMachineTypeResponse(
                    modelTypeById, machineTypeAttributeResponses);
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MODEL_TYPE_UPDATE_FAILED);
        }
    }

    @Override
    public void updateQrCodeForMachine(String guidelineCode, Machine machineToUpdate) {
        try {
            if (machineToUpdate == null) {
                throw new AppException(ErrorCode.MACHINE_NOT_EXISTED);
            }

            String qrCode = machineToUpdate.getMachineCode() + " @ " + guidelineCode;

            machineToUpdate.setQrCode(UtilService.generateAndStoreQRCode(qrCode));

            machineService.update(machineToUpdate.getId(), machineToUpdate);
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.MACHINE_UPDATE_FAILED);
        }
    }

    @Override
    public CourseResponse createGuideline(CourseCreationRequest request) {
        try {
            Course newCourse = CourseMapper.fromCourseCreationRequestToEntity(request);
            modelService.findById(request.getModelId());
            companyService.findById(request.getCompanyId());
            ModelType machineTypeById = machineTypeService.findById(request.getMachineTypeId());
            newCourse.setModelType(machineTypeById);
            newCourse.setImageUrl(FileStorageService.storeFile(request.getImageUrl()));
            newCourse.setStatus(ConstStatus.INACTIVE_STATUS);
            newCourse.setCourseCode(UUID.randomUUID().toString());
            newCourse.setDuration(0);
            newCourse.setNumberOfScan(0);
            newCourse.setQrCode(UtilService.generateAndStoreQRCode(newCourse.getCourseCode()));
            newCourse = courseService.save(newCourse);

            List<Machine> machinesByMachineTypeId =
                    machineService.getMachineByMachineType(newCourse.getModelType().getId());

//      if (!machinesByMachineTypeId.isEmpty()) {
//        Course finalNewCourse = newCourse;
//        machinesByMachineTypeId.forEach(
//            machine -> {
//              updateQrCodeForMachine(finalNewCourse.getCourseCode(), machine);
//            });
//      }

            if (!machinesByMachineTypeId.isEmpty()) {
                Course finalNewCourse = newCourse;
                machinesByMachineTypeId.forEach(
                        machine -> {
                            Machine_QR newMachineQr = new Machine_QR();
                            newMachineQr.setGuideline(finalNewCourse);
                            newMachineQr.setMachine(machine);

                            String qrCode = machine.getMachineCode() + " @ " + finalNewCourse.getCourseCode();
                            newMachineQr.setQrUrl(UtilService.generateAndStoreQRCode(qrCode));
                            machineQrService.create(newMachineQr);
                        });
            }

            if (newCourse.getId() != null) {
                Model modelById = modelService.findById(newCourse.getModel().getId());
                modelService.updateIsUsed(true, modelById);
            }
            //      Arrays.stream(keysToRemove)
            //          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
            //          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

            return CourseMapper.fromEntityToCourseResponse(newCourse);
        } catch (Exception exception) {
            if (exception instanceof AppException) {
                throw exception;
            }
            throw new AppException(ErrorCode.COURSE_CREATE_FAILED);
        }
    }
}
