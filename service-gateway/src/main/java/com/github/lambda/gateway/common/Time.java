package com.github.lambda.gateway.common;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Time {
  public static LocalDateTime getCurrentUTCDateTime() {
    return LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
  }

  public static Long getCurrentTimestamp() {
    return Instant.now().toEpochMilli();
  }

  public static Instant convertToInstant(LocalDateTime localDateTime) {
    return localDateTime.toInstant(ZoneOffset.UTC);
  }

  public static Long getTimestamp(LocalDateTime localDateTime) {
    return convertToInstant(localDateTime).toEpochMilli();
  }
}
