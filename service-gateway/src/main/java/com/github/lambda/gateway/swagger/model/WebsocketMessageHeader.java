package com.github.lambda.gateway.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.lambda.gateway.swagger.model.Failure;
import com.github.lambda.gateway.swagger.model.WebsocketMessageSegment;
import com.github.lambda.gateway.swagger.model.WebsocketMessageStage;
import com.github.lambda.gateway.swagger.model.WebsocketMessageType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
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

  @JsonProperty(value = "segment")
  private WebsocketMessageSegment segment;

  @JsonProperty(value = "destination")
  @Valid
  @Builder.Default
  private List<String> destination = new ArrayList<>();

  @JsonProperty(value = "stage")
  private WebsocketMessageStage stage;



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
   * Get segment
   * @return segment
  **/
  @ApiModelProperty(value = "")

  @Valid

  public WebsocketMessageSegment getSegment() {
    return segment;
  }

  public void setSegment(WebsocketMessageSegment segment) {
    this.segment = segment;
  }



  public WebsocketMessageHeader addDestinationItem(String destinationItem) {
    if (this.destination == null) {
      this.destination = new ArrayList<>();
    }
    this.destination.add(destinationItem);
    return this;
  }

  /**
   * Get destination
   * @return destination
  **/
  @ApiModelProperty(value = "")


  public List<String> getDestination() {
    return destination;
  }

  public void setDestination(List<String> destination) {
    this.destination = destination;
  }



  /**
   * Get stage
   * @return stage
  **/
  @ApiModelProperty(value = "")

  @Valid

  public WebsocketMessageStage getStage() {
    return stage;
  }

  public void setStage(WebsocketMessageStage stage) {
    this.stage = stage;
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

