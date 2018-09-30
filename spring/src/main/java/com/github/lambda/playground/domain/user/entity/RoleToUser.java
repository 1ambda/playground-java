package com.github.lambda.playground.domain.user.entity;

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
@Table(
    name = "`RoleToUser`",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"`role_id`", "`user_id`"}),
    }
)
public class RoleToUser extends BaseIdEntity {
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
