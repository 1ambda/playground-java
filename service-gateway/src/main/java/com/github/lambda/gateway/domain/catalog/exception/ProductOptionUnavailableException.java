package com.github.lambda.gateway.domain.catalog.exception;

import com.github.lambda.gateway.exception.type.BadRequestException;

public class ProductOptionUnavailableException extends BadRequestException {
  public static ProductOptionUnavailableException create(Long productOptionId,
                                                         String postfix) {
    String message = new StringBuilder()
        .append("ProductOption ")
        .append(productOptionId)
        .append(" ")
        .append(postfix)
        .toString();

    return new ProductOptionUnavailableException(message);
  }

  public static ProductOptionUnavailableException create(String message) {
    return new ProductOptionUnavailableException(message);
  }

  private ProductOptionUnavailableException(String msg) {
    super(msg);
  }

  private ProductOptionUnavailableException(String msg, Throwable t) {
    super(msg, t);
  }
}
