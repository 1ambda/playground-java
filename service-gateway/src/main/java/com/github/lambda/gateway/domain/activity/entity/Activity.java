package com.github.lambda.gateway.domain.activity.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lambda.gateway.domain.base.BaseEntity;
import com.github.lambda.gateway.domain.cart.entity.CartLine;
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
    name = "`Activity`",
    indexes = {
        @Index(name = "`idx_Activity_createdAt`", columnList = "`created_at`", unique = false),
        @Index(name = "`idx_Activity_deletedAt`", columnList = "`deleted_at`", unique = false),
        @Index(name = "`idx_Activity_locked`", columnList = "`locked`", unique = false),
        @Index(name = "`idx_Activity_actorId`", columnList = "`actor_id`", unique = false),
        @Index(name = "`idx_Activity_action`", columnList = "`action`", unique = false),
    },
    uniqueConstraints = {
    }
)
public class Activity extends BaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "`action`", nullable = false)
  private ActivityAction action;

  /**
   * other domains
   */

  @PositiveOrZero
  @Column(name = "`object_id`", nullable = true)
  private Long objectId; // polymorphic id (e.g product id, review id, ..)

  @PositiveOrZero
  @Column(name = "`actor_id`", nullable = false)
  private Long actorId;

  /**
   * relations
   */
  @ToString.Exclude
  @Builder.Default
  @OneToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
      mappedBy = "activity"
  )
  private List<Notification> notification = new ArrayList<>();

  /**
   * functions
   */
}
