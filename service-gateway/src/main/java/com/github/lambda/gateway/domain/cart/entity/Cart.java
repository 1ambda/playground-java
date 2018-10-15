package com.github.lambda.gateway.domain.cart.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

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
    name = "`Cart`",
    indexes = {
      @Index(name = "`idx_Cart_createdAt`", columnList = "`created_at`", unique = false),
      @Index(name = "`idx_Cart_deletedAt`", columnList = "`deleted_at`", unique = false),
      @Index(name = "`idx_Cart_locked`", columnList = "`locked`", unique = false),
      @Index(name = "`idx_Cart_userId`", columnList = "`user_id`", unique = false),
    })
public class Cart extends BaseEntity {
  /** other domains */
  @Column(name = "`user_id`", nullable = false)
  private Long userId;

  /** relations */
  @ToString.Exclude
  @Builder.Default
  @OneToMany(fetch = FetchType.EAGER)
  @JoinColumn(name = "`cart_id`")
  private List<CartLine> cartLines = new ArrayList<>();

  /** functions */
  public void addCartLine(CartLine orderDetail) {
    cartLines.add(orderDetail);
  }

  public void removeCartLine(CartLine orderDetail) {
    cartLines.remove(orderDetail);
  }
}
