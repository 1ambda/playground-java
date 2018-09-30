package com.github.lambda.playground.domain.user.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lambda.playground.domain.base.SoftDeleteEntity;
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
@Table(name = "`User`",
    indexes = {
        @Index(name = "idx_User_deletedAt", columnList = "deleted_at", unique = false),
    },
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"}),
    }
)
public class User extends SoftDeleteEntity {

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

  /**
   * relations
   */

  @OneToOne(
      fetch = FetchType.EAGER,
      mappedBy = "user",
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
      optional = false
  )
  private AuthIdentity authIdentity;

  @ToString.Exclude
  @Builder.Default
  @OneToMany(
      fetch = FetchType.EAGER,
      mappedBy = "user"
  )
  private List<RoleToUser> roleToUsers = new ArrayList<>();

  /**
   * functions
   */

  public void addRoleToUser(RoleToUser roleToUser) {
    roleToUser.setUser(this);
    roleToUsers.add(roleToUser);
  }

  public void removeRoleToUser(RoleToUser roleToUser) {
    roleToUser.setUser(null);
    roleToUsers.remove(roleToUser);
  }
}
