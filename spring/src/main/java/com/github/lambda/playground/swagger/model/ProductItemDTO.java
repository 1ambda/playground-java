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
 * ProductItemDTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-10-14T21:41:38.285+09:00")

public class ProductItemDTO   {
  @JsonProperty(value = "id")
  private Long id;

  @JsonProperty(value = "createdAt")
  private Long createdAt;

  @JsonProperty(value = "updatedAt")
  private Long updatedAt;

  @JsonProperty(value = "name")
  private String name;

  @JsonProperty(value = "price")
  private Long price;

  @JsonProperty(value = "description")
  private String description;

  @JsonProperty(value = "onSale")
  private String onSale;

  @JsonProperty(value = "categoryId")
  private Long categoryId;

  @JsonProperty(value = "categoryDisplayName")
  private String categoryDisplayName;

  @JsonProperty(value = "categoryPath")
  private String categoryPath;

  @JsonProperty(value = "imageId")
  private Long imageId;

  @JsonProperty(value = "imageType")
  private String imageType;

  @JsonProperty(value = "imagePath")
  private String imagePath;



  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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


  public Long getPrice() {
    return price;
  }

  public void setPrice(Long price) {
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
   * Get categoryId
   * @return categoryId
  **/
  @ApiModelProperty(value = "")


  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }



  /**
   * Get categoryDisplayName
   * @return categoryDisplayName
  **/
  @ApiModelProperty(value = "")


  public String getCategoryDisplayName() {
    return categoryDisplayName;
  }

  public void setCategoryDisplayName(String categoryDisplayName) {
    this.categoryDisplayName = categoryDisplayName;
  }



  /**
   * Get categoryPath
   * @return categoryPath
  **/
  @ApiModelProperty(value = "")


  public String getCategoryPath() {
    return categoryPath;
  }

  public void setCategoryPath(String categoryPath) {
    this.categoryPath = categoryPath;
  }



  /**
   * Get imageId
   * @return imageId
  **/
  @ApiModelProperty(value = "")


  public Long getImageId() {
    return imageId;
  }

  public void setImageId(Long imageId) {
    this.imageId = imageId;
  }



  /**
   * Get imageType
   * @return imageType
  **/
  @ApiModelProperty(value = "")


  public String getImageType() {
    return imageType;
  }

  public void setImageType(String imageType) {
    this.imageType = imageType;
  }



  /**
   * Get imagePath
   * @return imagePath
  **/
  @ApiModelProperty(value = "")


  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
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

