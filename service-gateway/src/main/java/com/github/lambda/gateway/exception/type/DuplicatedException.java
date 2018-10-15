package com.github.lambda.gateway.exception.type;

public class DuplicatedException extends RuntimeException {
  public DuplicatedException(String msg) {
    super(msg);
  }

  public DuplicatedException(String msg, Throwable t) {
    super(msg, t);
  }
}
