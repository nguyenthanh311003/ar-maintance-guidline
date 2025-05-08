package com.capstone.ar_guideline.services.impl;

import com.capstone.ar_guideline.constants.ConstStatus;
import com.capstone.ar_guideline.dtos.requests.Course.CourseCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Instruction.InstructionCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Machine.MachineCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Machine.MachineModifyRequest;
import com.capstone.ar_guideline.dtos.requests.MachineType.MachineTypeCreationRequest;
import com.capstone.ar_guideline.dtos.requests.MachineTypeAttribute.MachineTypeAttributeCreationRequest;
import com.capstone.ar_guideline.dtos.requests.Model.ModelCreationRequest;
import com.capstone.ar_guideline.dtos.responses.CompanyRequest.CompanyRequestResponse;
import com.capstone.ar_guideline.dtos.responses.Course.CourseResponse;
import com.capstone.ar_guideline.dtos.responses.Instruction.InstructionResponse;
import com.capstone.ar_guideline.dtos.responses.InstructionDetail.InstructionDetailResponse;
import com.capstone.ar_guideline.dtos.responses.Machine.HeaderResponse;
import com.capstone.ar_guideline.dtos.responses.Machine.MachineGuidelineResponse;
import com.capstone.ar_guideline.dtos.responses.Machine.MachineResponse;
import com.capstone.ar_guideline.dtos.responses.MachineType.MachineTypeResponse;
import com.capstone.ar_guideline.dtos.responses.MachineTypeAttribute.MachineTypeAttributeResponse;
import com.capstone.ar_guideline.dtos.responses.MachineTypeValue.MachineTypeValueResponse;
import com.capstone.ar_guideline.dtos.responses.Model.ModelResponse;
import com.capstone.ar_guideline.dtos.responses.PagingModel;
import com.capstone.ar_guideline.entities.*;
import com.capstone.ar_guideline.exceptions.AppException;
import com.capstone.ar_guideline.exceptions.ErrorCode;
import com.capstone.ar_guideline.mappers.*;
import com.capstone.ar_guideline.repositories.CompanyRequestRepository;
import com.capstone.ar_guideline.repositories.CourseRepository;
import com.capstone.ar_guideline.repositories.MachineTypeRepository;
import com.capstone.ar_guideline.services.*;
import com.capstone.ar_guideline.util.UtilService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
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
  IMachineTypeService machineTypeService;
  CompanyRequestRepository companyRequestRepository;
  FirebaseNotificationServiceImpl firebaseNotificationService;
  ObjectMapper objectMapper = new ObjectMapper();
  MachineTypeRepository machineTypeRepository;
  CourseRepository courseRepository;

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
      course.setStatus(ConstStatus.INACTIVE_STATUS);
      courseService.save(course);
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
      int page,
      int size,
      String companyId,
      String type,
      String name,
      String code,
      String machineTypeId) {
    try {
      PagingModel<ModelResponse> pagingModel = new PagingModel<>();
      Pageable pageable = PageRequest.of(page - 1, size);
      Page<Model> models =
          modelService.findByCompanyId(pageable, companyId, type, name, code, machineTypeId);
      List<ModelResponse> modelResponses =
          models.stream()
              .map(
                  m -> {
                    ModelResponse modelResponse =
                        ModelResponse.builder()
                            .id(m.getId())
                            .modelCode(m.getModelCode())
                            .modelTypeId(m.getModelType().getId())
                            .modelTypeName(m.getModelType().getName())
                            .status(m.getStatus())
                            .name(m.getName())
                            .description(m.getDescription())
                            .imageUrl(m.getImageUrl())
                            .version(m.getVersion())
                            .scale(m.getScale())
                            .isUsed(m.getIsUsed())
                            .file(m.getFile())
                            .build();
                    List<Course> courseByModelId = new ArrayList<>();
                    try {
                      courseByModelId = courseService.findByModelId(m.getId(), companyId);
                      if (courseByModelId.isEmpty()) {
                        modelResponse.setCourseName("No Course");
                      } else {
                        String courseNames =
                            courseByModelId.stream()
                                .map(Course::getTitle)
                                .collect(Collectors.joining(", "));
                        modelResponse.setCourseName(courseNames);
                      }
                    } catch (Exception exception) {
                      modelResponse.setCourseName("No Course");
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

      List<Course> courseByModelId;
      try {
        courseByModelId = courseService.findByModelIdReturnList(modelById.getId());
        if (courseByModelId.isEmpty()) {
          modelResponse.setCourseName("No Course");
        } else {
          String courseNames =
              courseByModelId.stream().map(Course::getTitle).collect(Collectors.joining(", "));
          modelResponse.setCourseName(courseNames);
        }
      } catch (Exception exception) {
        modelResponse.setCourseName("No Course");
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
      Instruction instruction  = instructionService.findById(instructionId);
      Course courseByInstructionId =
              courseService.findById(instruction.getCourse().getId());

      Boolean isInstructionDeleted = instructionService.delete(instructionId);

     courseByInstructionId.setStatus(ConstStatus.INACTIVE_STATUS);
     courseRepository.save(courseByInstructionId);
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
      //      ModelType modelTypeById = modelTypeService.findById(request.getModelTypeId());

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

          courseById.setStatus(ConstStatus.ACTIVE_STATUS);
          // Get company ID from the course
          String companyId = courseById.getCompany().getId();

          // Send notification about new course
          String topic = "company_" + companyId;
          String title = "New Course Available";
          String body = "A new course '" + courseById.getTitle() + "' is now available";

          // Format data payload for the notification
          String data =
              "type:new_course,courseId:"
                  + courseById.getId()
                  + ",courseName:"
                  + courseById.getTitle();

          try {
            // Send notification to company topic
            firebaseNotificationService.sendNotificationToTopic(topic, title, body, data);
            log.info("Successfully sent notification for new course: {}", courseById.getTitle());
          } catch (Exception e) {
            // Log but don't fail the course activation if notification fails
            log.error("Failed to send course activation notification", e);
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
  public PagingModel<MachineResponse> getMachinesByCompanyId(
      int page, int size, String companyId, String keyword, String machineTypeName) {
    try {
      PagingModel<MachineResponse> pagingModel = new PagingModel<>();
      Pageable pageable = PageRequest.of(page - 1, size);
      Page<Machine> machines =
          machineService.getMachineByCompanyId(pageable, companyId, keyword, machineTypeName);

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

      Boolean isMachineCodeExisted =
          machineService.isMachineCodeExisted(companyById.getId(), request.getMachineCode());

      if (isMachineCodeExisted) {
        throw new AppException(ErrorCode.MACHINE_CODE_EXISTED);
      }

      newMachine.setMachineCode(request.getMachineCode());

      try {
        String headerJson = objectMapper.writeValueAsString(request.getHeaderRequests());
        newMachine.setHeader(headerJson);
      } catch (Exception e) {
        throw new AppException(ErrorCode.JSON_PROCESSING_ERROR);
      }

      String qrCode = newMachine.getMachineCode();
      newMachine.setQrCode(UtilService.generateAndStoreQRCode(qrCode));

      newMachine = machineService.create(newMachine);

      List<MachineTypeValueResponse> machineTypeValueResponses = new ArrayList<>();

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

                if (mvr.getValueOfAttribute() == null || mvr.getValueOfAttribute().isEmpty()) {
                  machineTypeValueResponse.setValueAttribute("N/A");
                } else {
                  machineTypeValueResponse.setValueAttribute(mvr.getValueOfAttribute());
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

      MachineResponse machineResponse =
          MachineMapper.fromEntityToMachineResponseForCreate(
              machineById, machineTypeValueResponses);

      List<HeaderResponse> headerResponses = new ArrayList<>();
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        if (machineById.getHeader() != null && !machineById.getHeader().isEmpty()) {
          headerResponses =
              objectMapper.readValue(
                  machineById.getHeader(), new TypeReference<List<HeaderResponse>>() {});
        }
      } catch (JsonProcessingException e) {
        throw new AppException(ErrorCode.JSON_PROCESSING_ERROR);
      }

      machineResponse.setHeaderResponses(headerResponses);

      return machineResponse;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MACHINE_TYPE_ATTRIBUTE_CREATE_FAILED);
    }
  }

  @Override
  public MachineResponse getMachineByCode(String machineCode, String companyId) {
    try {
      Machine machineByMachineCode = machineService.findByCodeAndCompanyId(machineCode, companyId);
      List<MachineTypeValueResponse> machineTypeValueResponses =
          getMachineTypeAttributeByMachineTypeId(
              machineByMachineCode.getModelType().getId(), machineByMachineCode.getId());

      MachineResponse machineResponse =
          MachineMapper.fromEntityToMachineResponseForCreate(
              machineByMachineCode, machineTypeValueResponses);

      List<HeaderResponse> headerResponses = new ArrayList<>();
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        if (machineByMachineCode.getHeader() != null
            && !machineByMachineCode.getHeader().isEmpty()) {
          headerResponses =
              objectMapper.readValue(
                  machineByMachineCode.getHeader(), new TypeReference<List<HeaderResponse>>() {});
        }
      } catch (JsonProcessingException e) {
        throw new AppException(ErrorCode.JSON_PROCESSING_ERROR);
      }

      machineResponse.setHeaderResponses(headerResponses);
      return machineResponse;
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MACHINE_TYPE_ATTRIBUTE_CREATE_FAILED);
    }
  }

  @Override
  public MachineResponse updateMachineById(
      String machineId, MachineModifyRequest request, String companyId) {
    try {
      Machine machineById = machineService.findById(machineId);

      Company companyById = companyService.findByIdReturnEntity(companyId);

      machineById.setName(request.getMachineName());
      machineById.setApiUrl(request.getApiUrl());
      machineById.setRequestToken(request.getToken());

      ModelType machineTypeById = machineTypeService.findById(request.getMachineTypeId());

      machineById.setModelType(machineTypeById);

      Boolean isMachineCodeExisted =
          machineService.isMachineCodeExistedForUpdate(
              companyById.getId(), request.getMachineCode(), machineById.getMachineCode());

      if (isMachineCodeExisted) {
        throw new AppException(ErrorCode.MACHINE_CODE_EXISTED);
      }

      machineById.setMachineCode(request.getMachineCode());

      try {
        String headerJson = objectMapper.writeValueAsString(request.getHeaderRequests());
        machineById.setHeader(headerJson);
      } catch (Exception e) {
        throw new AppException(ErrorCode.JSON_PROCESSING_ERROR);
      }

      String qrCode = machineById.getMachineCode();
      machineById.setQrCode(UtilService.generateAndStoreQRCode(qrCode));

      machineById = machineService.update(machineById.getId(), machineById);

      return MachineMapper.fromEntityToMachineResponseForCreate(machineById);
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

      ModelType machineType = machineTypeRepository.findByNameAndCompanyId(request.getMachineTypeName(), companyById.getId());
      if (machineType != null) {
        throw new AppException(ErrorCode.MACHINE_TYPE_NAME_EXISTED);
      }
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
                    return MachineTypeAttributeMapper.fromEntityToMachineTypeAttributeResponse(
                        newMachineTypeAttribute);
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
      int page, int size, String companyId, String name) {
    try {
      PagingModel<MachineTypeResponse> pagingModel = new PagingModel<>();
      Pageable pageable = PageRequest.of(page - 1, size);

      Page<ModelType> machineTypes =
          machineTypeService.getMachineTypeByCompanyId(pageable, companyId, name);
      List<MachineTypeResponse> machineTypeResponses =
          machineTypes.getContent().stream()
              .map(
                  mt -> {
                    MachineTypeResponse machineTypeResponse =
                        MachineTypeResponse.builder()
                            .machineTypeId(mt.getId())
                            .machineTypeName(mt.getName())
                            .build();

                    Integer numOfMachine = machineService.countMachineByMachineType(mt.getId());
                    Integer numOfAttribute =
                        machineTypeAttributeService.countNumOfAttributeByMachineTypeId(mt.getId());
                    Integer numOfMachineUsing = machineService.countMachineByCompanyId(companyId);

                    if (!Objects.isNull(numOfAttribute)) {
                      machineTypeResponse.setNumOfAttribute(numOfAttribute);
                    }

                    if (!Objects.isNull(numOfMachine)) {
                      machineTypeResponse.setNumOfMachine(numOfMachine);
                    }

                    if (!Objects.isNull(numOfMachineUsing)) {
                      machineTypeResponse.setNumOfMachineUsing(numOfMachineUsing);
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
              .map(MachineTypeAttributeMapper::fromEntityToMachineTypeAttributeResponse)
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
                      newMachineTypeAttribute.setValueOfAttribute(mtr.getAttributeValue());
                      newMachineTypeAttribute =
                          machineTypeAttributeService.create(newMachineTypeAttribute);
                      return MachineTypeAttributeMapper.fromEntityToMachineTypeAttributeResponse(
                          newMachineTypeAttribute);
                    } else {
                      machineTypeAttributeById.setAttributeName(mtr.getAttributeName());
                      machineTypeAttributeById.setValueOfAttribute(mtr.getAttributeValue());
                      machineTypeAttributeById =
                          machineTypeAttributeService.update(
                              machineTypeAttributeById.getId(), machineTypeAttributeById);

                      return MachineTypeAttributeMapper.fromEntityToMachineTypeAttributeResponse(
                          machineTypeAttributeById);
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
      Model model = modelService.findById(request.getModelId());
      companyService.findById(request.getCompanyId());
      newCourse.setModelType(model.getModelType());
      newCourse.setImageUrl(FileStorageService.storeFile(request.getImageUrl()));
      newCourse.setStatus(ConstStatus.DRAFTED);
      newCourse.setCourseCode(UUID.randomUUID().toString());
      newCourse.setDuration(0);
      newCourse.setNumberOfScan(0);
      newCourse.setQrCode(UtilService.generateAndStoreQRCode(newCourse.getCourseCode()));
      newCourse.setModel(model);
      newCourse = courseService.save(newCourse);

      List<Machine> machinesByMachineTypeId =
          machineService.getMachineByMachineType(model.getModelType().getId());

      //      if (!machinesByMachineTypeId.isEmpty()) {
      //        Course finalNewCourse = newCourse;
      //        machinesByMachineTypeId.forEach(
      //            machine -> {
      //              Machine_QR newMachineQr = new Machine_QR();
      //              newMachineQr.setGuideline(finalNewCourse);
      //              newMachineQr.setMachine(machine);
      //
      //              String qrCode = machine.getMachineCode() + " @ " +
      // finalNewCourse.getCourseCode();
      //              newMachineQr.setQrUrl(UtilService.generateAndStoreQRCode(qrCode));
      //              machineQrService.create(newMachineQr);
      //            });
      //      }

      if (newCourse.getId() != null) {
        Model modelById = modelService.findById(newCourse.getModel().getId());
        modelService.updateIsUsed(true, modelById);
      }
      //      Arrays.stream(keysToRemove)
      //          .map(k -> k + ConstHashKey.HASH_KEY_ALL)
      //          .forEach(k -> UtilService.deleteCache(redisTemplate, redisTemplate.keys(k)));

      // Send notification about new course
      String topic = "company_" + request.getCompanyId();
      String title = "New Course Available";
      String body = "A new course '" + newCourse.getTitle() + "' is now available";
      String data =
          "type:new_course,courseId:" + newCourse.getId() + ",courseName:" + newCourse.getTitle();

      try {
        firebaseNotificationService.sendNotificationToTopic(topic, title, body, data);
      } catch (Exception e) {
        // Log but don't fail the course creation if notification fails
        log.error("Failed to send course creation notification", e);
      }

      return CourseMapper.fromEntityToCourseResponse(newCourse);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.COURSE_CREATE_FAILED);
    }
  }

  @Override
  public CompanyRequestResponse uploadAgain(
      String requestId, ModelCreationRequest modelCreationRequest) {
    try {
      CompanyRequest companyRequest = companyRequestRepository.findByRequestId(requestId);

      if (Objects.isNull(companyRequest)) {
        throw new AppException(ErrorCode.COMPANY_REQUEST_NOT_EXISTED);
      }

      Model modelById = modelService.findById(companyRequest.getAssetModel().getId());
      modelService.delete(modelById.getId());

      Model newModel =
          Model.builder()
              .modelType(companyRequest.getAssetModel().getModelType())
              .company(companyRequest.getCompany())
              .file(FileStorageService.storeFile(modelCreationRequest.getFile()))
              .isUsed(false)
              .status(ConstStatus.DRAFTED)
              .position("0,0,0")
              .rotation("0,0,0")
              .scale("1")
              .size((double) modelCreationRequest.getFile().getSize())
              .build();

      newModel = modelService.create(newModel);

      companyRequest.setAssetModel(newModel);
      companyRequest = companyRequestRepository.save(companyRequest);

      return CompanyRequestMapper.fromEntityToResponse(companyRequest);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        try {
          throw exception;
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      throw new AppException(ErrorCode.COMPANY_REQUEST_UPDATE_FAILED);
    }
  }

  @Override
  public List<ModelResponse> getModelByMachineTypeId(String machineTypeId, String companyId) {
    try {
      List<Model> modelsByMachineTypeId =
          modelService.getModelsByMachineTypeIdAndCompanyId(machineTypeId, companyId);

      return modelsByMachineTypeId.stream().map(ModelMapper::fromEntityToModelResponse).toList();
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_NOT_EXISTED);
    }
  }

  @Override
  public List<MachineResponse> getMachineByGuidelineId(String guidelineId) {
    try {
      Course courseById = courseService.findById(guidelineId);

      if (Objects.isNull(courseById)) {
        throw new AppException(ErrorCode.COURSE_NOT_EXISTED);
      }

      List<Machine> machines = machineService.getMachineByGuidelineId(courseById.getId());

      return machines.stream().map(MachineMapper::fromEntityToMachineResponse).toList();
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MACHINE_NOT_EXISTED);
    }
  }

  @Override
  public List<MachineGuidelineResponse> getMachineForMachineTabByGuidelineId(String guidelineId) {
    try {
      Course courseById = courseService.findById(guidelineId);

      if (Objects.isNull(courseById)) {
        throw new AppException(ErrorCode.COURSE_NOT_EXISTED);
      }

      List<Machine> machines = machineService.getMachineByGuidelineId(courseById.getId());

      return machines.stream().map(MachineMapper::fromEntityToMachineGuidelineResponse).toList();
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MACHINE_NOT_EXISTED);
    }
  }

  @Override
  public MachineTypeResponse getMachineTypeByGuidelineCode(String guidelineCode) {
    try {
      ModelType modelTypeByGuidelineCode =
          machineTypeService.getMachineTypeByGuidelineCode(guidelineCode);

      List<MachineTypeAttribute> machineTypeAttributesByMachineTypeId =
          machineTypeAttributeService.getByMachineTypeId(modelTypeByGuidelineCode.getId());

      List<MachineTypeAttributeResponse> machineTypeAttributeResponses =
          machineTypeAttributesByMachineTypeId.stream()
              .map(MachineTypeAttributeMapper::fromEntityToMachineTypeAttributeResponse)
              .toList();

      return MachineTypeMapper.fromEntityToMachineTypeResponse(
          modelTypeByGuidelineCode, machineTypeAttributeResponses);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MODEL_TYPE_NOT_EXISTED);
    }
  }

  @Override
  public Boolean checkMachineIsBelongToGuideline(String machineCode, String guidelineId) {
    try {
      return machineService.checkMachineIsBelongToGuideline(machineCode, guidelineId);
    } catch (Exception exception) {
      if (exception instanceof AppException) {
        throw exception;
      }
      throw new AppException(ErrorCode.MACHINE_NOT_EXISTED);
    }
  }

  @Override
  public List<MachineTypeValueResponse> getMachineTypeValueByMachineTypeId(String machineTypeId) {
    try {
      List<MachineTypeAttribute> machineTypeAttributes =
          machineTypeAttributeService.getByMachineTypeId(machineTypeId);

      return machineTypeAttributes.stream()
          .map(
              mvr -> {
                MachineTypeValueResponse machineTypeValueResponse = new MachineTypeValueResponse();
                machineTypeValueResponse.setMachineTypeAttributeName(mvr.getAttributeName());
                machineTypeValueResponse.setMachineTypeAttributeId(mvr.getId());

                if (mvr.getValueOfAttribute() == null || mvr.getValueOfAttribute().isEmpty()) {
                  machineTypeValueResponse.setValueAttribute("N/A");
                } else {
                  machineTypeValueResponse.setValueAttribute(mvr.getValueOfAttribute());
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
}
