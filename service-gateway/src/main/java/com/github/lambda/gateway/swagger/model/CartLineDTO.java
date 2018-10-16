package com.github.lambda.gateway.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.lambda.gateway.swagger.model.CartLineOptionDTO;
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
 * CartLineDTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Validated

public class CartLineDTO   {
  @JsonProperty(value = "cartLineID")
  private Long cartLineID;

  @JsonProperty(value = "createdAt")
  private Long createdAt;

  @JsonProperty(value = "updatedAt")
  private Long updatedAt;

  @JsonProperty(value = "totalPrice")
  private String totalPrice;

  @JsonProperty(value = "index")
  private Integer index;

  @JsonProperty(value = "quantity")
  private Long quantity;

  @JsonProperty(value = "productPrice")
  private String productPrice;

  @JsonProperty(value = "productId")
  private Long productId;

  @JsonProperty(value = "cartLineOptions")
  @Valid
  @Builder.Default
  private List<CartLineOptionDTO> cartLineOptions = new ArrayList<>();



  /**
   * Get cartLineID
   * @return cartLineID
  **/
  @ApiModelProperty(value = "")


  public Long getCartLineID() {
    return cartLineID;
  }

  public void setCartLineID(Long cartLineID) {
    this.cartLineID = cartLineID;
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
   * Get totalPrice
   * @return totalPrice
  **/
  @ApiModelProperty(value = "")


  public String getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(String totalPrice) {
    this.totalPrice = totalPrice;
  }



  /**
   * Get index
   * @return index
  **/
  @ApiModelProperty(value = "")


  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }



  /**
   * Get quantity
   * @return quantity
  **/
  @ApiModelProperty(value = "")


  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }



  /**
   * Get productPrice
   * @return productPrice
  **/
  @ApiModelProperty(value = "")


  public String getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(String productPrice) {
    this.productPrice = productPrice;
  }



  /**
   * Get productId
   * @return productId
  **/
  @ApiModelProperty(value = "")


  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }



  public CartLineDTO addCartLineOptionsItem(CartLineOptionDTO cartLineOptionsItem) {
    if (this.cartLineOptions == null) {
      this.cartLineOptions = new ArrayList<>();
    }
    this.cartLineOptions.add(cartLineOptionsItem);
    return this;
  }

  /**
   * Get cartLineOptions
   * @return cartLineOptions
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CartLineOptionDTO> getCartLineOptions() {
    return cartLineOptions;
  }

  public void setCartLineOptions(List<CartLineOptionDTO> cartLineOptions) {
    this.cartLineOptions = cartLineOptions;
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

