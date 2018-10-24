package com.github.lambda.gateway.domain.cart;

import base.ServiceTest;
import base.fixture.CartFixture;
import base.fixture.CatalogFixture;
import base.fixture.UserFixture;
import com.github.lambda.gateway.domain.cart.entity.Cart;
import com.github.lambda.gateway.domain.cart.entity.CartLine;
import com.github.lambda.gateway.domain.cart.entity.CartLineOption;
import com.github.lambda.gateway.domain.cart.exception.CartAlreadyExistException;
import com.github.lambda.gateway.domain.cart.repository.CartLineRepository;
import com.github.lambda.gateway.domain.cart.repository.CartRepository;
import com.github.lambda.gateway.domain.catalog.entity.Category;
import com.github.lambda.gateway.domain.catalog.entity.Product;
import com.github.lambda.gateway.domain.catalog.entity.ProductOption;
import com.github.lambda.gateway.domain.catalog.repository.CategoryRepository;
import com.github.lambda.gateway.domain.catalog.repository.ProductOptionRepository;
import com.github.lambda.gateway.domain.catalog.repository.ProductRepository;
import com.github.lambda.gateway.domain.user.UserService;
import com.github.lambda.gateway.domain.user.entity.User;
import com.github.lambda.gateway.domain.user.repository.RoleRepository;
import com.github.lambda.gateway.swagger.model.CartDTO;
import com.github.lambda.gateway.swagger.model.CartLineDTO;
import com.github.lambda.gateway.swagger.model.CartLineOptionDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@Slf4j
@Getter
@RunWith(SpringRunner.class)
@ServiceTest
public class CartServiceTest implements CatalogFixture,
                                        UserFixture,
                                        CartFixture {

  @MockBean RoleRepository mockRoleRepository;
  @Autowired ProductRepository productRepository;
  @Autowired ProductOptionRepository productOptionRepository;
  @Autowired CategoryRepository categoryRepository;
  @Autowired CartRepository cartRepository;
  @Autowired CartLineRepository cartLineRepository;
  @Autowired UserService userService;
  @Autowired CartService cartService;

  @Test
  public void createUser_shouldGenerateCart() {
    // given
    User user = prepareUser();
    Long userId = user.getId();

    try {
      // when
      Cart cart = cartService.getUserCartOrThrow(userId);
    } catch (Exception e) {
      // then
      fail("shouldn't throw an exception");
    }
  }

  @Test(expected = CartAlreadyExistException.class)
  public void createCart_shouldThrow_whenTryingToCreateCartAgainForTheSameUser() {
    // given
    User user = prepareUser();
    Long userId = user.getId();

    // when
    cartService.handleCreateCartAction(userId);

    // then
    fail("should throw an exception");
  }

  @Test
  public void getCartDTO_shouldReturn_CartDTO() {
    // given
    User user = prepareUser();
    Long userId = user.getId();

    // when
    CartDTO found = cartService.handleGetCartRequest(userId);

    // then
    assertThat(found.getCartId()).isGreaterThan(0L);
  }

  @Transactional
  @Test
  public void addCartLine_shouldInsertCartLine() {
    // given: prepare user, cart
    User user = prepareUser();
    Long userId = user.getId();

    // given: prepare product
    Category category = prepareCategory();
    Product product = prepareProduct(category);
    List<ProductOption> productOptionList = prepareProductOptions(product);

    // given: request
    List<CartLineOptionDTO> optionsRequest = productOptionList
        .stream()
        .map(o -> {
          return CartLineOptionDTO.builder()
              .productOptionId(o.getId())
              .quantity(1L)
              .build();
        })
        .collect(Collectors.toList());

    CartLineDTO lineRequest = CartLineDTO.builder()
        .productId(product.getId())
        .quantity(1L)
        .cartLineOptions(optionsRequest)
        .build();

    // when
    CartLine cartLine = cartService.addCartLine(userId, lineRequest);

    // then: CartLine
    assertThat(cartLine).isNotNull();
    assertThat(cartLine.getProductId()).isEqualTo(lineRequest.getProductId());
    assertThat(cartLine.getQuantity()).isEqualTo(lineRequest.getQuantity());

    // then: CartLineOption
    List<CartLineOption> cartLineOptions = cartLine.getCartLineOptions();
    Map<Long, CartLineOptionDTO> productOptionIdToRequest =
        optionsRequest
            .stream()
            .collect(Collectors.toMap(CartLineOptionDTO::getProductOptionId,
                                      o -> o));

    for (CartLineOption cartLineOption : cartLineOptions) {
      CartLineOptionDTO request =
          productOptionIdToRequest.get(cartLineOption.getProductOptionId());

      assertThat(cartLineOption.getQuantity()).isEqualTo(request.getQuantity());
    }
  }

  @Transactional
  @Test
  public void removeCartLine_shouldDeleteCartLine() {
    // given: prepare Product
    Category category = prepareCategory();
    Product product = prepareProduct(category);
    Long productId = product.getId();

    // given: User, Cart
    User user = prepareUser();
    Long userId = user.getId();

    // given: CartLine
    CartLine cartLine1 = prepareCartLine(userId, productId);

    // when
    cartService.removeCartLine(userId, cartLine1.getId());
    Cart updated = cartService.getUserCartOrThrow(userId);

    // then
    assertThat(updated.getCartLines().size()).isEqualTo(0);
  }

  @Transactional
  @Test
  public void updateCartLine_shouldModifyCartLine() {
    Long productId = prepareProductInTransaction();

    // given: User, Cart
    Long userId = prepareUserInTransaction();

    // given: CartLine
    CartLine cartLine = prepareCartLine(userId, productId);
    Long cartLineId = cartLine.getId();

    // when: update quantity
    Long newQuantity = cartLine.getQuantity() + 1;
    CartLineDTO request = CartLineDTO.builder()
        .cartLineId(cartLineId)
        .quantity(newQuantity)
        .build();

    CartLine updated = cartService.updateCartLine(userId, cartLineId, request);

    // then
    assertThat(updated.getProductId()).isEqualTo(cartLine.getProductId());
    assertThat(updated.getQuantity()).isEqualTo(newQuantity);
  }

  @Test
  public void removeCartLines_shouldDelete() {
    // given
    Long userId = prepareUserInTransaction();
    Long productId = prepareProductInTransaction();

    int cartLineCount = 10;
    List<Long> cartLineIdList = prepareCartLinesInTransaction(userId, productId, cartLineCount);

    // when
    cartService.removeCartLines(userId, cartLineIdList);

    // then
    assertThat(cartLineIdList.size()).isEqualTo(cartLineCount);
    for (Long cartLineId : cartLineIdList) {
      assertThat(isCartLineDeleted(cartLineId)).isTrue();
    }
  }

  @Test
  public void removeCartLines_shouldRollback_whenExceptionOccurred() {
    // given
    Long userId = prepareUserInTransaction();
    Long productId = prepareProductInTransaction();

    int cartLineCount = 10;
    List<Long> cartLineIdList = prepareCartLinesInTransaction(userId, productId, cartLineCount);

    // given: invalid cartLineIdList
    List<Long> invalidCartLineIdList = new ArrayList<>(cartLineIdList);
    int lastCartLineIndex = invalidCartLineIdList.size() - 1;
    Long unknownCartLineId = 0L;
    invalidCartLineIdList.set(lastCartLineIndex, unknownCartLineId);

    // when: should fail while deleting last CartLine
    try {
      cartService.removeCartLines(userId, invalidCartLineIdList);
      fail("should throw an exception");
    } catch (Exception e) {
      log.info("Expected exception: " + e.getMessage());
    }

    // then
    for (Long cartLineId : cartLineIdList) {
      // is not deleted!
      assertThat(isCartLineDeleted(cartLineId)).isFalse();
    }
  }
}