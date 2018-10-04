package com.github.lambda.playground.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * ProductOption
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-10-04T19:55:12.531+09:00")

public class ProductOption   {
  @JsonProperty(value = "id")
  private String id;

  @JsonProperty(value = "name")
  private String name;

  @JsonProperty(value = "price")
  private String price;

  @JsonProperty(value = "description")
  private String description;

  @JsonProperty(value = "onSale")
  private String onSale;



  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }



  /**
   * Get name
   * @return name
  **/
  @ApiModelProperty(value = "")


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }



  /**
   * Get price
   * @return price
  **/
  @ApiModelProperty(value = "")


  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }



  /**
   * Get description
   * @return description
  **/
  @ApiModelProperty(value = "")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }



  /**
   * Get onSale
   * @return onSale
  **/
  @ApiModelProperty(value = "")


  public String getOnSale() {
    return onSale;
  }

  public void setOnSale(String onSale) {
    this.onSale = onSale;
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

