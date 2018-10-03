package com.github.lambda.playground.exception.type;

public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException(String msg) {
    super(msg);
  }

  public UnauthorizedException(String msg, Throwable t) {
    super(msg, t);
  }
}
