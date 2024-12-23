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
  INSTRUCTION_CREATE_FAILED(1029, "Instruction create fail", HttpStatus.BAD_REQUEST),
  INSTRUCTION_UPDATE_FAILED(1030, "Instruction update fail", HttpStatus.BAD_REQUEST),
  INSTRUCTION_DELETE_FAILED(1031, "Instruction delete fail", HttpStatus.BAD_REQUEST),
  INSTRUCTION_NOT_EXISTED(1032, "Instruction not existed", HttpStatus.NOT_FOUND),
  INSTRUCTION_DETAIL_CREATE_FAILED(1033, "Instruction detail create fail", HttpStatus.BAD_REQUEST),
  INSTRUCTION_DETAIL_UPDATE_FAILED(1034, "Instruction detail update fail", HttpStatus.BAD_REQUEST),
  INSTRUCTION_DETAIL_DELETE_FAILED(1035, "Instruction detail delete fail", HttpStatus.BAD_REQUEST),
  INSTRUCTION_DETAIL_NOT_EXISTED(1036, "Instruction detail not existed", HttpStatus.NOT_FOUND),
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
