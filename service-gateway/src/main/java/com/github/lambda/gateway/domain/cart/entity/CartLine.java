package com.github.lambda.gateway.domain.cart.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lambda.gateway.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(
    name = "`CartLine`",
    indexes = {
        @Index(name = "`idx_CartLine_createdAt`", columnList = "`created_at`", unique = false),
        @Index(name = "`idx_CartLine_deletedAt`", columnList = "`deleted_at`", unique = false),
        @Index(name = "`idx_CartLine_locked`", columnList = "`locked`", unique = false),
        @Index(name = "`idx_CartLine_cartId`", columnList = "`cart_id`", unique = false),
        @Index(name = "`idx_CartLine_productId`", columnList = "`product_id`", unique = false),
    })
public class CartLine extends BaseEntity {
  @PositiveOrZero
  @Column(name = "`index`", nullable = false)
  private Long index;

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
  @JoinColumn(name = "`cart_line_id`")
  private List<CartLineOption> cartLineOptions = new ArrayList<>();

  /**
   * functions
   */
  public void AddCartLineOption(CartLineOption orderLineOption) {
    cartLineOptions.add(orderLineOption);
  }

  public void RemoveCartLineOption(CartLineOption orderLineOption) {
    cartLineOptions.remove(orderLineOption);
  }
}
