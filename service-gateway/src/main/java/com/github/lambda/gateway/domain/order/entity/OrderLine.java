package com.github.lambda.gateway.domain.order.entity;

import java.util.ArrayList;
import java.util.List;
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
    name = "`OrderLine`",
    indexes = {
        @Index(name = "`idx_OrderLine_createdAt`", columnList = "`created_at`", unique = false),
        @Index(name = "`idx_OrderLine_deletedAt`", columnList = "`deleted_at`", unique = false),
        @Index(name = "`idx_OrderLine_locked`", columnList = "`locked`", unique = false),
        @Index(name = "`idx_OrderLine_orderId`", columnList = "`order_id`", unique = false),
        @Index(name = "`idx_OrderLine_productId`", columnList = "`product_id`", unique = false),
        @Index(name = "`idx_OrderLine_state`", columnList = "`state`", unique = false),
    })
public class OrderLine extends BaseEntity {
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

  /**
   * other domains
   */
  @Column(name = "`product_id`", nullable = false)
  private Long productId;

  /**
   * relations
   */
  @ToString.Exclude
  @Builder.Default
  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "`order_line_id`")
  private List<OrderLineOption> orderLineOptions = new ArrayList<>();

  /**
   * functions
   */
  public void AddOrderLineOption(OrderLineOption orderLineOption) {
    orderLineOptions.add(orderLineOption);
  }

  public void RemoveOrderLineOption(OrderLineOption orderLineOption) {
    orderLineOptions.remove(orderLineOption);
  }
}
