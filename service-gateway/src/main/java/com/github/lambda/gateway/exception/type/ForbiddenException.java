package com.github.lambda.gateway.exception.type;

public class ForbiddenException extends RuntimeException {
  public ForbiddenException(String msg) {
    super(msg);
  }

  public ForbiddenException(String msg, Throwable t) {
    super(msg, t);
  }
}
