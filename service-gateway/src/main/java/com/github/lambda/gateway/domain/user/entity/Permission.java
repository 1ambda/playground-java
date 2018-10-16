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
@Table(name = "`Permission`",
       indexes = {
           @Index(name = "idx_Permission_createdAt", columnList = "created_at", unique = false),
           @Index(name = "idx_Permission_deletedAt", columnList = "deleted_at", unique = false),
       },
       uniqueConstraints = {
           @UniqueConstraint(columnNames = {"code"}),
       }
)
public class Permission extends BaseEntity {
  public enum Code {
    INVALID("INVALID"),
    SETTING("SETTING"),
    ORDER("ORDER"),
    STOCK("STOCK"),
    PAYMENT("PAYMENT");

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
  private Code code;

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
      mappedBy = "permission"
  )
  private List<PermissionToRole> permissionToRoles = new ArrayList<>();

  /**
   * functions
   */

  public void addPermissionToRole(PermissionToRole permissionToRole) {
    permissionToRole.setPermission(this);
    permissionToRoles.add(permissionToRole);
  }

  public void removePermissionToRole(PermissionToRole permissionToRole) {
    permissionToRole.setPermission(null);
    permissionToRoles.remove(permissionToRole);
  }
}
