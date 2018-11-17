package com.github.lambda.gateway.websocket.exception;

import com.github.lambda.gateway.exception.type.InternalServerException;

public class WebsocketMessageSendException extends InternalServerException {
  public static WebsocketMessageSendException create(String message) {
    return new WebsocketMessageSendException(message);
  }

  public static WebsocketMessageSendException create(Throwable t) {
    return new WebsocketMessageSendException(
        "Failed to send TextMessage", t);
  }

  private WebsocketMessageSendException(String msg) {
    super(msg);
  }

  private WebsocketMessageSendException(String msg, Throwable t) {
    super(msg, t);
  }
}
