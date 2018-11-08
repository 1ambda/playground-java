package com.github.lambda.gateway.domain.activity.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ActivityAction {
  LOGIN("LOGIN"),
  LOGOUT("LOGOUT"),
  TEST("TEST");

  private String value;

  ActivityAction(String value) {
    this.value = value;
  }

  @JsonValue
  public String value() {
    return value;
  }
}
