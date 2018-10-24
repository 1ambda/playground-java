package com.github.lambda.gateway.domain.cart.controller;

import com.github.lambda.gateway.domain.cart.CartService;
import com.github.lambda.gateway.swagger.model.CartDTO;
import com.github.lambda.gateway.swagger.model.CartLineDTO;
import com.github.lambda.gateway.swagger.model.CartLineOptionDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CartControllerTest {

  @Mock private CartService mockCartService;

  @Autowired
  private CartController cartControllerUnderTest;

  @Before
  public void setUp() {
  }

  @Test
  public void testAddUserCartLine() {
    // given 
    final CartLineDTO body = null;
    final ResponseEntity<CartLineDTO> expectedResult = null;

    // when 
    final ResponseEntity<CartLineDTO> result = cartControllerUnderTest.addUserCartLine(body);

    // then 
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  public void testClearUserCartLines() {
    // given 
    final ResponseEntity<Void> expectedResult = null;

    // when 
    final ResponseEntity<Void> result = cartControllerUnderTest.clearUserCartLines();

    // then 
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  public void testGetUserCartLines() {
    // given 
    final ResponseEntity<CartDTO> expectedResult = null;

    // when 
    final ResponseEntity<CartDTO> result = cartControllerUnderTest.getUserCartLines();

    // then 
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  public void testRemoveUserCartLine() {
    // given 
    final Long lineId = 0L;
    final ResponseEntity<Void> expectedResult = null;

    // when 
    final ResponseEntity<Void> result = cartControllerUnderTest.removeUserCartLine(lineId);

    // then 
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  public void testRemoveUserCartLineOption() {
    // given 
    final Long lineId = 0L;
    final Long optionId = 0L;
    final ResponseEntity<Void> expectedResult = null;

    // when 
    final ResponseEntity<Void> result = cartControllerUnderTest.removeUserCartLineOption(lineId,
                                                                                         optionId);

    // then 
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  public void testUpdateUserCartLine() {
    // given 
    final Long lineId = 0L;
    final CartLineDTO body = null;
    final ResponseEntity<CartLineDTO> expectedResult = null;

    // when 
    final ResponseEntity<CartLineDTO> result = cartControllerUnderTest.updateUserCartLine(lineId,
                                                                                          body);

    // then 
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  public void testUpdateUserCartLineOption() {
    // given 
    final Long lineId = 0L;
    final Long optionId = 0L;
    final CartLineOptionDTO body = null;
    final ResponseEntity<CartLineOptionDTO> expectedResult = null;

    // when 
    final ResponseEntity<CartLineOptionDTO> result = cartControllerUnderTest.updateUserCartLineOption(
        lineId,
        optionId,
        body);

    // then 
    assertThat(result).isEqualTo(expectedResult);
  }
}
