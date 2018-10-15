package com.github.lambda.gateway.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lambda.gateway.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "`PermissionToRole`",
    indexes = {
        @Index(name = "idx_PermissionToRole_createdAt", columnList = "created_at", unique = false),
        @Index(name = "idx_PermissionToRole_deletedAt", columnList = "deleted_at", unique = false),
    },
    uniqueConstraints = {
        @UniqueConstraint(
            name = "`uniq_PermissionToRole_permissionAndRoleId`",
            columnNames = {"`permission_id`", "`role_id`",}
        ),
    }
)
public class PermissionToRole extends BaseEntity {
  @Column(name = "`permission_id`", nullable = true)
  private Long permissionId;

  @Column(name = "`role_id`", nullable = true)
  private Long roleId;

  @ToString.Exclude
  @ManyToOne(
      fetch = FetchType.LAZY
  )
  @JoinColumn(
      name = "`permission_id`",
      referencedColumnName = "`id`",
      insertable = false,
      updatable = false
  )
  private Permission permission;

  @ToString.Exclude
  @ManyToOne(
      fetch = FetchType.LAZY
  )
  @JoinColumn(
      name = "`role_id`",
      referencedColumnName = "`id`",
      insertable = false,
      updatable = false
  )
  private Role role;
}
