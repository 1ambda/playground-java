package com.github.lambda.gateway.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.lambda.gateway.swagger.model.CartLineOptionRequestDTO;
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
 * CartLineRequestDTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Validated

public class CartLineRequestDTO   {
  @JsonProperty(value = "cartLineId")
  private Long cartLineId;

  @JsonProperty(value = "productId")
  private Long productId;

  @JsonProperty(value = "quantity")
  private Long quantity;

  @JsonProperty(value = "options")
  @Valid
  @Builder.Default
  private List<CartLineOptionRequestDTO> options = new ArrayList<>();



  /**
   * might be null depending on the request type
   * @return cartLineId
  **/
  @ApiModelProperty(value = "might be null depending on the request type")


  public Long getCartLineId() {
    return cartLineId;
  }

  public void setCartLineId(Long cartLineId) {
    this.cartLineId = cartLineId;
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



  public CartLineRequestDTO addOptionsItem(CartLineOptionRequestDTO optionsItem) {
    if (this.options == null) {
      this.options = new ArrayList<>();
    }
    this.options.add(optionsItem);
    return this;
  }

  /**
   * Get options
   * @return options
  **/
  @ApiModelProperty(value = "")

  @Valid

  public List<CartLineOptionRequestDTO> getOptions() {
    return options;
  }

  public void setOptions(List<CartLineOptionRequestDTO> options) {
    this.options = options;
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

