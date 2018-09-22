package com.github.lambda.playground.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.github.lambda.playground.swagger.model.ProductOption;
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
 * Product
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-09-22T12:23:52.059+09:00")

public class Product   {
  @JsonProperty(value = "id")
  private String id = null;

  @JsonProperty(value = "createdAt")
  private String createdAt = null;

  @JsonProperty(value = "updatedAt")
  private String updatedAt = null;

  @JsonProperty(value = "name")
  private String name = null;

  @JsonProperty(value = "price")
  private String price = null;

  @JsonProperty(value = "description")
  private String description = null;

  @JsonProperty(value = "onSale")
  private String onSale = null;

  @JsonProperty(value = "categoryID")
  private String categoryID = null;

  @JsonProperty(value = "categoryDisplayName")
  private String categoryDisplayName = null;

  @JsonProperty(value = "categoryPath")
  private String categoryPath = null;

  @JsonProperty(value = "imageID")
  private String imageID = null;

  @JsonProperty(value = "imageType")
  private String imageType = null;

  @JsonProperty(value = "imagePath")
  private String imagePath = null;

  @JsonProperty(value = "options")
  @Valid
  @Builder.Default
  private List<ProductOption> options = new ArrayList<>();



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
   * Get createdAt
   * @return createdAt
  **/
  @ApiModelProperty(value = "")


  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }



  /**
   * Get updatedAt
   * @return updatedAt
  **/
  @ApiModelProperty(value = "")


  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
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
   * Get categoryID
   * @return categoryID
  **/
  @ApiModelProperty(value = "")


  public String getCategoryID() {
    return categoryID;
  }

  public void setCategoryID(String categoryID) {
    this.categoryID = categoryID;
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
   * Get imageID
   * @return imageID
  **/
  @ApiModelProperty(value = "")


  public String getImageID() {
    return imageID;
  }

  public void setImageID(String imageID) {
    this.imageID = imageID;
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



  public Product addOptionsItem(ProductOption optionsItem) {
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

  public List<ProductOption> getOptions() {
    return options;
  }

  public void setOptions(List<ProductOption> options) {
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

