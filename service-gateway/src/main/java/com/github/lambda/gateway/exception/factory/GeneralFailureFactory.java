package com.github.lambda.gateway.exception.factory;

import com.github.lambda.gateway.common.Time;
import com.github.lambda.gateway.swagger.model.Failure;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Profile({"stag", "prod"})
@Component
public class GeneralFailureFactory implements FailureFactory {
  @Override
  public Failure build(Throwable e, String message, long status, String path) {
    Failure.FailureBuilder failureBuilder = Failure.builder();
    failureBuilder.timestamp(Time.getCurrentTimestamp());
    failureBuilder.code(status);
    failureBuilder.type(e.getClass().getCanonicalName());

    if (!StringUtils.isEmpty(path)) {
      failureBuilder.path(path);
    }

    if (StringUtils.isEmpty(message)) {
      failureBuilder.message(e.getMessage());
    }

    Failure failure = failureBuilder.build();
    return failure;
  }
}
