package com.github.lambda.gateway.domain.order.entity;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
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
    name = "`OrderDetail`",
    indexes = {
      @Index(name = "`idx_OrderDetail_createdAt`", columnList = "`created_at`", unique = false),
      @Index(name = "`idx_OrderDetail_deletedAt`", columnList = "`deleted_at`", unique = false),
      @Index(name = "`idx_OrderDetail_locked`", columnList = "`locked`", unique = false),
      @Index(name = "`idx_OrderDetail_state`", columnList = "`state`", unique = false),
      @Index(name = "`idx_OrderDetail_orderId`", columnList = "`order_id`", unique = false),
      @Index(name = "`idx_OrderDetail_productId`", columnList = "`product_id`", unique = false),
      @Index(name = "`idx_OrderDetail_productOptionId`", columnList = "`product_option_id`", unique = false),
    })
public class OrderDetail extends BaseEntity {
  public enum State {
    CANCELLED("CANCELLED"),
    DELIVERED("DELIVERED"),
    IN_TRANSIT("IN_TRANSIT"),
    PICKUP_AVAILABLE("PICKUP_AVAILABLE"),
    PROBLEM("PROBLEM"),
    PROCESSING("PROCESSING"),
    RETURNED("RETURNED");

    private String value;

    State(String value) {
      this.value = value;
    }

    @JsonValue
    public String value() {
      return value;
    }
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "`state`", nullable = false)
  private State state;

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
