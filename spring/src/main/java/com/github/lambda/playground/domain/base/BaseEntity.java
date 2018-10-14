package com.github.lambda.playground.domain.base;

import java.time.LocalDateTime;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.lambda.playground.common.Time;
import lombok.AllArgsConstructor;
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
    this.createdAt = Time.getCurrentUTCDateTime();
    this.updatedAt = Time.getCurrentUTCDateTime();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = Time.getCurrentUTCDateTime();
  }

  protected static final String NOT_DELETED =
      "deleted_at > CURRENT_TIMESTAMP OR deleted_at IS NULL";

  @Column(name = "`deleted_at`")
  protected LocalDateTime deletedAt;

  public boolean isDeleted() {
    return Time.getCurrentUTCDateTime().isAfter(this.deletedAt);
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "`locked`")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected YesNo locked;

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

  /**
   * @return timestamp for `createdAt`
   */
  public Long getCreateTimestamp() {
    return Time.getTimestamp(createdAt);
  }

  /**
   * @return timestamp for `updatedAt`
   */
  public Long getUpdateTimestamp() {
    return Time.getTimestamp(updatedAt);
  }

  /**
   * @return timestamp for `deletedAt`
   */
  public Long getDeleteTimestamp() {
    return Time.getTimestamp(deletedAt);
  }
}
