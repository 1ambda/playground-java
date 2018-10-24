package com.github.lambda.gateway.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
public class RequestTest {

  @Test
  public void parseCommaSeparatedLongValues_shouldReturn_withEmptyList() {
    // given 
    final String query = "";
    final int max = 1; // inconsistent with query, but should succeed
    // when
    final List<Long> result = Request.parseCommaSeparatedLongValues(query, max);

    // then 
    assertThat(result.size()).isEqualTo(0);
  }

  @Test
  public void parseCommaSeparatedLongValues_shouldReturnParsed_withLessMax() {
    // given
    final String query = "1,2,3";
    final int max = 1;

    // when
    final List<Long> result = Request.parseCommaSeparatedLongValues(query, max);

    // then
    assertThat(result.size()).isEqualTo(max);
    assertThat(result).containsExactly(1L);
  }

  @Test
  public void parseCommaSeparatedLongValues_shouldReturnParsed_withGreaterMax() {
    // given
    final String query = "1,2";
    final int max = 4;

    // when
    final List<Long> result = Request.parseCommaSeparatedLongValues(query, max);

    // then
    assertThat(result.size()).isEqualTo(2);
    assertThat(result).containsExactly(1L, 2L);
  }

  @Test
  public void parseCommaSeparatedLongValues_shouldReturn_parsedLongValues() {
    // given
    final String query = "1,2,3,4";
    final int max = 4;

    // when
    final List<Long> result = Request.parseCommaSeparatedLongValues(query, max);

    // then
    assertThat(result.size()).isEqualTo(max);
    assertThat(result).containsExactly(1L, 2L, 3L, 4L);
  }

  @Test(expected = NumberFormatException.class)
  public void parseCommaSeparatedLongValues_shouldThrow_withInvalidQuery() {
    // given
    final String query = "1,2,b,4";
    final int max = 4;

    // when
    final List<Long> result = Request.parseCommaSeparatedLongValues(query, max);

    // then
    fail("should thrown an exception");
  }
}
