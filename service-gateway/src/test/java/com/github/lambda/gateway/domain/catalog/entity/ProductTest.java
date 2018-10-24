package com.github.lambda.gateway.domain.catalog.entity;

import com.github.lambda.gateway.domain.base.YesNo;
import com.github.lambda.gateway.domain.catalog.exception.ProductOptionUnavailableException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
public class ProductTest {

  @Test(expected = ProductOptionUnavailableException.class)
  public void validateAvailableOptionsOrThrow_shouldThrow_withUnavailableOptionIds() {
    // given 
    ProductOption existingOption1 = ProductOption.builder().build();
    existingOption1.setId(1L);
    ProductOption existingOption2 = ProductOption.builder().build();
    existingOption2.setId(2L);
    List<ProductOption> existingOptions = Arrays.asList(existingOption1, existingOption2);

    Product product = Product.builder()
        .productOptions(existingOptions)
        .build();

    // when
    List<Long> requestedOptionIdList = Arrays.asList(1L, 3L);
    product.validateAvailableOptionsOrThrow(requestedOptionIdList);

    // then
    fail("should throw an exception");
  }

  @Test(expected = ProductOptionUnavailableException.class)
  public void validateAvailableOptionsOrThrow_shouldThrow_whenUnavailableOption() {
    // given
    ProductOption existingOption1 = ProductOption.builder().build();
    existingOption1.setId(1L);
    ProductOption existingOption2 = ProductOption.builder().build();
    existingOption2.setId(2L);
    existingOption2.setLocked(YesNo.Y); // locked
    List<ProductOption> existingOptions = Arrays.asList(existingOption1, existingOption2);
    Product product = Product.builder()
        .productOptions(existingOptions)
        .build();

    // when: id list is correct but option2 is unavailable
    final List<Long> requestedOptionIdList = Arrays.asList(
        existingOption1.getId(), existingOption2.getId());
    product.validateAvailableOptionsOrThrow(requestedOptionIdList);

    // then
    fail("should throw an exception");
  }
}
