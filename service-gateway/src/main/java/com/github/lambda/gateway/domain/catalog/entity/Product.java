package com.github.lambda.gateway.domain.catalog.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lambda.gateway.domain.base.BaseEntity;
import lombok.*;

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

  /** relations */
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

  /** functions */
  public void addProductOption(ProductOption productOption) {
    productOption.setProduct(this);
    productOptions.add(productOption);
  }

  public void removeProductOption(ProductOption productOption) {
    productOption.setProduct(null);
    productOptions.remove(productOption);
  }
}
