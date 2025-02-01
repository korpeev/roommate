package org.broxton.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

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

  private ResponseEntity<CustomErrorResponse> buildErrorResponse(String message, HttpStatus httpStatus) {
    CustomErrorResponse errorResponse = CustomErrorResponse.builder()
            .statusCode(httpStatus.value())
            .timestamp(LocalDateTime.now())
            .message(message)
            .build();

    return new ResponseEntity<>(errorResponse, httpStatus);
  }
}
