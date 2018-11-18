package com.github.lambda.gateway.websocket.pubsub.kafka;

import com.github.lambda.gateway.websocket.pubsub.WebsocketConsumer;
import com.github.lambda.gateway.websocket.session.WebsocketSessionRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WebsocketKafkaConsumer implements WebsocketConsumer {

  private WebsocketSessionRegistry registry;

  public WebsocketKafkaConsumer(WebsocketSessionRegistry registry) {
    this.registry = registry;
  }

  @KafkaListener(
      topics = WebsocketKafkaConfig.TOPIC_RAW,
      containerFactory = WebsocketKafkaConfig.CONSUMER_RAW)
  @Override
  public void listen(String message) {
    log.info(message);

    // TODO: put into DLQ if failed to parse
  }
}
