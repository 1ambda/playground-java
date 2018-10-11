package com.github.lambda.playground.domain.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lambda.playground.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(
    name = "`Image`",
    indexes = {
      @Index(name = "idx_Image_createdAt", columnList = "created_at", unique = false),
      @Index(name = "idx_Image_deletedAt", columnList = "deleted_at", unique = false),
      @Index(name = "idx_Image_locked", columnList = "locked", unique = false),
    },
    uniqueConstraints = {})
public class Image extends BaseEntity {
  @Size(min = 0, max = 255)
  @Column(name = "`name`", nullable = false)
  private String name;

  @Size(min = 0, max = 255)
  @Column(name = "`type`", nullable = false)
  private String type;

  @Size(min = 0, max = 65535)
  @Column(name = "`path`", nullable = false, columnDefinition = "TEXT")
  private String path;

  /** relations */

  /** functions */
}
