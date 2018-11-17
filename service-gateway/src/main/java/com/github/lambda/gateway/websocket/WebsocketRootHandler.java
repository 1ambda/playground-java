package com.github.lambda.gateway.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lambda.gateway.swagger.model.NotificationWebsocketMessage;
import com.github.lambda.gateway.swagger.model.WebsocketMessageBase;
import com.github.lambda.gateway.swagger.model.WebsocketMessageHeader;
import com.github.lambda.gateway.swagger.model.WebsocketMessageType;
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

    try {
      WebsocketMessageBase parsed = null;
      parsed = mapper.readValue(payload, WebsocketMessageBase.class);

      if (log.isDebugEnabled()) {
        String username = principal.getName();
        log.info("Websocket payload (session {} / username {})\n{}",
                 sessionId, username, parsed);
      }

      WebsocketMessageHeader header = parsed.getHeader();
      if (header == null) {
        Throwable t = WebsocketInvalidPayloadException.create("Websocket header is empty");
        sendBadRequestError(session, t, null, null);
        return;
      }

      WebsocketMessageType type = header.getType();
      if (type == null) {
        Throwable t = WebsocketInvalidPayloadException.create("Websocket type is empty");
        sendBadRequestError(session, t, null, null);
        return;
      }

      switch (type) {
        case NOTIFICATION:
          NotificationWebsocketMessage message =
              mapper.readValue(payload, NotificationWebsocketMessage.class);

          notificationMessageHandler.dispatch(session, message);
          break;
      }

    } catch (Exception e) {
      log.error("Failed to dispatch payload {}", payload, e);
      sendInternalServerError(session, e, "Failed to dispatch payload", null);
    }
  }

  void sendBadRequestError(WebSocketSession session,
                           Throwable t,
                           String message,
                           WebsocketMessageType type) {

    WebsocketMessageBase base = errorBuilder.buildBadRequestError(t, message, type);
    TextMessage serialized = messageSerializer.serialize(base);

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

    WebsocketMessageBase base = errorBuilder.buildInternalServerError(t, message, type);
    TextMessage serialized = messageSerializer.serialize(base);

    try {
      session.sendMessage(serialized);

    } catch (IOException e) {
      log.error("Failed to send error websocket message due to internal server error: {}",
                base, e);
    }
  }
}
