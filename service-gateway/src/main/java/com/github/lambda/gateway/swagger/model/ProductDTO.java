package com.github.lambda.gateway.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.lambda.gateway.swagger.model.ProductItemDTO;
import com.github.lambda.gateway.swagger.model.ProductOptionDTO;
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
 * ProductDTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-10-15T14:42:52.804+09:00")

public class ProductDTO   {
  @JsonProperty(value = "item")
  private ProductItemDTO item;

  @JsonProperty(value = "options")
  @Valid
  @Builder.Default
  private List<ProductOptionDTO> options = new ArrayList<>();



  /**
   * Get item
   * @return item
  **/
  @ApiModelProperty(value = "")

  @Valid

  public ProductItemDTO getItem() {
    return item;
  }

  public void setItem(ProductItemDTO item) {
    this.item = item;
  }



  public ProductDTO addOptionsItem(ProductOptionDTO optionsItem) {
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

  public List<ProductOptionDTO> getOptions() {
    return options;
  }

  public void setOptions(List<ProductOptionDTO> options) {
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

