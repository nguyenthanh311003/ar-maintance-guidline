package com.capstone.ar_guideline.constants;

public class ConstAPI {
  public static class ModelTypeAPI {
    public static final String GET_ALL_MODEL_TYPE = "api/v1/model-types";
    public static final String GET_MODEL_TYPE_BY_COMPANY = "api/v1/model-types/company/";
    public static final String CREATE_MODEL_TYPE = "api/v1/model-types";
    public static final String UPDATE_MODEL_TYPE = "api/v1/model-types/";
    public static final String DELETE_MODEL_TYPE = "api/v1/model-types/";
  }

  public static class CourseAPI {
    public static final String COURSE = "api/v1/course";
    public static final String CHANGE_STATUS_GUIDELINE = "api/v1/course/status/";
    public static final String COURSE_FIND_BY_TITLE = "api/v1/course/title";
    public static final String COURSE_FIND_BY_COMPANY_ID = "api/v1/course/company/";
    public static final String FIND_COURSE_BY_CODE = "api/v1/course/code/";
    public static final String NO_MANDATORY_COURSE = "api/v1/course/no-mandatory/company/";
    public static final String UPDATE_COURSE_PICTURE = "api/v1/course/picture";
    public static final String UPDATE_COURSE_SCAN_TIMES = "api/v1/course/scan";
    public static final String PUBLIC_GUIDELINE_FIRST_TIME = "api/v1/course/publication";
    public static final String COUNT_INSTRUCTION_DETAILS_DRAFTED =
        "api/v1/course/instruction-detail-drafted/number";
  }

  public static class ModelAPI {
    public static final String CREATE_MODEL = "api/v1/model";
    public static final String UPDATE_MODEL = "api/v1/model/";
    public static final String DELETE_MODEL = "api/v1/model/";
    public static final String GET_MODEL_BY_ID = "api/v1/model/";
    public static final String GET_MODELS_BY_MACHINE_TYPE_ID_AND_COMPANY_ID =
        "api/v1/model/machine-type/";
    public static final String GET_MODEL_BY_COURSE = "api/v1/model/course/";
    public static final String GET_UNUSED_MODEL_BY_ID = "api/v1/model/unused/company/";
    public static final String GET_MODEL_BY_COMPANY_ID = "api/v1/model/company/";
  }

  public static class RoleAPI {
    public static final String GET_ROLES = "api/v1/roles";
    public static final String GET_ROLE_BY_ID = "api/v1/role/id";
    public static final String GET_ROLE_BY_NAME = "api/v1/role/name";
    public static final String CREATE_ROLE = "api/v1/role";
    public static final String DELETE_ROLE = "api/v1/role/";
  }

  public static class UserAPI {
    public static final String GET_USERS = "api/v1/user";
    public static final String GET_STAFF_BY_COMPANY = "api/v1/user/company/";
    public static final String LOGIN = "api/v1/login";
    public static final String REGISTER = "api/v1/register";
    public static final String REGISTER_FOR_COMPANY = "api/v1/register/company";
    public static final String PREFIX_USER = "api/v1/user/";
    public static final String GET_USER_TO_ASSIGN = "course/";
  }

  public static class InstructionAPI {
    public static final String GET_INSTRUCTIONS_BY_COURSE_ID = "api/v1/instruction/course/";
    public static final String GET_INSTRUCTIONS_BY_COURSE_ID_FOR_SWAP_ORDER =
        "api/v1/instruction/no-paging/course/";
    public static final String CREATE_INSTRUCTION = "api/v1/instruction";
    public static final String UPDATE_INSTRUCTION = "api/v1/instruction/";
    public static final String DELETE_INSTRUCTION = "api/v1/instruction/";
    public static final String GET_INSTRUCTION_BY_ID = "api/v1/instruction/";
    public static final String SWAP_ORDER_INSTRUCTION = "api/v1/instruction/swap-order";
  }

  public static class InstructionDetailAPI {
    public static final String GET_INSTRUCTION_DETAIL_BY_ID = "api/v1/instruction-detail/";
    public static final String GET_INSTRUCTION_DETAIL_BY_INSTRUCTION =
        "api/v1/instruction-detail/instruction/";
    public static final String CREATE_INSTRUCTION_DETAIL = "api/v1/instruction-detail";
    public static final String UPDATE_INSTRUCTION_DETAIL = "api/v1/instruction-detail/";
    public static final String DELETE_INSTRUCTION_DETAIL = "api/v1/instruction-detail/";
    public static final String SWAP_ORDER_INSTRUCTION_DETAIL =
        "api/v1/instruction-detail/swap-order";
  }

  public static class CompanyAPI {
    public static final String GET_COMPANIES = "api/v1/companies";
    public static final String GET_COMPANY_MANAGEMENT = "api/v1/company/all";
    public static final String GET_COMPANY_BY_ID = "api/v1/company/id";
    public static final String GET_COMPANY_BY_NAME = "api/v1/company/name";
    public static final String CREATE_COMPANY = "api/v1/company";
    public static final String DELETE_COMPANY = "api/v1/company/";
    public static final String GET_COMPANY_BY_USER_ID = "api/v1/company/userId";
  }

  public static class LessonAPI {
    public static final String LESSON = "api/v1/lesson";
  }

  public static class LessonProcessAPI {
    public static final String LESSON_PROCESS = "api/v1/lesson-process";
  }

  public static class InstructionLessonAPI {
    public static final String INSTRUCTION_LESSON = "api/v1/instruction-lesson";
  }

  public static class EnrollmentAPI {
    public static final String CREATE_ENROLLMENT = "api/v1/enrollment";
    public static final String UPDATE_ENROLLMENT = "api/v1/enrollment/";
    public static final String DELETE_ENROLLMENT = "api/v1/enrollment/";
    public static final String DELETE_ENROLLMENT_BY_COURSE_USER = "api/v1/enrollment/course/";
    public static final String UPDATE_STATUS_ENROLLMENT = "api/v1/enrollment/status/";
    public static final String FIND_COURSE_MANDATORY = "api/v1/enrollment/user/";
    public static final String ENROLL = "api/v1/enrollment/enrollment";
  }

  public static class SubscriptionAPI {
    public static final String CREATE_SUBSCRIPTION = "api/v1/subscription";
    public static final String UPDATE_SUBSCRIPTION = "api/v1/subscription/";
    public static final String DELETE_SUBSCRIPTION = "api/v1/subscription/";
    public static final String GET_CURRENT_PLAN = "api/v1/subscription/company/";
  }

  public static class CompanySubscriptionAPI {
    public static final String CREATE_COMPANY_SUBSCRIPTION = "api/v1/company-subscription";
    public static final String UPDATE_COMPANY_SUBSCRIPTION = "api/v1/company-subscription/";
    public static final String DELETE_COMPANY_SUBSCRIPTION = "api/v1/company-subscription/";
    public static final String FIND_BY_COMPANY_ID = "api/v1/company-subscription/company/";
  }

  public static class OrderTransactionAPI {
    public static final String GET_ORDER_TRANSACTION_BY_COMPANY_ID =
        "api/v1/order-transaction/company/";
    public static final String CREATE_ORDER_TRANSACTION = "api/v1/order-transaction";
    public static final String UPDATE_ORDER_TRANSACTION = "api/v1/order-transaction/";
    public static final String DELETE_ORDER_TRANSACTION = "api/v1/order-transaction/";
    public static final String HANDLE_ORDER_STATUS = "api/v1/order-transaction/order-status/";
    public static final String GET_ALL_ORDER_TRANSACTION = "api/v1/order-transaction/all";
  }

  public static class QuizAPI {
    public static final String CREATE_QUIZ = "api/v1/quiz";
    public static final String UPDATE_QUIZ = "api/v1/quiz/";
    public static final String DELETE_QUIZ = "api/v1/quiz/";
    public static final String FIND_QUIZ_BY_COURSE_ID = "api/v1/quiz/course/";
  }

  public static class QuestionAPI {
    public static final String QUESTION = "api/v1/question";
    public static final String UPDATE_QUESTION = "api/v1/question/";
    public static final String FIND_QUESTION_BY_QUIZ_ID = "api/v1/question/quiz/";
    public static final String DELETE_QUESTION = "api/v1/question/";
  }

  public static class OptionAPI {
    public static final String OPTION = "api/v1/option";
  }

  public static class ResultAPI {
    public static final String CREATE_RESULT = "api/v1/result";
    public static final String UPDATE_RESULT = "api/v1/result/";
    public static final String DELETE_RESULT = "api/v1/result/";
  }

  public static class InstructionProcessAPI {
    public static final String InstructionProcess = "api/v1/instruction-process";
  }

  public static class LessonDetailAPI {
    public static final String LESSON_DETAIL = "api/v1/lesson-detail";
  }

  public static class FileAPI {
    public static final String FILE = "api/v1/files";
  }

  public static class DashboardAPI {
    public static final String ADMIN_DASHBOARD = "api/v1/dashboard/admin";
    public static final String COMPANY_DASHBOARD = "api/v1/dashboard/company";
  }

  public static class WalletAPI {
    public static final String WALLET = "api/v1/wallets/user";
    public static final String WALLET_HISTORY = "api/v1/wallets/history/user";
  }

  public static class AssignGuidelineAPI {
    public static final String ASSIGN_GUIDELINES = "api/v1/assign-guideline";
  }

  public static class CompanyRequestAPI {
    public static final String COMPANY_REQUEST = "api/v1/company-request";
    public static final String COMPANY_REQUEST_UPLOAD_AGAIN =
        "api/v1/company-request/upload-again/";
  }

  public static class MachineAPI {
    public static final String GET_MACHINES_BY_COMPANY = "api/v1/machine/company/";
    public static final String GET_MACHINES_BY_ID = "api/v1/machine/";
    public static final String DELETE_MACHINE_BY_ID = "api/v1/machine/";
    public static final String GET_MACHINES_BY_CODE = "api/v1/machine/code/";
    public static final String GET_MACHINES_BY_GUIDELINE_ID = "api/v1/machine/guideline/";
    public static final String CREATE_MACHINE = "api/v1/machine";
    public static final String UPDATE_MACHINE = "api/v1/machine/";
    public static final String GET_MACHINE_QR_BY_MACHINE_ID = "api/v1/machine-qr/machine/";
  }

  public static class MachineTypeAttributeAPI {
    public static final String GET_MACHINES_TYPE_ATTRIBUTE_BY_MACHINE_TYPE =
        "api/v1/machine-type-attribute/machine-type/";
    public static final String CREATE_MACHINE_TYPE_ATTRIBUTE = "api/v1/machine-type-attribute";
    public static final String DELETE_MACHINE_TYPE_ATTRIBUTE = "api/v1/machine-type-attribute/";
  }

  public static class MachineTypeAPI {
    public static final String CREATE_MACHINE_TYPE = "api/v1/machine-type";
    public static final String UPDATE_MACHINE_TYPE = "api/v1/machine-type/";
    public static final String GET_MACHINE_TYPES_BY_COMPANY_ID = "api/v1/machine-type/company/";
    public static final String GET_MACHINE_TYPES_BY_ID = "api/v1/machine-type/";
    public static final String GET_MACHINE_TYPE_BY_GUIDELINE_CODE =
        "api/v1/machine-type/guideline/code/";
    public static final String DELETE_MACHINE_TYPES = "api/v1/machine-type/";
  }

  public static class PointRequestAPI {
    public static final String POINT_REQUEST_ENDPOINT = "api/v1/point-request";
  }

  public static class NotificationAPI {
    public static final String REGISTER_DEVICE = "api/v1/notifications/register";
    public static final String SUBSCRIBE_TO_TOPIC = "api/v1/notifications/subscribe";
    public static final String UNSUBSCRIBE_FROM_TOPIC = "api/v1/notifications/unsubscribe";
    public static final String SEND_TO_TOPIC = "api/v1/notifications/send/topic";
    public static final String SEND_TO_TOKEN = "api/v1/notifications/send/token";
  }


}
