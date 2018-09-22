package com.github.lambda.playground.domain.base;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import lombok.Data;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Version
  @Column(name = "`version`")
  protected Long version;

  @Column(name = "`created_at`")
  protected LocalDateTime createdAt;

  @Column(name = "`updated_at`")
  protected LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
    this.updatedAt = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
  }
}
