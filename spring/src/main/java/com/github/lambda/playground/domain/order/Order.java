package com.github.lambda.playground.domain.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lambda.playground.domain.base.SoftDeleteEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "`Order`",
    indexes = {
        @Index(name = "idx_Order_deletedAt", columnList = "deleted_at", unique = false),
    }
)
public class Order extends SoftDeleteEntity {
  @Column(name = "`product_id`", nullable = false)
  private Long productID;

  @Column(name = "`quantity`", nullable = false)
  private Long quantity;

  @Column(name = "`total_price`", nullable = false)
  private Long totalPrice;
}
