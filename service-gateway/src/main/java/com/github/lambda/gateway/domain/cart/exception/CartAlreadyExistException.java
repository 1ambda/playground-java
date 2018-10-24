package com.github.lambda.gateway.domain.cart.exception;

import com.github.lambda.gateway.exception.type.DuplicatedException;

public class CartAlreadyExistException extends DuplicatedException {
  public static CartAlreadyExistException create(Long userId) {
    String message = new StringBuilder()
        .append("Cart already exists for userId ")
        .append(userId)
        .toString();

    return new CartAlreadyExistException(message);
  }

  private CartAlreadyExistException(String msg) {
    super(msg);
  }

  private CartAlreadyExistException(String msg, Throwable t) {
    super(msg, t);
  }
}
