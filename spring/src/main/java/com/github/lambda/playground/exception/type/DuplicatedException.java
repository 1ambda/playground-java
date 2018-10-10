package com.github.lambda.playground.exception.type;

public class DuplicatedException extends RuntimeException {
  public DuplicatedException(String msg) {
    super(msg);
  }

  public DuplicatedException(String msg, Throwable t) {
    super(msg, t);
  }
}
