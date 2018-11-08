package com.github.lambda.gateway.domain.activity.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NotificationState {
  SENT("SENT"),
  SEEN("SEEN"),
  DISMISSED("DISMISSED");

  private String value;

  NotificationState(String value) {
    this.value = value;
  }

  @JsonValue
  public String value() {
    return value;
  }
}
