package com.github.lambda.gateway.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.lambda.gateway.swagger.model.NotificationWebsocketMessage;
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
 * WebsocketMessageInclusive
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Validated

public class WebsocketMessageInclusive   {
  @JsonProperty(value = "notification")
  private NotificationWebsocketMessage notification;



  /**
   * Get notification
   * @return notification
  **/
  @ApiModelProperty(value = "")

  @Valid

  public NotificationWebsocketMessage getNotification() {
    return notification;
  }

  public void setNotification(NotificationWebsocketMessage notification) {
    this.notification = notification;
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

