package com.github.lambda.playground.domain.order;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lambda.playground.domain.base.BaseEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(
    name = "`OrderDetail`",
    indexes = {
      @Index(name = "idx_OrderDetail_createdAt", columnList = "created_at", unique = false),
      @Index(name = "idx_OrderDetail_deletedAt", columnList = "deleted_at", unique = false),
      @Index(name = "idx_OrderDetail_locked", columnList = "locked", unique = false),
      @Index(name = "idx_OrderDetail_orderId", columnList = "locked", unique = false),
      @Index(name = "idx_OrderDetail_productId", columnList = "locked", unique = false),
      @Index(name = "idx_OrderDetail_productOptionId", columnList = "locked", unique = false),
    })
public class OrderDetail extends BaseEntity {
  @PositiveOrZero
  @Column(name = "`index`", nullable = false)
  private Long index;

  @PositiveOrZero
  @Column(name = "`price`", nullable = false)
  private Long price;

  @PositiveOrZero
  @Column(name = "`quantity`", nullable = false)
  private Long quantity;

  /** other domains */
  @Column(name = "`product_id`", nullable = false)
  private Long productId;

  @Column(name = "`product_option_id`", nullable = false)
  private Long productOptionId;

  /** relations */
  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "`order_id`", nullable = false)
  private Order order;

  /** functions */
}
