package com.github.lambda.gateway.websocket.exception;

import com.github.lambda.gateway.exception.type.InternalServerException;

public class WebsocketInvalidPayloadException extends InternalServerException {
  public static WebsocketInvalidPayloadException create(String message) {
    return new WebsocketInvalidPayloadException(message);
  }

  public static WebsocketInvalidPayloadException create(Throwable t) {
    return new WebsocketInvalidPayloadException("Got invalid payload", t);
  }

  private WebsocketInvalidPayloadException(String msg) {
    super(msg);
  }

  private WebsocketInvalidPayloadException(String msg, Throwable t) {
    super(msg, t);
  }
}
