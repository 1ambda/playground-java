package com.github.lambda.gateway.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import com.github.lambda.gateway.domain.base.BaseEntity;
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
@Table(name = "`Role`",
       indexes = {
           @Index(name = "`idx_Role_createdAt`", columnList = "`created_at`", unique = false),
           @Index(name = "`idx_Role_deletedAt`", columnList = "`deleted_at`", unique = false),
       },
       uniqueConstraints = {
           @UniqueConstraint(name = "`uniq_Role_code`", columnNames = {"code"}),
       }
)
public class Role extends BaseEntity {
  public enum Code {
    ROLE_INVALID("ROLE_INVALID"),
    ROLE_ANONYMOUS("ROLE_ANONYMOUS"),
    ROLE_USER("ROLE_USER"),
    ROLE_CUSTOMER("ROLE_CUSTOMER"),
    ROLE_SELLER("ROLE_SELLER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String value;

    Code(String value) {
      this.value = value;
    }

    @JsonValue
    public String value() {
      return value;
    }
  }

  @Enumerated(EnumType.STRING)
  @Size(min = 0, max = 255)
  @Column(name = "`code`", nullable = false)
  private Role.Code code;

  @Size(min = 0, max = 255)
  @Column(name = "`description`", nullable = false)
  private String description;

  /**
   * relations
   */

  @ToString.Exclude
  @Builder.Default
  @OneToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
      mappedBy = "role"
  )
  private List<RoleToUser> roleToUsers = new ArrayList<>();

  @ToString.Exclude
  @Builder.Default
  @OneToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
      mappedBy = "role"
  )
  private List<PermissionToRole> permissionToRoles = new ArrayList<>();

  /**
   * functions
   */

  public void addRoleToUser(RoleToUser roleToUser) {
    roleToUsers.add(roleToUser);
    roleToUser.setRole(this);
  }

  public void removeRoleToUser(RoleToUser roleToUser) {
    roleToUser.setRole(null);
    roleToUsers.remove(roleToUser);
  }

  public void addPermissionToRole(PermissionToRole permissionToRole) {
    permissionToRole.setRole(this);
    permissionToRoles.add(permissionToRole);
  }

  public void removePermissionToRole(PermissionToRole permissionToRole) {
    permissionToRole.setRole(null);
    permissionToRoles.remove(permissionToRole);
  }
}
