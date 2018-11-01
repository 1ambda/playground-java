package com.github.lambda.gateway.domain.cart.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lambda.gateway.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(
    name = "`CartLineOption`",
    indexes = {
        @Index(name = "`idx_CartLineOption_createdAt`",
            columnList = "`created_at`",
            unique = false),
        @Index(name = "`idx_CartLineOption_deletedAt`",
            columnList = "`deleted_at`",
            unique = false),
        @Index(name = "`idx_CartLineOption_locked`", columnList = "`locked`", unique = false),
        @Index(name = "`idx_CartLineOption_cartLineId`",
            columnList = "`cart_line_id`",
            unique = false),
        @Index(name = "`idx_CartLineOption_productOptionId`",
            columnList = "`product_option_id`",
            unique = false),
    })
public class CartLineOption extends BaseEntity {
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
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "`cart_line_id`")
  private CartLine cartLine;

  /**
   * functions
   */
}
