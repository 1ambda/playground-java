package com.github.lambda.gateway.domain.catalog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lambda.gateway.domain.base.BaseEntity;
import com.github.lambda.gateway.domain.catalog.exception.ProductOptionUnavailableException;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(
    name = "`Product`",
    indexes = {
        @Index(name = "idx_Product_createdAt", columnList = "created_at", unique = false),
        @Index(name = "idx_Product_deletedAt", columnList = "deleted_at", unique = false),
        @Index(name = "idx_Product_locked", columnList = "locked", unique = false),
    },
    uniqueConstraints = {})
public class Product extends BaseEntity {
  @Size(min = 0, max = 255)
  @Column(name = "`name`", nullable = false)
  private String name;

  @PositiveOrZero
  @Column(name = "`price`", nullable = false)
  private Long price;

  @Size(min = 0, max = 65535)
  @Column(name = "`description`", nullable = false, columnDefinition = "TEXT")
  private String description;

  /**
   * relations
   */
  @ToString.Exclude
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "`category_id`", nullable = false)
  private Category category;

  @ToString.Exclude
  @OneToOne(fetch = FetchType.EAGER)
  private Image image;

  @ToString.Exclude
  @Builder.Default
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
  private List<ProductOption> productOptions = new ArrayList<>();

  /**
   * functions
   */
  public void addProductOption(ProductOption productOption) {
    productOption.setProduct(this);
    productOptions.add(productOption);
  }

  public void removeProductOption(ProductOption productOption) {
    productOption.setProduct(null);
    productOptions.remove(productOption);
  }

  public Long getTotalPrice() {
    Long totalPrice = price;

    for (ProductOption productOption : productOptions) {
      totalPrice += productOption.getPrice();
    }

    return totalPrice;
  }

  /**
   * @param requestedOptionIdList
   * @throws ProductOptionUnavailableException
   */
  public void validateAvailableOptionsOrThrow(List<Long> requestedOptionIdList) {
    List<ProductOption> existingOptions = getProductOptions();

    if (existingOptions.size() == 0 || requestedOptionIdList.size() == 0) {
      return;
    }

    if (requestedOptionIdList.size() > existingOptions.size()) {
      throw ProductOptionUnavailableException.create(
          "Requested options are much more than existing options");
    }

    Map<Long, ProductOption> existingOptionMap = existingOptions
        .stream()
        .collect(Collectors.toMap(ProductOption::getId, o -> o));

    requestedOptionIdList.forEach(requestOptionId -> {
      if (!existingOptionMap.containsKey(requestOptionId)) {
        throw ProductOptionUnavailableException
            .create(requestOptionId, "does not exist");
      }

      ProductOption existing = existingOptionMap.get(requestOptionId);
      if (!existing.isAvailable()) {
        throw ProductOptionUnavailableException
            .create(requestOptionId, "unavailable");
      }
    });

    return;
  }
}
