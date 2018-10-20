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
 * CartLineOptionRequestDTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Validated

public class CartLineOptionRequestDTO   {
  @JsonProperty(value = "cartLineOptionId")
  private Long cartLineOptionId;

  @JsonProperty(value = "productOptionId")
  private Long productOptionId;

  @JsonProperty(value = "quantity")
  private Long quantity;



  /**
   * might be null depending on the request type
   * @return cartLineOptionId
  **/
  @ApiModelProperty(value = "might be null depending on the request type")


  public Long getCartLineOptionId() {
    return cartLineOptionId;
  }

  public void setCartLineOptionId(Long cartLineOptionId) {
    this.cartLineOptionId = cartLineOptionId;
  }



  /**
   * Get productOptionId
   * @return productOptionId
  **/
  @ApiModelProperty(value = "")


  public Long getProductOptionId() {
    return productOptionId;
  }

  public void setProductOptionId(Long productOptionId) {
    this.productOptionId = productOptionId;
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

