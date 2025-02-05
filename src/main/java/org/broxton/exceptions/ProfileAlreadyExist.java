package org.broxton.exceptions;

public class ProfileAlreadyExist extends RuntimeException {
  public ProfileAlreadyExist(String message) {
    super(message);
  }
}
