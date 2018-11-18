package com.github.lambda.gateway.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

  import com.fasterxml.jackson.annotation.JsonCreator;

/**
* Gets or Sets WebsocketMessageStage
*/
public enum WebsocketMessageStage {
  
    BEFOREPRODUCE("BeforeProduce"),
    
    BEFOREBROADCAST("BeforeBroadcast");

private String value;

WebsocketMessageStage(String value) {
this.value = value;
}

@Override
@JsonValue
public String toString() {
return String.valueOf(value);
}

@JsonCreator
public static WebsocketMessageStage fromValue(String text) {
for (WebsocketMessageStage b : WebsocketMessageStage.values()) {
if (String.valueOf(b.value).equals(text)) {
return b;
}
}
return null;
}
}
