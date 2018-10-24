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
  @JsonProperty(value = "cartLineId")
  private Long cartLineId;

  @JsonProperty(value = "createdAt")
  private Long createdAt;

  @JsonProperty(value = "updatedAt")
  private Long updatedAt;

  @JsonProperty(value = "totalPrice")
  private Long totalPrice;

  @JsonProperty(value = "index")
  private Long index;

  @JsonProperty(value = "quantity")
  private Long quantity;

  @JsonProperty(value = "productName")
  private String productName;

  @JsonProperty(value = "productDescription")
  private String productDescription;

  @JsonProperty(value = "productPrice")
  private Long productPrice;

  @JsonProperty(value = "productId")
  private Long productId;

  @JsonProperty(value = "cartLineOptions")
  @Valid
  @Builder.Default
  private List<CartLineOptionDTO> cartLineOptions = new ArrayList<>();



  /**
   * Get cartLineId
   * minimum: 1
   * @return cartLineId
  **/
  @ApiModelProperty(value = "")

@Min(1)
  public Long getCartLineId() {
    return cartLineId;
  }

  public void setCartLineId(Long cartLineId) {
    this.cartLineId = cartLineId;
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


  public Long getTotalPrice() {
    return totalPrice;
  }

  public void setTotalPrice(Long totalPrice) {
    this.totalPrice = totalPrice;
  }



  /**
   * Get index
   * @return index
  **/
  @ApiModelProperty(value = "")


  public Long getIndex() {
    return index;
  }

  public void setIndex(Long index) {
    this.index = index;
  }



  /**
   * Get quantity
   * minimum: 0
   * @return quantity
  **/
  @ApiModelProperty(value = "")

@Min(0)
  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }



  /**
   * Get productName
   * @return productName
  **/
  @ApiModelProperty(value = "")


  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }



  /**
   * Get productDescription
   * @return productDescription
  **/
  @ApiModelProperty(value = "")


  public String getProductDescription() {
    return productDescription;
  }

  public void setProductDescription(String productDescription) {
    this.productDescription = productDescription;
  }



  /**
   * Get productPrice
   * @return productPrice
  **/
  @ApiModelProperty(value = "")


  public Long getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(Long productPrice) {
    this.productPrice = productPrice;
  }



  /**
   * Get productId
   * minimum: 1
   * @return productId
  **/
  @ApiModelProperty(value = "")

@Min(1)
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

