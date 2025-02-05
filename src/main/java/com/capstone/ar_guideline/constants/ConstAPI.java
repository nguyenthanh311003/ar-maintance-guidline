package com.capstone.ar_guideline.constants;

public class ConstAPI {
  public static class ModelTypeAPI {
    public static final String CREATE_MODEL_TYPE = "api/v1/model-types";
    public static final String UPDATE_MODEL_TYPE = "api/v1/model-types/";
    public static final String DELETE_MODEL_TYPE = "api/v1/model-types/";
  }

  public static class CourseAPI {
    public static final String COURSE = "api/v1/course";
    public static final String COURSE_FIND_BY_TITLE = "api/v1/course/title";
    public static final String COURSE_FIND_BY_COMPANY_ID = "api/v1/course/company/";
    public static final String NO_MANDATORY_COURSE = "api/v1/course/no-mandatory";
  }

  public static class ModelAPI {
    public static final String CREATE_MODEL = "api/v1/model";
    public static final String UPDATE_MODEL = "api/v1/model/";
    public static final String DELETE_MODEL = "api/v1/model/";
  }

  public static class RoleAPI {
    public static final String GET_ROLES = "api/v1/roles";
    public static final String GET_ROLE_BY_ID = "api/v1/role/id";
    public static final String GET_ROLE_BY_NAME = "api/v1/role/name";
    public static final String CREATE_ROLE = "api/v1/role";
    public static final String DELETE_ROLE = "api/v1/role/";
  }

  public static class UserAPI {
    public static final String LOGIN = "api/v1/login";
    public static final String REGISTER = "api/v1/register";
    public static final String PREFIX_USER = "api/v1/user/";
    public static final String GET_USER_TO_ASSIGN = "course/";
  }

  public static class InstructionAPI {
    public static final String CREATE_INSTRUCTION = "api/v1/instruction";
    public static final String UPDATE_INSTRUCTION = "api/v1/instruction/";
    public static final String DELETE_INSTRUCTION = "api/v1/instruction/";
  }

  public static class InstructionDetailAPI {
    public static final String CREATE_INSTRUCTION_DETAIL = "api/v1/instruction-detail";
    public static final String UPDATE_INSTRUCTION_DETAIL = "api/v1/instruction-detail/";
    public static final String DELETE_INSTRUCTION_DETAIL = "api/v1/instruction-detail/";
  }

  public static class CompanyAPI {
    public static final String GET_COMPANIES = "api/v1/companies";
    public static final String GET_COMPANY_BY_ID = "api/v1/company/id";
    public static final String GET_COMPANY_BY_NAME = "api/v1/company/name";
    public static final String CREATE_COMPANY = "api/v1/company";
    public static final String DELETE_COMPANY = "api/v1/company/";
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
    public static final String UPDATE_STATUS_ENROLLMENT = "api/v1/enrollment/status/";
    public static final String FIND_COURSE_MANDATORY = "api/v1/enrollment/user/";
    public static final String ENROLL = "api/v1/enrollment/enrollment";
  }

  public static class SubscriptionAPI {
    public static final String CREATE_SUBSCRIPTION = "api/v1/subscription";
    public static final String UPDATE_SUBSCRIPTION = "api/v1/subscription/";
    public static final String DELETE_SUBSCRIPTION = "api/v1/subscription/";
  }

  public static class CompanySubscriptionAPI {
    public static final String CREATE_COMPANY_SUBSCRIPTION = "api/v1/company-subscription";
    public static final String UPDATE_COMPANY_SUBSCRIPTION = "api/v1/company-subscription/";
    public static final String DELETE_COMPANY_SUBSCRIPTION = "api/v1/company-subscription/";
  }

  public static class OrderTransactionAPI {
    public static final String CREATE_ORDER_TRANSACTION = "api/v1/order-transaction";
    public static final String UPDATE_ORDER_TRANSACTION = "api/v1/order-transaction/";
    public static final String DELETE_ORDER_TRANSACTION = "api/v1/order-transaction/";
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
}
