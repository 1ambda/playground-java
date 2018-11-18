package com.github.lambda.gateway.websocket.pubsub.kafka;

import com.github.lambda.gateway.websocket.pubsub.WebsocketProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component(WebsocketKafkaProducer.TYPE)
public class WebsocketKafkaProducer implements WebsocketProducer {

  public static final String TYPE = "KAFKA";

  private KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  public WebsocketKafkaProducer(@Qualifier(WebsocketKafkaConfig.PRODUCER_RAW)
                                    KafkaTemplate<String, String> kafkaTemplate) {

    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void send(String payload, String shardKey) {
    // use `DefaultPartitioner` for partition key (`shard` = `username`)
    kafkaTemplate.send(WebsocketKafkaConfig.TOPIC_RAW, shardKey, payload);
  }
}
