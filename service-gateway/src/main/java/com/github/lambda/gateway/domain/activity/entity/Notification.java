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
    name = "`Notification`",
    indexes = {
        @Index(name = "`idx_Notification_createdAt`", columnList = "`created_at`", unique = false),
        @Index(name = "`idx_Notification_deletedAt`", columnList = "`deleted_at`", unique = false),
        @Index(name = "`idx_Notification_locked`", columnList = "`locked`", unique = false),
        @Index(name = "`idx_Notification_activityId`", columnList = "`activity_id`", unique = false),
        @Index(name = "`idx_Notification_recipientId`", columnList = "`recipient_id`", unique = false),
        @Index(name = "`idx_Notification_segment`", columnList = "`segment`", unique = false),
        @Index(name = "`idx_Notification_platform`", columnList = "`platform`", unique = false),
        @Index(name = "`idx_Notification_state`", columnList = "`state`", unique = false),
    },
    uniqueConstraints = {
    }
)
public class Notification extends BaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(name = "`segment`", nullable = false)
  private NotificationSegment segment;

  @Enumerated(EnumType.STRING)
  @Column(name = "`platform`", nullable = false)
  private NotificationPlatform platform;

  @Enumerated(EnumType.STRING)
  @Column(name = "`state`", nullable = false)
  private NotificationState state;

  /**
   * other domains
   */

  @PositiveOrZero
  @Column(name = "`recipient_id`", nullable = false)
  private Long recipientId;

  /**
   * relations
   */
  @ToString.Exclude
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "`activity_id`")
  private Activity activity;

  /**
   * functions
   */
}
