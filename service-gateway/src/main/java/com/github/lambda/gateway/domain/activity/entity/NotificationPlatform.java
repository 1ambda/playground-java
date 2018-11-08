package com.github.lambda.gateway.domain.activity.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NotificationPlatform {
  IOS("IOS"),
  ANDROID("ANDROID"),
  MOBILE("MOBILE"),
  WEB("WEB"),
  ALL("ALL");

  private String value;

  NotificationPlatform(String value) {
    this.value = value;
  }

  @JsonValue
  public String value() {
    return value;
  }
}
