package com.github.lambda.gateway.websocket.handler;

import com.github.lambda.gateway.swagger.model.WebsocketMessageBase;
import com.github.lambda.gateway.swagger.model.WebsocketMessageType;
import com.github.lambda.gateway.websocket.WebsocketErrorBuilder;
import com.github.lambda.gateway.websocket.WebsocketMessageSerializer;
import com.github.lambda.gateway.websocket.exception.WebsocketMessageSendException;
import com.github.lambda.gateway.websocket.session.WebsocketSessionRegistry;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public interface WebsocketMessageHandler<M> {

  WebsocketSessionRegistry getRegistry();

  WebsocketMessageSerializer getMessageSerializer();

  WebsocketErrorBuilder getErrorBuilder();

  void dispatch(WebSocketSession session, M message);

  default void sendBadRequestError(WebSocketSession session,
                                   Throwable t,
                                   String message,
                                   WebsocketMessageType type) {

    WebsocketErrorBuilder errorBuilder = getErrorBuilder();
    WebsocketMessageBase base = errorBuilder.buildBadRequestError(t, message, type);

    WebsocketMessageSerializer messageSerializer = getMessageSerializer();
    TextMessage serialized = messageSerializer.serialize(base);

    send(session, serialized);
  }

  default void send(WebSocketSession session, M message) {
    try {
      WebsocketMessageSerializer serializer = getMessageSerializer();
      TextMessage serialized = serializer.serialize(message);
      session.sendMessage(serialized);

    } catch (IOException e) {
      throw WebsocketMessageSendException.create(e);
    }
  }

  default void send(WebSocketSession session, TextMessage message) {
    try {
      session.sendMessage(message);

    } catch (IOException e) {
      throw WebsocketMessageSendException.create(e);
    }
  }
}
