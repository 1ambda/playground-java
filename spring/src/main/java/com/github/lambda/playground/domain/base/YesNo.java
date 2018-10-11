package com.github.lambda.playground.domain.base;

import com.fasterxml.jackson.annotation.JsonValue;

public enum YesNo {
  Y("Y"),
  N("N");

  private String value;

  YesNo(String value) {
    this.value = value;
  }

  @JsonValue
  public String value() {
    return value;
  }
}
