package com.github.lambda.gateway.websocket.session;

import org.springframework.web.socket.WebSocketSession;

import java.util.List;

public interface WebsocketSessionRegistry {
  List<WebSocketSession> getUserSessions(String username);
}
