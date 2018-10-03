package com.github.lambda.playground.exception.type.custom;

import com.github.lambda.playground.exception.type.UnauthorizedException;

public class AlreadyLoggedOutException extends UnauthorizedException {
  public AlreadyLoggedOutException(String msg) {
    super(msg);
  }

  public AlreadyLoggedOutException(String msg, Throwable t) {
    super(msg, t);
  }
}
