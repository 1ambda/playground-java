package com.github.lambda.gateway.domain.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;

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
    name = "`OrderLineOption`",
    indexes = {
        @Index(name = "`idx_OrderLineOption_createdAt`", columnList = "`created_at`", unique = false),
        @Index(name = "`idx_OrderLineOption_deletedAt`", columnList = "`deleted_at`", unique = false),
        @Index(name = "`idx_OrderLineOption_locked`", columnList = "`locked`", unique = false),
        @Index(name = "`idx_OrderLineOption_orderLineId`", columnList = "`order_line_id`", unique = false),
        @Index(name = "`idx_OrderLineOption_productOptionId`", columnList = "`product_option_id`", unique = false),
    })
public class OrderLineOption extends BaseEntity {
  @PositiveOrZero
  @Column(name = "`price`", nullable = false)
  private Long price;

  @PositiveOrZero
  @Column(name = "`quantity`", nullable = false)
  private Long quantity;

  /**
   * other domains
   */
  @Column(name = "`product_option_id`", nullable = false)
  private Long productOptionId;

  /**
   * relations
   */

  /**
   * functions
   */
}
