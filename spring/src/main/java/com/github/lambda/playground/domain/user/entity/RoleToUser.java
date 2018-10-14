package com.github.lambda.playground.domain.user.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lambda.playground.domain.base.BaseEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(
    name = "`RoleToUser`",
    indexes = {
        @Index(name = "idx_RoleToUser_createdAt", columnList = "created_at", unique = false),
        @Index(name = "idx_RoleToUser_deletedAt", columnList = "deleted_at", unique = false),
    },
    uniqueConstraints = {
        @UniqueConstraint(
                name = "`uniq_RoleToUser_roleAndUserId`",
                columnNames = {"`role_id`", "`user_id`"}
        ),
    }
)
public class RoleToUser extends BaseEntity {
  @ToString.Exclude
  @ManyToOne(
      fetch = FetchType.EAGER
  )
  @JoinColumn(
      name = "`role_id`",
      referencedColumnName = "`id`"
  )
  private Role role;

  @ToString.Exclude
  @ManyToOne(
      fetch = FetchType.LAZY
  )
  @JoinColumn(
      name = "`user_id`",
      referencedColumnName = "`id`"
  )
  private User user;
}
