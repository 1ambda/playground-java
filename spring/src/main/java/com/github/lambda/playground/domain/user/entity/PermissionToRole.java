package com.github.lambda.playground.domain.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lambda.playground.domain.base.BaseIdEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "`PermissionToRole`",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"`permission_id`", "`role_id`",}),
    }
)
public class PermissionToRole extends BaseIdEntity {
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
