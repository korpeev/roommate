package org.broxton.exceptions;

public class UserNotAccessException extends RuntimeException {
  public UserNotAccessException(String message) {
    super(message);
  }
}
