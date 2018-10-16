package com.github.lambda.gateway.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.lambda.gateway.swagger.model.CartLineDTO;
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
 * CartDTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Validated

public class CartDTO   {
  @JsonProperty(value = "cartId")
  private Long cartId;

  @JsonProperty(value = "updatedAt")
  private Long updatedAt;

  @JsonProperty(value = "totalPrice")
  private Long totalPrice;

  @JsonProperty(value = "itemCount")
  private Long itemCount;

  @JsonProperty(value = "cartLines")
  @Valid
  @Builder.Default
  private List<CartLineDTO> cartLines = new ArrayList<>();



  /**
   * Get cartId
   * @return cartId
  **/
  @ApiModelProperty(value = "")


  public Long getCartId() {
    return cartId;
  }

  public void setCartId(Long cartId) {
    this.cartId = cartId;
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
   * Get itemCount
   * @return itemCount
  **/
  @ApiModelProperty(value = "")


  public Long getItemCount() {
    return itemCount;
  }

  public void setItemCount(Long itemCount) {
    this.itemCount = itemCount;
  }



  public CartDTO addCartLinesItem(CartLineDTO cartLinesItem) {
    if (this.cartLines == null) {
      this.cartLines = new ArrayList<>();
    }
    this.cartLines.add(cartLinesItem);
    return this;
  }

  /**
   * Get cartLines
   * @return cartLines
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CartLineDTO> getCartLines() {
    return cartLines;
  }

  public void setCartLines(List<CartLineDTO> cartLines) {
    this.cartLines = cartLines;
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

