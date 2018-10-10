package com.github.lambda.playground.exception.type;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String msg) {
    super(msg);
  }

  public NotFoundException(String msg, Throwable t) {
    super(msg, t);
  }
}
