package com.github.lambda.gateway.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lambda.gateway.websocket.exception.WebsocketSerializeException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

@Component
public class WebsocketMessageSerializer {
  private static ObjectMapper mapper = new ObjectMapper();

  public TextMessage serialize(Object message) {

    String serialized = null;

    try {
      serialized = mapper.writeValueAsString(message);
    } catch (JsonProcessingException e) {
      throw WebsocketSerializeException.create(e);
    }

    return new TextMessage(serialized);
  }
}
