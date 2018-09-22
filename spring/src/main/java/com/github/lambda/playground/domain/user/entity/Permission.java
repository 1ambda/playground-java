package com.github.lambda.playground.domain.user.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
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
@Table(name = "`Permission`",
    indexes = {
        @Index(name = "idx_Permission_deletedAt", columnList = "deleted_at", unique = false),
    },
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"}),
    }
)
public class Permission extends SoftDeleteEntity {
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
