package com.github.lambda.gateway.websocket.pubsub;

public interface WebsocketConsumer {
  void listen(String message);
}
