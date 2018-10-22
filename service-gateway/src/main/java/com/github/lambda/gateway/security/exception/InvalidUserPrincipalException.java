package com.github.lambda.gateway.security.exception;

import com.github.lambda.gateway.exception.type.UnauthorizedException;

public class InvalidUserPrincipalException extends UnauthorizedException {
  public InvalidUserPrincipalException(String msg) {
    super(msg);
  }

  public InvalidUserPrincipalException(String msg, Throwable t) {
    super(msg, t);
  }
}
