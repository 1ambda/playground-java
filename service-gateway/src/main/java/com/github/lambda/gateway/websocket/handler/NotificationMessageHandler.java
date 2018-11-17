package com.github.lambda.gateway.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lambda.gateway.swagger.model.NotificationWebsocketMessage;
import com.github.lambda.gateway.websocket.WebsocketErrorBuilder;
import com.github.lambda.gateway.websocket.WebsocketMessageSerializer;
import com.github.lambda.gateway.websocket.session.WebsocketSessionManager;
import com.github.lambda.gateway.websocket.session.WebsocketSessionRegistry;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Component
public class NotificationMessageHandler
    implements WebsocketMessageHandler<NotificationWebsocketMessage> {

  private static ObjectMapper mapper = new ObjectMapper();

  @Getter
  private WebsocketErrorBuilder errorBuilder;

  @Getter
  private WebsocketMessageSerializer messageSerializer;

  @Getter
  private WebsocketSessionRegistry registry;

  @Autowired
  public NotificationMessageHandler(WebsocketMessageSerializer messageSerializer,
                                    WebsocketSessionRegistry registry) {
    this.messageSerializer = messageSerializer;
    this.registry = registry;
  }

  @Override
  public void dispatch(WebSocketSession session,
                       NotificationWebsocketMessage message) {

    String username = WebsocketSessionManager.extractUsername(session);
    List<WebSocketSession> userSessions = registry.getUserSessions(username);

    final TextMessage serialized = messageSerializer.serialize(message);
    userSessions.parallelStream().forEach(s -> {
      send(s, serialized);
    });
  }
}
