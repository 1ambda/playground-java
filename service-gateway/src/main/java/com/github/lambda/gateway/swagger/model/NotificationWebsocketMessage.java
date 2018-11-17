package com.github.lambda.gateway.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.lambda.gateway.swagger.model.NotificationWebsocketMessageBody;
import com.github.lambda.gateway.swagger.model.WebsocketMessageHeader;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * NotificationWebsocketMessage
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Validated

public class NotificationWebsocketMessage   {
  @JsonProperty(value = "header")
  private WebsocketMessageHeader header;

  @JsonProperty(value = "body")
  private NotificationWebsocketMessageBody body;



  /**
   * Get header
   * @return header
  **/
  @ApiModelProperty(value = "")

  @Valid

  public WebsocketMessageHeader getHeader() {
    return header;
  }

  public void setHeader(WebsocketMessageHeader header) {
    this.header = header;
  }



  /**
   * Get body
   * @return body
  **/
  @ApiModelProperty(value = "")

  @Valid

  public NotificationWebsocketMessageBody getBody() {
    return body;
  }

  public void setBody(NotificationWebsocketMessageBody body) {
    this.body = body;
  }


  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

