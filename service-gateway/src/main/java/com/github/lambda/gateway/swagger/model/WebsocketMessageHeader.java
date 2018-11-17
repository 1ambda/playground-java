package com.github.lambda.gateway.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.lambda.gateway.swagger.model.Failure;
import com.github.lambda.gateway.swagger.model.WebsocketMessageType;
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
 * WebsocketMessageHeader
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Validated

public class WebsocketMessageHeader   {
  @JsonProperty(value = "failure")
  private Failure failure;

  @JsonProperty(value = "type")
  private WebsocketMessageType type;



  /**
   * Get failure
   * @return failure
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Failure getFailure() {
    return failure;
  }

  public void setFailure(Failure failure) {
    this.failure = failure;
  }



  /**
   * Get type
   * @return type
  **/
  @ApiModelProperty(value = "")

  @Valid

  public WebsocketMessageType getType() {
    return type;
  }

  public void setType(WebsocketMessageType type) {
    this.type = type;
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

