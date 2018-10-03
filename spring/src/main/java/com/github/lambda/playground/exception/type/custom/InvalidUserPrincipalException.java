package com.github.lambda.playground.exception.type.custom;

import com.github.lambda.playground.exception.type.UnauthorizedException;

public class InvalidUserPrincipalException extends UnauthorizedException {
  public InvalidUserPrincipalException(String msg) {
    super(msg);
  }

  public InvalidUserPrincipalException(String msg, Throwable t) {
    super(msg, t);
  }
}
