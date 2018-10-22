package com.github.lambda.gateway.security.exception;

import com.github.lambda.gateway.exception.type.UnauthorizedException;

public class AlreadyLoggedOutException extends UnauthorizedException {
  public AlreadyLoggedOutException(String msg) {
    super(msg);
  }

  public AlreadyLoggedOutException(String msg, Throwable t) {
    super(msg, t);
  }
}
