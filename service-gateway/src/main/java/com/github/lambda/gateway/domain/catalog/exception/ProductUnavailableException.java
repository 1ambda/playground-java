package com.github.lambda.gateway.domain.catalog.exception;

import com.github.lambda.gateway.exception.type.BadRequestException;

public class ProductUnavailableException extends BadRequestException {
  public static ProductUnavailableException create(Long productId) {
    String message = new StringBuilder()
        .append("Product ")
        .append(productId)
        .append(" is not available")
        .toString();

    return new ProductUnavailableException(message);
  }

  private ProductUnavailableException(String msg) {
    super(msg);
  }

  private ProductUnavailableException(String msg, Throwable t) {
    super(msg, t);
  }
}
