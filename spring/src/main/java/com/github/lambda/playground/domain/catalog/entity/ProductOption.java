package com.github.lambda.playground.domain.catalog.entity;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lambda.playground.domain.base.BaseEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(
    name = "`ProductOption`",
    indexes = {
      @Index(name = "idx_ProductOption_createdAt", columnList = "created_at", unique = false),
      @Index(name = "idx_ProductOption_deletedAt", columnList = "deleted_at", unique = false),
      @Index(name = "idx_ProductOption_locked", columnList = "locked", unique = false),
    },
    uniqueConstraints = {})
public class ProductOption extends BaseEntity {
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
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "`product_id`", nullable = false)
  private Product product;

  @ToString.Exclude
  @OneToOne(fetch = FetchType.EAGER)
  private Image image;

  /** functions */
}
