package com.capstone.ar_guideline.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
  UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
  INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
  USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
  USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
  INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
  USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
  UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
  UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
  INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
  MODEL_TYPE_NOT_EXISTED(1009, "Model Type not existed", HttpStatus.NOT_FOUND),
  MODEL_TYPE_CREATE_FAILED(1010, "Model Type create fail", HttpStatus.BAD_REQUEST),
  MODEL_TYPE_UPDATE_FAILED(1011, "Model Type update fail", HttpStatus.BAD_REQUEST),
  MODEL_TYPE_DELETE_FAILED(1012, "Model Type delete fail", HttpStatus.BAD_REQUEST),
  MODEL_CREATE_FAILED(1013, "Model create fail", HttpStatus.BAD_REQUEST),
  MODEL_UPDATE_FAILED(1014, "Model update fail", HttpStatus.BAD_REQUEST),
  MODEL_DELETE_FAILED(1015, "Model delete fail", HttpStatus.BAD_REQUEST),
  MODEL_NOT_EXISTED(1016, "Model not existed", HttpStatus.NOT_FOUND),
  ROLE_CREATE_FAILED(2706, "Create role failed", HttpStatus.INTERNAL_SERVER_ERROR),
  ROLE_NOT_EXISTED(2707, "Role not existed", HttpStatus.NOT_FOUND),
  USER_DISABLED(2708, "User disabled", HttpStatus.NOT_FOUND),
  ROLE_FIND_ALL_FAILED(2709, "Find all role failed", HttpStatus.NOT_FOUND),
  COURSE_CREATE_FAILED(1021, "Course create failed", HttpStatus.BAD_REQUEST),
  COURSE_UPDATE_FAILED(1022, "Course update failed", HttpStatus.BAD_REQUEST),
  COURSE_DELETE_FAILED(1023, "Course delete failed", HttpStatus.BAD_REQUEST),
  COURSE_NOT_EXISTED(1024, "Course not existed", HttpStatus.NOT_FOUND),
  INSTRUCTION_CREATE_FAILED(1029, "Instruction create fail", HttpStatus.BAD_REQUEST),
  INSTRUCTION_UPDATE_FAILED(1030, "Instruction update fail", HttpStatus.BAD_REQUEST),
  INSTRUCTION_DELETE_FAILED(1031, "Instruction delete fail", HttpStatus.BAD_REQUEST),
  INSTRUCTION_NOT_EXISTED(1032, "Instruction not existed", HttpStatus.NOT_FOUND),
  INSTRUCTION_DETAIL_CREATE_FAILED(1033, "Instruction detail create fail", HttpStatus.BAD_REQUEST),
  INSTRUCTION_DETAIL_UPDATE_FAILED(1034, "Instruction detail update fail", HttpStatus.BAD_REQUEST),
  INSTRUCTION_DETAIL_DELETE_FAILED(1035, "Instruction detail delete fail", HttpStatus.BAD_REQUEST),
  INSTRUCTION_DETAIL_NOT_EXISTED(1036, "Instruction detail not existed", HttpStatus.NOT_FOUND),
  COMPANY_NOT_EXISTED(2710, "Company Not Found", HttpStatus.NOT_FOUND),
  COMPANY_CREATE_FAILED(2711, "Create Company failed", HttpStatus.INTERNAL_SERVER_ERROR),
  COMPANY_FIND_ALL_FAILED(2712, "Find all Company failed", HttpStatus.NOT_FOUND),
  LESSON_CREATE_FAILED(1025, "Lesson create fail", HttpStatus.BAD_REQUEST),
  LESSON_UPDATE_FAILED(1026, "Lesson update fail", HttpStatus.BAD_REQUEST),
  LESSON_DELETE_FAILED(1027, "Lesson delete fail", HttpStatus.BAD_REQUEST),
  LESSON_NOT_EXISTED(10289, "Lesson not existed", HttpStatus.NOT_FOUND),
  LESSON_PROCESS_NOT_EXISTED(1037, "Lesson Process create fail", HttpStatus.BAD_REQUEST),
  LESSON_PROCESS_CREATE_FAILED(1038, "Lesson Process create fail", HttpStatus.BAD_REQUEST),
  LESSON_PROCESS_UPDATE_FAILED(1039, "Lesson Process update fail", HttpStatus.BAD_REQUEST),
  LESSON_PROCESS_DELETE_FAILED(1040, "Lesson Process delete fail", HttpStatus.BAD_REQUEST),
  MODEL_LESSON_CREATE_FAILED(1041, "Model Lesson create fail", HttpStatus.BAD_REQUEST),
  MODEL_LESSON_UPDATE_FAILED(1042, "Model Lesson update fail", HttpStatus.BAD_REQUEST),
  MODEL_LESSON_DELETE_FAILED(1043, "Model Lesson delete fail", HttpStatus.BAD_REQUEST),
  MODEL_LESSON_NOT_EXISTED(1044, "Model Lesson not existed", HttpStatus.NOT_FOUND),
  ENROLLMENT_CREATE_FAILED(1045, "Enrollment create fail", HttpStatus.BAD_REQUEST),
  ENROLLMENT_UPDATE_FAILED(1046, "Enrollment update fail", HttpStatus.BAD_REQUEST),
  ENROLLMENT_DELETE_FAILED(1047, "Enrollment delete fail", HttpStatus.BAD_REQUEST),
  ENROLLMENT_NOT_EXISTED(1048, "Enrollment not existed", HttpStatus.NOT_FOUND),
  SUBSCRIPTION_CREATE_FAILED(1049, "Subscription create fail", HttpStatus.BAD_REQUEST),
  SUBSCRIPTION_UPDATE_FAILED(1050, "Subscription update fail", HttpStatus.BAD_REQUEST),
  SUBSCRIPTION_DELETE_FAILED(1051, "Subscription delete fail", HttpStatus.BAD_REQUEST),
  SUBSCRIPTION_NOT_EXISTED(1052, "Subscription not existed", HttpStatus.NOT_FOUND),
  AWS_S3_BUCKET_FAILED_TO_STORE_FILE(
      1053, "Failed to store file to s3", HttpStatus.INTERNAL_SERVER_ERROR),
  COMPANY_SUBSCRIPTION_CREATE_FAILED(
      1054, "Company Subscription create fail", HttpStatus.BAD_REQUEST),
  COMPANY_SUBSCRIPTION_UPDATE_FAILED(
      1055, "Company Subscription update fail", HttpStatus.BAD_REQUEST),
  COMPANY_SUBSCRIPTION_DELETE_FAILED(
      1056, "Company Subscription delete fail", HttpStatus.BAD_REQUEST),
  COMPANY_SUBSCRIPTION_NOT_EXISTED(1057, "Company Subscription not existed", HttpStatus.NOT_FOUND),
  ORDER_TRANSACTION_CREATE_FAILED(1058, "Order Transaction create fail", HttpStatus.BAD_REQUEST),
  ORDER_TRANSACTION_UPDATE_FAILED(1059, "Order Transaction update fail", HttpStatus.BAD_REQUEST),
  ORDER_TRANSACTION_DELETE_FAILED(1060, "Order Transaction delete fail", HttpStatus.BAD_REQUEST),
  ORDER_TRANSACTION_NOT_EXISTED(1061, "Order Transaction not existed", HttpStatus.NOT_FOUND),
  QUIZ_CREATE_FAILED(1062, "Quiz create fail", HttpStatus.BAD_REQUEST),
  QUIZ_UPDATE_FAILED(1063, "Quiz update fail", HttpStatus.BAD_REQUEST),
  QUIZ_DELETE_FAILED(1064, "Quiz delete fail", HttpStatus.BAD_REQUEST),
  QUIZ_NOT_EXISTED(1065, "Quiz not existed", HttpStatus.NOT_FOUND),
  RESULT_CREATE_FAILED(1066, "Results create fail", HttpStatus.BAD_REQUEST),
  RESULT_UPDATE_FAILED(1067, "Results update fail", HttpStatus.BAD_REQUEST),
  RESULT_DELETE_FAILED(1068, "Results delete fail", HttpStatus.BAD_REQUEST),
  RESULT_NOT_EXISTED(1069, "Results not existed", HttpStatus.NOT_FOUND),
  QUESTION_CREATE_FAILED(1070, "Question create fail", HttpStatus.BAD_REQUEST),
  QUESTION_UPDATE_FAILED(1071, "Question update fail", HttpStatus.BAD_REQUEST),
  QUESTION_DELETE_FAILED(1072, "Question delete fail", HttpStatus.BAD_REQUEST),
  QUESTION_NOT_EXISTED(1073, "Question not existed", HttpStatus.NOT_FOUND),
  OPTION_CREATE_FAILED(1074, "Option create fail", HttpStatus.BAD_REQUEST),
  OPTION_UPDATE_FAILED(1075, "Option update fail", HttpStatus.BAD_REQUEST),
  OPTION_DELETE_FAILED(1076, "Option delete fail", HttpStatus.BAD_REQUEST),
  OPTION_NOT_EXISTED(1077, "Option not existed", HttpStatus.NOT_FOUND),
  QUIZ_EXISTED(1078, "Quiz existed", HttpStatus.BAD_REQUEST),
  INSTRUCTION_PROCESS_NOT_EXISTED(1079, "Instruction process not existed", HttpStatus.BAD_REQUEST),
  LESSON_DETAIL(1080, "Lesson detail not existed", HttpStatus.BAD_REQUEST),
  LESSON_DETAIL_CREATE_FAILED(1081, "Lesson detail create failed", HttpStatus.BAD_REQUEST),
  LESSON_DETAIL_UPDATE_FAILED(1082, "Lesson detail update failed", HttpStatus.BAD_REQUEST),
  FIND_COURSE_MANDATORY_FAILED(1083, "Find course mandatory failed", HttpStatus.BAD_REQUEST),
  FIND_COURSE_NO_MANDATORY_FAILED(1084, "Find course no mandatory failed", HttpStatus.BAD_REQUEST),
  FIND_USER_TO_ASSIGN_FAILED(1085, "Find user to assign failed", HttpStatus.BAD_REQUEST),
  ENROLLMENT_EXISTED(1086, "Enrollment existed", HttpStatus.BAD_REQUEST),
  GET_HIGHEST_ORDER_FAIL(1087, "Get highest order number failed", HttpStatus.BAD_REQUEST),
  SWAP_ORDER_NUMBER_FAILED(1088, "Swap order number failed", HttpStatus.BAD_REQUEST),
  USER_UPDATE_FAILED(1089, "User update failed", HttpStatus.BAD_REQUEST),
  USER_CREATE_FAILED(1090, "User create failed", HttpStatus.BAD_REQUEST),
  COMPANY_SUBSCRIPTION_EXISTED(1091, "Company Subscription existed", HttpStatus.BAD_REQUEST),
  COMPANY_SUBSCRIPTION_EXPIRED(1092, "Company Subscription expired", HttpStatus.BAD_REQUEST),
  COMPANY_SUBSCRIPTION_MODEL_OVER_LIMIT(
      1093, "Company Model is greater than subscription limit", HttpStatus.BAD_REQUEST),
  COMPANY_SUBSCRIPTION_USER_OVER_LIMIT(
      1094, "Company User is greater than subscription limit", HttpStatus.BAD_REQUEST),
  MODEL_NAME_EXISTED(1095, "Model existed", HttpStatus.BAD_REQUEST),
  UPDATE_GUIDELINE_FAIL_MODEL_INACTIVE_STATUS(
      1096,
      "Can not change guideline active because model of this guideline is inactive",
      HttpStatus.BAD_REQUEST),
  INSTRUCTION_DETAIL_COUNT_FAILED(1097, "Instruction detail count failed", HttpStatus.BAD_REQUEST),
  UPDATE_GUIDELINE_FAIL_INSTRUCTION_COUNT(
      1098,
      "Can not change guideline active because this guideline need more than 1 instruction detail",
      HttpStatus.BAD_REQUEST),
  USER_DELETE_FAILED(1099, "Delete user failed", HttpStatus.BAD_REQUEST),
  MACHINE_TYPE_NAME_EXISTED(1100, "Machine Type Name existed !", HttpStatus.BAD_REQUEST),
  MACHINE_CREATE_FAILED(1101, "Machine create fail", HttpStatus.BAD_REQUEST),
  MACHINE_UPDATE_FAILED(1102, "Machine update fail", HttpStatus.BAD_REQUEST),
  MACHINE_DELETE_FAILED(1103, "Machine delete fail", HttpStatus.BAD_REQUEST),
  MACHINE_NOT_EXISTED(1104, "Machine not existed", HttpStatus.NOT_FOUND),
  MACHINE_NAME_EXISTED(1105, "Machine name existed !", HttpStatus.BAD_REQUEST),
  MACHINE_TYPE_ATTRIBUTE_CREATE_FAILED(
      1106, "Machine Type Attribute create fail", HttpStatus.BAD_REQUEST),
  MACHINE_TYPE_ATTRIBUTE_UPDATE_FAILED(
      1107, "Machine Type Attribute update fail", HttpStatus.BAD_REQUEST),
  MACHINE_TYPE_ATTRIBUTE_DELETE_FAILED(
      1108, "Machine Type Attribute delete fail", HttpStatus.BAD_REQUEST),
  MACHINE_TYPE_ATTRIBUTE_NOT_EXISTED(
      1109, "Machine Type Attribute not existed", HttpStatus.NOT_FOUND),
  MACHINE_TYPE_ATTRIBUTE_NAME_EXISTED(
      1110, "Machine Type Attribute name existed !", HttpStatus.BAD_REQUEST),
  MACHINE_TYPE_VALUE_CREATE_FAILED(1111, "Machine Type Value create fail", HttpStatus.BAD_REQUEST),
  MACHINE_TYPE_VALUE_UPDATE_FAILED(1112, "Machine Type Value update fail", HttpStatus.BAD_REQUEST),
  MACHINE_TYPE_VALUE_DELETE_FAILED(1113, "Machine Type Value delete fail", HttpStatus.BAD_REQUEST),
  MACHINE_TYPE_VALUE_NOT_EXISTED(1114, "Machine Type Value not existed", HttpStatus.NOT_FOUND),
  GET_ASSIGN_GUIDELINES_BY_GUIDELINE_ID_FAILED(
      2000, "Get all assign guidelines by guideline id failed", HttpStatus.BAD_REQUEST),
  CREATE_ASSIGN_GUIDELINE_FAILED(
      2001, "Get all assign guidelines by guideline id failed", HttpStatus.BAD_REQUEST),
  COMPANY_REQUEST_FAILED(1115, "COMPANY REQUEST FEATURES ERROR", HttpStatus.BAD_REQUEST),
  MACHINE_QR_CREATE_FAILED(1116, "Machine QR create fail", HttpStatus.BAD_REQUEST),
  MACHINE_QR_UPDATE_FAILED(1117, "Machine QR update fail", HttpStatus.BAD_REQUEST),
  MACHINE_QR_DELETE_FAILED(1118, "Machine QR delete fail", HttpStatus.BAD_REQUEST),
  MACHINE_QR_NOT_EXISTED(1119, "Machine QR not existed", HttpStatus.NOT_FOUND),
  JSON_PROCESSING_ERROR(1120, "JSON PROCESSING fail", HttpStatus.BAD_REQUEST),
  MACHINE_CODE_EXISTED(1121, "Machine Code existed !", HttpStatus.BAD_REQUEST),
  POINT_REQUEST_ERROR(2002, "Point Request Error", HttpStatus.BAD_REQUEST),
  COMPANY_REQUEST_CREATE_FAILED(1122, "Company request create fail", HttpStatus.BAD_REQUEST),
  COMPANY_REQUEST_UPDATE_FAILED(1123, "Company request update fail", HttpStatus.BAD_REQUEST),
  COMPANY_REQUEST_DELETE_FAILED(1124, "Company request delete fail", HttpStatus.BAD_REQUEST),
  COMPANY_REQUEST_NOT_EXISTED(1125, "Company request not existed", HttpStatus.NOT_FOUND),
  MACHINE_IS_CURRENT_USED(1126, "Machine being used now !", HttpStatus.BAD_REQUEST),
  MACHINE_TYPE_IS_CURRENT_USED(1127, "Machine type being used now !", HttpStatus.BAD_REQUEST),
  NOTIFICATION_REGISTRATION_FAILED(
      1127, "Failed to register notification device", HttpStatus.BAD_REQUEST),
  TOPIC_SUBSCRIPTION_FAILED(
      1127, "Failed to subscribe to notification topic", HttpStatus.BAD_REQUEST),
  TOPIC_UNSUBSCRIPTION_FAILED(
      1127, "Failed to unsubscribe from notification topic", HttpStatus.BAD_REQUEST),
  NOTIFICATION_SEND_FAILED(1127, "Failed to send notification", HttpStatus.BAD_REQUEST),
  USER_PHONE_EXISTED(1128, "User Phone existed !", HttpStatus.BAD_REQUEST),
  CAN_ONLY_JOIN_ONE_COMPANY_REQUEST(1129, "Can only join 1 request at the same time", HttpStatus.BAD_REQUEST),
  ;

  private final int code;
  private final String message;
  private final HttpStatusCode statusCode;

  ErrorCode(int code, String message, HttpStatusCode statusCode) {
    this.code = code;
    this.message = message;
    this.statusCode = statusCode;
  }
}
