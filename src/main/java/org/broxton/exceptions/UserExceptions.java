package org.broxton.exceptions;

public class UserExceptions {
  public static class UserUnauthorizedException extends RuntimeException {
    public UserUnauthorizedException(String message) {
      super(message);
    }

    public UserUnauthorizedException() {
      super("User unauthorized");
    }
  }
}
