package com.github.lambda.playground.exception.type;

public class InternalServerException extends RuntimeException {
  public InternalServerException(String msg) {
    super(msg);
  }

  public InternalServerException(String msg, Throwable t) {
    super(msg, t);
  }
}
