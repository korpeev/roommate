package org.broxton.exceptions;

public class TokenExceptions {
  public static class MissingTokenException extends RuntimeException {
    public MissingTokenException() {
      super("Token is missing");
    }
  }

  public static class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
      super("Invalid token");
    }
  }

  public static class ExpiredTokenException extends RuntimeException {
    public ExpiredTokenException() {
      super("Token has expired");
    }
  }
}
