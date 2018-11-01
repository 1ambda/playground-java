package com.github.lambda.gateway.domain.cart.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lambda.gateway.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
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
    name = "`Cart`",
    indexes = {
        @Index(name = "`idx_Cart_createdAt`", columnList = "`created_at`", unique = false),
        @Index(name = "`idx_Cart_deletedAt`", columnList = "`deleted_at`", unique = false),
        @Index(name = "`idx_Cart_locked`", columnList = "`locked`", unique = false),
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "`uniq_Cart_userId`", columnNames = {"`user_id`"}),
    }
)
public class Cart extends BaseEntity {
  /**
   * other domains
   */
  @Column(name = "`user_id`", nullable = false)
  private Long userId;

  /**
   * relations
   */
  @ToString.Exclude
  @Builder.Default
  @OneToMany(
      fetch = FetchType.EAGER,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
      mappedBy = "cart"
  )
  private List<CartLine> cartLines = new ArrayList<>();

  /**
   * functions
   */
  public void addCartLine(CartLine cartLine) {
    cartLine.setCart(this);
    cartLines.add(cartLine);
  }

  public void removeCartLine(CartLine cartLine) {
    cartLine.setCart(null);
    cartLines.remove(cartLine);
  }
}
