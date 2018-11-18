package com.github.lambda.gateway.websocket;

import com.github.lambda.gateway.exception.factory.FailureFactory;
import com.github.lambda.gateway.swagger.model.Failure;
import com.github.lambda.gateway.swagger.model.WebsocketMessageBase;
import com.github.lambda.gateway.swagger.model.WebsocketMessageHeader;
import com.github.lambda.gateway.swagger.model.WebsocketMessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class WebsocketErrorBuilder {
  private FailureFactory failureFactory;

  @Autowired
  public WebsocketErrorBuilder(FailureFactory failureFactory) {
    this.failureFactory = failureFactory;
  }

  public WebsocketMessageBase buildBadRequestError(Throwable t,
                                                   String message) {

    return build(t, message, WebsocketMessageType.ERROR, HttpStatus.BAD_REQUEST);
  }

  public WebsocketMessageBase buildInternalServerError(Throwable t,
                                                       String message) {

    return build(t, message, WebsocketMessageType.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public WebsocketMessageBase build(Throwable throwable,
                                    String message,
                                    WebsocketMessageType type,
                                    HttpStatus httpStatus) {
    if (StringUtils.isEmpty(message)) {
      message = throwable.getMessage();
    }

    Failure failure = failureFactory.build(
        throwable, message, httpStatus.value(), null);

    WebsocketMessageHeader header = WebsocketMessageHeader.builder()
        .failure(failure)
        .type(type)
        .build();

    WebsocketMessageBase base = WebsocketMessageBase.builder()
        .header(header)
        .build();

    return base;
  }
}
