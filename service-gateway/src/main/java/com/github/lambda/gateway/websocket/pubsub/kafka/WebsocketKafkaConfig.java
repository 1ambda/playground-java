package com.github.lambda.gateway.websocket.pubsub.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class WebsocketKafkaConfig {

  @Value("${application.gateway.websocket.kafka.brokers}")
  public String kafkaBrokers;

  @Value("application.gateway.websocket.kafka.consumer-group")
  public String consumerGroup;

  public static final String PRODUCER_RAW = "KafkaProducerWebsocketRaw";
  public static final String PRODUCER_DLQ = "KafkaProducerWebsocketDead";
  public static final String CONSUMER_RAW = "KafkaConsumerWebsocketRaw";

  public static final String TOPIC_RAW = "application.gateway.websocket.raw";
  public static final String TOPIC_DLQ = "application.gateway.websocket.dead";

  /**
   * producer-related
   */

  @Bean
  public ProducerFactory<String, String> producerFactory() {
    Map<String, Object> configProps = new HashMap<>();
    configProps.put(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
        kafkaBrokers);
    configProps.put(
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
        StringSerializer.class);
    configProps.put(
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
        StringSerializer.class);
    return new DefaultKafkaProducerFactory<>(configProps);
  }

  @Bean(PRODUCER_RAW)
  public KafkaTemplate<String, String> rawKafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  @Bean(PRODUCER_DLQ)
  public KafkaTemplate<String, String> deadKafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  /**
   * consumer-related
   */

  @Bean
  public ConsumerFactory<String, String> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfigs());
  }

  @Bean
  public Map<String, Object> consumerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
        kafkaBrokers);
    props.put(
        ConsumerConfig.GROUP_ID_CONFIG,
        consumerGroup);
    props.put(
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class);
    props.put(
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class);
    return props;
  }

  @Bean(CONSUMER_RAW)
  public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }
}
