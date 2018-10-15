package com.github.lambda.gateway.domain.catalog.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
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
    name = "`Category`",
    indexes = {
      @Index(name = "idx_Category_createdAt", columnList = "created_at", unique = false),
      @Index(name = "idx_Category_deletedAt", columnList = "deleted_at", unique = false),
      @Index(name = "idx_Category_locked", columnList = "locked", unique = false),
    },
    uniqueConstraints = {
      @UniqueConstraint(columnNames = {"path"}),
    })
public class Category extends BaseEntity {
  @Size(min = 0, max = 255)
  @Column(name = "`name`", nullable = false)
  private String name;

  @Size(min = 0, max = 255)
  @Column(name = "`path`", nullable = false)
  private String path;

  @Size(min = 0, max = 255)
  @Column(name = "`display_name`", nullable = false)
  private String displayName;

  @Size(min = 0, max = 65535)
  @Column(name = "`description`", nullable = false, columnDefinition = "TEXT")
  private String description;

  /** relations */
  @ToString.Exclude
  @Builder.Default
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
  private List<Product> products = new ArrayList<>();

  @Column(name = "`parent_category_id`", nullable = false)
  private Long parentCategoryId; // self-referencing relation

  /** functions */
  public void addProduct(Product product) {
    product.setCategory(this);
    products.add(product);
  }

  public void removeProduct(Product product) {
    product.setCategory(null);
    products.remove(product);
  }
}
