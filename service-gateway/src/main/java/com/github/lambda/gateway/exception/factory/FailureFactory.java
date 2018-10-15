package com.github.lambda.gateway.exception.factory;

import com.github.lambda.gateway.swagger.model.Failure;

public interface FailureFactory {
  Failure build(Throwable e, String message, long status, String path);
}
