package com.github.lambda.playground.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lambda.playground.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
    name = "`User`",
    indexes = {
      @Index(name = "idx_User_createdAt", columnList = "created_at", unique = false),
      @Index(name = "idx_User_deletedAt", columnList = "deleted_at", unique = false),
      @Index(name = "idx_User_locked", columnList = "locked", unique = false),
    },
    uniqueConstraints = {
      @UniqueConstraint(columnNames = {"email"}),
    })
public class User extends BaseEntity {

  @ToString.Exclude
  @Size(min = 0, max = 255)
  @Column(name = "`name`", nullable = false)
  private String name;

  @ToString.Exclude
  @Size(min = 0, max = 255)
  @Column(name = "`email`", nullable = false)
  private String email;

  @ToString.Exclude
  @Size(min = 0, max = 255)
  @Column(name = "`address`", nullable = false)
  private String address;

  /** relations */
  @OneToOne(
      fetch = FetchType.EAGER,
      mappedBy = "user",
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
      optional = false)
  private AuthIdentity authIdentity;

  @ToString.Exclude
  @Builder.Default
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
  private List<RoleToUser> roleToUsers = new ArrayList<>();

  /** functions */
  public void addRoleToUser(RoleToUser roleToUser) {
    roleToUser.setUser(this);
    roleToUsers.add(roleToUser);
  }

  public void removeRoleToUser(RoleToUser roleToUser) {
    roleToUser.setUser(null);
    roleToUsers.remove(roleToUser);
  }
}
