package com.github.lambda.gateway.common;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Request {

  /**
   * @throws NumberFormatException
   */
  public static List<Long> parseCommaSeparatedLongValues(String query, int max) {
    List<Long> longValues = new ArrayList<>();

    if (StringUtils.isEmpty(query)) {
      return longValues;
    }

    List<String> splitted = Arrays.asList(query.trim().split(","));
    for (int i = 0; i < splitted.size() && i < max; i++) {
      Long parsed = Long.valueOf(splitted.get(i));
      longValues.add(parsed);
    }

    return longValues;
  }
}
