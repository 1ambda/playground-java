package com.github.lambda.playground.domain.base;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SoftDeleteEntity extends BaseIdEntity {

  protected static final String NOT_DELETED = "deleted_at > CURRENT_TIMESTAMP OR deleted_at IS NULL";

  @Column(name = "`deleted_at`")
  protected LocalDateTime deletedAt;
}
