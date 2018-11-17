package com.github.lambda.gateway.websocket.exception;

import com.github.lambda.gateway.exception.type.InternalServerException;

public class WebsocketRegistryException extends InternalServerException {
  public static WebsocketRegistryException create(String message) {
    return new WebsocketRegistryException(message);
  }

  public static WebsocketRegistryException create(Throwable t) {
    return new WebsocketRegistryException(
        "Failed to add invalid websocket session to registry", t);
  }

  private WebsocketRegistryException(String msg) {
    super(msg);
  }

  private WebsocketRegistryException(String msg, Throwable t) {
    super(msg, t);
  }
}
