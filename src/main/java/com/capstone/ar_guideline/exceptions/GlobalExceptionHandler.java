package com.capstone.ar_guideline.exceptions;

import com.capstone.ar_guideline.dtos.responses.ApiResponse;
import jakarta.validation.ConstraintViolation;
import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  private static final String MIN_ATTRIBUTE = "min";

  @ExceptionHandler(value = Exception.class)
  ResponseEntity<ApiResponse> handlingRuntimeException(Exception exception) {
    log.error("Exception: ", exception);
    ApiResponse apiResponse = new ApiResponse();

    apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
    apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

    return ResponseEntity.badRequest().body(apiResponse);
  }

  @ExceptionHandler(value = com.amazonaws.services.mq.model.UnauthorizedException.class)
  ResponseEntity<ApiResponse> handlingUnauthorizedException(com.amazonaws.services.mq.model.UnauthorizedException exception) {
    log.warn("Unauthorized access attempt: ", exception);

    ErrorCode errorCode = ErrorCode.UNAUTHORIZED; // Define this in your error codes

    return ResponseEntity.status(errorCode.getStatusCode())
            .body(ApiResponse.builder()
                    .code(errorCode.getCode())
                    .message("Invalid or expired token.")
                    .build());
  }


  @ExceptionHandler(value = AppException.class)
  ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
    ErrorCode errorCode = exception.getErrorCode();
    ApiResponse apiResponse = new ApiResponse();

    apiResponse.setCode(errorCode.getCode());
    apiResponse.setMessage(errorCode.getMessage());

    return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
  }

  @ExceptionHandler(value = AccessDeniedException.class)
  ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
    ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

    return ResponseEntity.status(errorCode.getStatusCode())
        .body(
            ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build());
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
    String enumKey = exception.getFieldError().getDefaultMessage();

    ErrorCode errorCode = ErrorCode.INVALID_KEY;
    Map<String, Object> attributes = null;
    try {
      errorCode = ErrorCode.valueOf(enumKey);

      var constraintViolation =
          exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);

      attributes = constraintViolation.getConstraintDescriptor().getAttributes();

      log.info(attributes.toString());

    } catch (IllegalArgumentException e) {

    }

    ApiResponse apiResponse = new ApiResponse();

    apiResponse.setCode(errorCode.getCode());
    apiResponse.setMessage(
        Objects.nonNull(attributes)
            ? mapAttribute(errorCode.getMessage(), attributes)
            : errorCode.getMessage());

    return ResponseEntity.badRequest().body(apiResponse);
  }

  private String mapAttribute(String message, Map<String, Object> attributes) {
    String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

    return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
  }
}
