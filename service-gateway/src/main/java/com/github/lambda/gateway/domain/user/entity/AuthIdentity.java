package com.github.lambda.gateway.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import com.github.lambda.gateway.domain.base.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "`AuthIdentity`",
    indexes = {
        @Index(name = "idx_AuthIdentity_createdAt", columnList = "created_at", unique = false),
        @Index(name = "idx_AuthIdentity_deletedAt", columnList = "deleted_at", unique = false),
        @Index(name = "idx_AuthIdentity_locked", columnList = "locked", unique = false),
    },
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
    }
)
public class AuthIdentity extends BaseEntity {
  public enum Provider {
    PASSWORD("PASSWORD"),
    OAUTH_GOOGLE("OAUTH_GOOGLE"),
    OAUTH_GITHUB("OAUTH_GITHUB"),
    OAUTH_FACEBOOK("OAUTH_FACEBOOK");

    private String value;

    Provider(String value) {
      this.value = value;
    }

    @JsonValue
    public String value() {
      return value;
    }
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "`provider`", nullable = false)
  private Provider provider;

  @Size(min = 4, max = 24)
  @Column(name = "`username`", nullable = false)
  private String username;

  @ToString.Exclude
  @Column(name = "`password`", nullable = true)
  private String password;

  /**
   * relations
   */

  @ToString.Exclude
  @Where(clause = BaseEntity.NOT_DELETED)
  @OneToOne(
      fetch = FetchType.EAGER,
      optional = false
  )
  @JoinColumn(name = "`user_id`", nullable = false)
  private User user;
}
