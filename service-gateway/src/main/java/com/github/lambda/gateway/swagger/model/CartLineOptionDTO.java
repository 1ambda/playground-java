package com.github.lambda.gateway.swagger.model;

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
 * CartLineOptionDTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Validated

public class CartLineOptionDTO   {
  @JsonProperty(value = "cartLineOptionId")
  private Long cartLineOptionId;

  @JsonProperty(value = "updatedAt")
  private Long updatedAt;

  @JsonProperty(value = "createdAt")
  private Long createdAt;

  @JsonProperty(value = "quantity")
  private Long quantity;

  @JsonProperty(value = "productOptionName")
  private String productOptionName;

  @JsonProperty(value = "productOptionDescription")
  private String productOptionDescription;

  @JsonProperty(value = "productOptionId")
  private Long productOptionId;

  @JsonProperty(value = "productOptionPrice")
  private Long productOptionPrice;



  /**
   * Get cartLineOptionId
   * minimum: 1
   * @return cartLineOptionId
  **/
  @ApiModelProperty(value = "")

@Min(1)
  public Long getCartLineOptionId() {
    return cartLineOptionId;
  }

  public void setCartLineOptionId(Long cartLineOptionId) {
    this.cartLineOptionId = cartLineOptionId;
  }



  /**
   * Get updatedAt
   * @return updatedAt
  **/
  @ApiModelProperty(value = "")


  public Long getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Long updatedAt) {
    this.updatedAt = updatedAt;
  }



  /**
   * Get createdAt
   * @return createdAt
  **/
  @ApiModelProperty(value = "")


  public Long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
  }



  /**
   * Get quantity
   * minimum: 1
   * maximum: 1
   * @return quantity
  **/
  @ApiModelProperty(value = "")

@Min(1) @Max(1) 
  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }



  /**
   * Get productOptionName
   * @return productOptionName
  **/
  @ApiModelProperty(value = "")


  public String getProductOptionName() {
    return productOptionName;
  }

  public void setProductOptionName(String productOptionName) {
    this.productOptionName = productOptionName;
  }



  /**
   * Get productOptionDescription
   * @return productOptionDescription
  **/
  @ApiModelProperty(value = "")


  public String getProductOptionDescription() {
    return productOptionDescription;
  }

  public void setProductOptionDescription(String productOptionDescription) {
    this.productOptionDescription = productOptionDescription;
  }



  /**
   * Get productOptionId
   * minimum: 1
   * @return productOptionId
  **/
  @ApiModelProperty(value = "")

@Min(1)
  public Long getProductOptionId() {
    return productOptionId;
  }

  public void setProductOptionId(Long productOptionId) {
    this.productOptionId = productOptionId;
  }



  /**
   * Get productOptionPrice
   * minimum: 0
   * @return productOptionPrice
  **/
  @ApiModelProperty(value = "")

@Min(0)
  public Long getProductOptionPrice() {
    return productOptionPrice;
  }

  public void setProductOptionPrice(Long productOptionPrice) {
    this.productOptionPrice = productOptionPrice;
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

