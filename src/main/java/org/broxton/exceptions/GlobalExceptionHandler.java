package org.broxton.exceptions;


import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<CustomErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<CustomErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InvalidCredentialsException.class)
  public ResponseEntity<CustomErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(RoleNotFoundException.class)
  public ResponseEntity<CustomErrorResponse> handleRoleNotFoundException(RoleNotFoundException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(TokenInvalidException.class)
  public ResponseEntity<CustomErrorResponse> handleTokenInvalidException(TokenInvalidException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserNotAccessException.class)
  public ResponseEntity<CustomErrorResponse> handleUserNotAccessException(UserNotAccessException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(ProfileAlreadyExist.class)
  public ResponseEntity<CustomErrorResponse> handleProfileAlreadyExistException(ProfileAlreadyExist ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(ProfileNotFoundException.class)
  public ResponseEntity<CustomErrorResponse> handleProfileNotFoundException(ProfileNotFoundException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getConstraintViolations().forEach(violation -> {
      errors.put(violation.getPropertyPath().toString(), violation.getMessage());
    });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TokenExceptions.MissingTokenException.class)
  public ResponseEntity<CustomErrorResponse> handleMissingTokenException(TokenExceptions.MissingTokenException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserExceptions.UserUnauthorizedException.class)
  public ResponseEntity<CustomErrorResponse> handleUserUnauthorizedException(UserExceptions.UserUnauthorizedException ex) {
    return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
  }

  private ResponseEntity<CustomErrorResponse> buildErrorResponse(String message, HttpStatus httpStatus) {
    CustomErrorResponse errorResponse = CustomErrorResponse.builder()
            .statusCode(httpStatus.value())
            .timestamp(LocalDateTime.now())
            .message(message)
            .build();

    return new ResponseEntity<>(errorResponse, httpStatus);
  }
}
