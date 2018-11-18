package com.github.lambda.gateway.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lambda.gateway.swagger.model.WebsocketMessageBase;
import com.github.lambda.gateway.swagger.model.WebsocketMessageHeader;
import com.github.lambda.gateway.swagger.model.WebsocketMessageType;
import com.github.lambda.gateway.websocket.exception.WebsocketSerializeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class WebsocketMessageSerializer {
  private static ObjectMapper mapper = new ObjectMapper();

  public Optional<WebsocketMessageBase> deserialize(String payload) {

    WebsocketMessageBase parsed;

    try {
      parsed = mapper.readValue(payload, WebsocketMessageBase.class);

      WebsocketMessageHeader header = parsed.getHeader();
      if (header == null) {
        return Optional.empty();
      }

      WebsocketMessageType type = header.getType();
      if (type == null) {
        return Optional.empty();
      }

    } catch (IOException e) {
      log.error("Failed to parse websocket payload");
      return Optional.empty();
    }

    return Optional.of(parsed);
  }

  public <M> M deserialize(Class<M> clazz, String payload) {
    String serialized = null;

    try {
      M message = mapper.readValue(payload, clazz);
      return message;
    } catch (IOException e) {
      throw WebsocketSerializeException.create(e);
    }
  }

  public String serialize(Object message) {
    String serialized = null;

    try {
      serialized = mapper.writeValueAsString(message);
    } catch (JsonProcessingException e) {
      throw WebsocketSerializeException.create(e);
    }

    return serialized;
  }

  public TextMessage toTextMessage(Object message) {
    String serialized = serialize(message);

    return new TextMessage(serialized);
  }
}
