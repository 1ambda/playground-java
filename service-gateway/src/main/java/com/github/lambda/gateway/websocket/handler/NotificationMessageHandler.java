package com.github.lambda.gateway.websocket.handler;

import com.github.lambda.gateway.swagger.model.*;
import com.github.lambda.gateway.websocket.WebsocketErrorBuilder;
import com.github.lambda.gateway.websocket.WebsocketMessageSerializer;
import com.github.lambda.gateway.websocket.exception.WebsocketInvalidPayloadException;
import com.github.lambda.gateway.websocket.pubsub.WebsocketProducer;
import com.github.lambda.gateway.websocket.pubsub.kafka.WebsocketKafkaProducer;
import com.github.lambda.gateway.websocket.session.WebsocketSessionManager;
import com.github.lambda.gateway.websocket.session.WebsocketSessionRegistry;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;

@Component
public class NotificationMessageHandler
    implements WebsocketMessageHandler<NotificationWebsocketMessageBody> {

  @Getter
  private WebsocketErrorBuilder errorBuilder;

  @Getter
  private WebsocketMessageSerializer messageSerializer;

  @Getter
  private WebsocketSessionRegistry registry;

  @Getter
  private WebsocketProducer producer;

  @Autowired
  public NotificationMessageHandler(WebsocketErrorBuilder errorBuilder,
                                    WebsocketMessageSerializer messageSerializer,
                                    WebsocketSessionRegistry registry,
                                    @Qualifier(WebsocketKafkaProducer.TYPE)
                                        WebsocketProducer producer) {
    this.errorBuilder = errorBuilder;
    this.messageSerializer = messageSerializer;
    this.registry = registry;
    this.producer = producer;
  }

  @Override
  public void dispatch(WebSocketSession session,
                       NotificationWebsocketMessageBody body) {

    Throwable t = WebsocketInvalidPayloadException.create("Hello");
    WebsocketMessageBase errorMessage = errorBuilder.buildBadRequestError(t, null);

    String username = WebsocketSessionManager.extractUsername(session);

    // set target
    WebsocketMessageHeader header = errorMessage.getHeader().toBuilder()
        .stage(WebsocketMessageStage.BEFOREPRODUCE)
        .segment(WebsocketMessageSegment.USER)
        .destination(Collections.singletonList(username))
        .build();
    errorMessage.setHeader(header);

    String serialized = messageSerializer.serialize(errorMessage);
    producer.send(serialized, username);
  }
}
