package com.github.lambda.gateway.websocket.handler;

import com.github.lambda.gateway.swagger.model.WebsocketMessageBase;
import com.github.lambda.gateway.websocket.WebsocketErrorBuilder;
import com.github.lambda.gateway.websocket.WebsocketMessageSerializer;
import com.github.lambda.gateway.websocket.exception.WebsocketMessageSendException;
import com.github.lambda.gateway.websocket.pubsub.WebsocketProducer;
import com.github.lambda.gateway.websocket.session.WebsocketSessionRegistry;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

public interface WebsocketMessageHandler<B> {

  WebsocketSessionRegistry getRegistry();

  WebsocketMessageSerializer getMessageSerializer();

  WebsocketErrorBuilder getErrorBuilder();

  WebsocketProducer getProducer();

  void dispatch(WebSocketSession session, B body);

  default void sendBadRequestError(WebSocketSession session,
                                   Throwable t,
                                   String message) {

    WebsocketErrorBuilder errorBuilder = getErrorBuilder();
    WebsocketMessageBase base = errorBuilder.buildBadRequestError(t, message);

    WebsocketMessageSerializer messageSerializer = getMessageSerializer();
    TextMessage serialized = messageSerializer.toTextMessage(base);

    sendBack(session, serialized);
  }

  default void sendInternalServerError(WebSocketSession session,
                                       Throwable t,
                                       String message) {

    WebsocketErrorBuilder errorBuilder = getErrorBuilder();
    WebsocketMessageBase base = errorBuilder.buildInternalServerError(t, message);

    WebsocketMessageSerializer messageSerializer = getMessageSerializer();
    TextMessage serialized = messageSerializer.toTextMessage(base);

    sendBack(session, serialized);
  }

  default void sendBack(WebSocketSession session, TextMessage message) {
    try {
      session.sendMessage(message);

    } catch (IOException e) {
      throw WebsocketMessageSendException.create(e);
    }
  }

}
