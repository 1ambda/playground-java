package com.github.lambda.playground.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Time {
  public static LocalDateTime getUTCDateTime() {
    return LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
  }

  public static Long getTimestamp() {
    return Instant.now().toEpochMilli();
  }
}
