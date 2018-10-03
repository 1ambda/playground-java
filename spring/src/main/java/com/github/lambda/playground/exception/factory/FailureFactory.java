package com.github.lambda.playground.exception.factory;

import com.github.lambda.playground.swagger.model.Failure;

public interface FailureFactory {
    Failure build(Throwable e, String message, long status, String path);
}
