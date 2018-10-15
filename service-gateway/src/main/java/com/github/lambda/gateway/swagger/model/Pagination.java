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
 * Pagination
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-10-15T14:42:52.804+09:00")

public class Pagination   {
  @JsonProperty(value = "itemCountPerPage")
  private Integer itemCountPerPage;

  @JsonProperty(value = "currentPageOffset")
  private Integer currentPageOffset;

  @JsonProperty(value = "totalItemCount")
  private Long totalItemCount;



  /**
   * Get itemCountPerPage
   * @return itemCountPerPage
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getItemCountPerPage() {
    return itemCountPerPage;
  }

  public void setItemCountPerPage(Integer itemCountPerPage) {
    this.itemCountPerPage = itemCountPerPage;
  }



  /**
   * Get currentPageOffset
   * @return currentPageOffset
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getCurrentPageOffset() {
    return currentPageOffset;
  }

  public void setCurrentPageOffset(Integer currentPageOffset) {
    this.currentPageOffset = currentPageOffset;
  }



  /**
   * Get totalItemCount
   * @return totalItemCount
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Long getTotalItemCount() {
    return totalItemCount;
  }

  public void setTotalItemCount(Long totalItemCount) {
    this.totalItemCount = totalItemCount;
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

