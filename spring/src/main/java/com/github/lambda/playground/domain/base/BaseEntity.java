package com.github.lambda.playground.domain.base;

import java.time.LocalDateTime;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.lambda.playground.common.Time;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {
  private static final long serialVersionUID = 1L;

  @Id
  @JsonProperty("_id")
  @Column(name = "`id`")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected long id;

  @Version
  @Column(name = "`version`")
  protected Long version;

  @Column(name = "`created_at`")
  protected LocalDateTime createdAt;

  @Column(name = "`updated_at`")
  protected LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = Time.getUTCDateTime();
    this.updatedAt = Time.getUTCDateTime();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = Time.getUTCDateTime();
  }

  protected static final String NOT_DELETED =
      "deleted_at > CURRENT_TIMESTAMP OR deleted_at IS NULL";

  @Column(name = "`deleted_at`")
  protected LocalDateTime deletedAt;

  public boolean isDeleted() {
    return Time.getUTCDateTime().isAfter(this.deletedAt);
  }

  @Enumerated(EnumType.STRING)
  @Builder.Default
  @Column(name = "`locked`")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected YesNo locked = YesNo.N;

  /**
   * @return true if temporarily disabled.
   */
  public boolean isLocked() {
    return YesNo.N.equals(this.locked);
  }

  /**
   * @return true if not disabled (locked) and not deleted.
   */
  public boolean isAvailable() {
    return !isLocked() && !isDeleted();
  }
}
