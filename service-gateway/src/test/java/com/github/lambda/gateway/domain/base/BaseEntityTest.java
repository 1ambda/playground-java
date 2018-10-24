package com.github.lambda.gateway.domain.base;

import com.github.lambda.gateway.common.Time;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class BaseEntityTest {

  @Test
  public void isDeleted_shouldReturnTrue() {
    // given
    BaseEntity baseEntity = new BaseEntity();
    LocalDateTime before = Time.getCurrentUTCDateTime().minus(1, ChronoUnit.HOURS);

    // when
    baseEntity.setDeletedAt(before);

    // then 
    assertThat(baseEntity.isDeleted()).isTrue();
  }

  @Test
  public void isAvailable_shouldReturnFalse_whenLockedOrDeleted() {
    // given
    BaseEntity deletedEntity = new BaseEntity();
    LocalDateTime before = Time.getCurrentUTCDateTime().minus(1, ChronoUnit.HOURS);
    deletedEntity.setDeletedAt(before);

    BaseEntity lockedEntity = new BaseEntity();
    lockedEntity.setLocked(YesNo.Y);

    // when + then
    assertThat(deletedEntity.isAvailable()).isFalse();
    assertThat(lockedEntity.isAvailable()).isFalse();
  }
}
