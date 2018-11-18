package com.github.lambda.gateway.websocket.pubsub;

public interface WebsocketProducer {
  void send(String payload, String shardKey);
}
