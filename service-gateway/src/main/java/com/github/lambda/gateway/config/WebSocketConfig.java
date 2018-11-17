package com.github.lambda.gateway.config;

import com.github.lambda.gateway.websocket.WebsocketRootHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  @Autowired WebsocketRootHandler webSocketRootHandler;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(webSocketRootHandler, "/ws/authenticated")
        .setAllowedOrigins("*") // TODO: CORS variable
        .withSockJS();
  }
}
