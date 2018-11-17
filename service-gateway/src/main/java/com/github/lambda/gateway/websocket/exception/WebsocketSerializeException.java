package com.github.lambda.gateway.websocket.exception;

import com.github.lambda.gateway.exception.type.InternalServerException;

public class WebsocketSerializeException extends InternalServerException {
  public static WebsocketSerializeException create(String message) {
    return new WebsocketSerializeException(message);
  }

  public static WebsocketSerializeException create(Throwable t) {
    return new WebsocketSerializeException(
        "Failed to serialize message", t);
  }

  private WebsocketSerializeException(String msg) {
    super(msg);
  }

  private WebsocketSerializeException(String msg, Throwable t) {
    super(msg, t);
  }
}
