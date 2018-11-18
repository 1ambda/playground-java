package com.github.lambda.gateway.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lambda.gateway.swagger.model.*;
import com.github.lambda.gateway.websocket.exception.WebsocketInvalidPayloadException;
import com.github.lambda.gateway.websocket.handler.NotificationMessageHandler;
import com.github.lambda.gateway.websocket.session.WebsocketSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@Slf4j
@Component
public class WebsocketRootHandler extends TextWebSocketHandler {

  private static ObjectMapper mapper = new ObjectMapper();

  private NotificationMessageHandler notificationMessageHandler;
  private WebsocketSessionManager sessionManager;
  private WebsocketErrorBuilder errorBuilder;
  private WebsocketMessageSerializer messageSerializer;

  @Autowired
  public WebsocketRootHandler(
      WebsocketSessionManager sessionManager,
      NotificationMessageHandler notificationMessageHandler,
      WebsocketErrorBuilder errorBuilder,
      WebsocketMessageSerializer messageSerializer) {

    this.sessionManager = sessionManager;
    this.notificationMessageHandler = notificationMessageHandler;
    this.errorBuilder = errorBuilder;
    this.messageSerializer = messageSerializer;
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    try {
      this.sessionManager.addSession(session);
    } catch (Exception e) {
      log.error("Failed to add new websocket session {}", session.getId());
      session.close(CloseStatus.SERVER_ERROR);
      return;
    }

    super.afterConnectionEstablished(session);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    try {
      this.sessionManager.removeSession(session);
    } catch (Exception e) {
      log.error("Failed to remove websocket session {}", session.getId());
    }

    super.afterConnectionClosed(session, status);
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    log.error("Unknown websocket error occurred", exception);

    super.handleTransportError(session, exception);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage raw) throws Exception {
    String sessionId = session.getId();
    Principal principal = session.getPrincipal();
    String payload = raw.getPayload();

    WebsocketMessageBase parsed = null;

    try {
      parsed = mapper.readValue(payload, WebsocketMessageBase.class);

      Optional<WebsocketMessageBase> opt = messageSerializer.deserialize(payload);
      if (!opt.isPresent()) {
        Throwable t = WebsocketInvalidPayloadException.create("Websocket header or type is empty");
        sendBadRequestError(session, t, null, null);
        return;
      }

      WebsocketMessageHeader header = parsed.getHeader();
      WebsocketMessageType type = header.getType();

      switch (type) {
        case NOTIFICATION:
          NotificationWebsocketMessage message =
              messageSerializer.deserialize(NotificationWebsocketMessage.class, payload);
          NotificationWebsocketMessageBody body = message.getBody();

          notificationMessageHandler.dispatch(session, body);
          break;
      }

    } catch (Exception e) {
      log.error("Failed to dispatch payload {}", payload, e);
      sendInternalServerError(session, e, "Failed to dispatch payload", null);
    }

    if (log.isDebugEnabled()) {
      String username = principal.getName();
      log.info("Websocket payload (session {} / username {})\n{}",
               sessionId, username, parsed);
    }
  }

  void sendBadRequestError(WebSocketSession session,
                           Throwable t,
                           String message,
                           WebsocketMessageType type) {

    WebsocketMessageBase base = errorBuilder.buildBadRequestError(t, message);
    TextMessage serialized = messageSerializer.toTextMessage(base);

    try {
      session.sendMessage(serialized);

    } catch (IOException e) {
      log.error("Failed to send error websocket message due to bad request: {}",
                base, e);
    }
  }

  void sendInternalServerError(WebSocketSession session,
                               Throwable t,
                               String message,
                               WebsocketMessageType type) {

    WebsocketMessageBase base = errorBuilder.buildInternalServerError(t, message);
    TextMessage serialized = messageSerializer.toTextMessage(base);

    try {
      session.sendMessage(serialized);

    } catch (IOException e) {
      log.error("Failed to send error websocket message due to internal server error: {}",
                base, e);
    }
  }
}
