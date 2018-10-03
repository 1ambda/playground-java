package com.github.lambda.playground.exception.type;

public class BadRequestException extends RuntimeException {
  public BadRequestException(String msg) {
    super(msg);
  }

  public BadRequestException(String msg, Throwable t) {
    super(msg, t);
  }
}
