package com.github.lambda.gateway.domain.activity.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NotificationSegment {
  USER("USER"),           // to selected users
  GROUP("GROUP"),         // to users who belong a group
  BROADCAST("BROADCAST"), // to all, authenticated users
  PUBLIC("PUBLIC");       // to all users including anonymous

  private String value;

  NotificationSegment(String value) {
    this.value = value;
  }

  @JsonValue
  public String value() {
    return value;
  }
}
