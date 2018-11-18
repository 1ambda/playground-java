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
* Gets or Sets WebsocketMessageSegment
*/
public enum WebsocketMessageSegment {
  
    USER("User"),
    
    GROUP("Group"),
    
    UNIVERSE("Universe");

private String value;

WebsocketMessageSegment(String value) {
this.value = value;
}

@Override
@JsonValue
public String toString() {
return String.valueOf(value);
}

@JsonCreator
public static WebsocketMessageSegment fromValue(String text) {
for (WebsocketMessageSegment b : WebsocketMessageSegment.values()) {
if (String.valueOf(b.value).equals(text)) {
return b;
}
}
return null;
}
}
