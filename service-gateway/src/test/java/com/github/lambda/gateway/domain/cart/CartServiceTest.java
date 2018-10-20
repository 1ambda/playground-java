package com.github.lambda.gateway.domain.cart;

import base.ServiceTest;
import base.fixture.CartFixture;
import base.fixture.CatalogFixture;
import base.fixture.UserFixture;
import com.github.lambda.gateway.domain.cart.entity.Cart;
import com.github.lambda.gateway.domain.cart.entity.CartLine;
import com.github.lambda.gateway.domain.cart.entity.CartLineOption;
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
import com.github.lambda.gateway.swagger.model.CartLineOptionRequestDTO;
import com.github.lambda.gateway.swagger.model.CartLineRequestDTO;
import lombok.Getter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

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
  @Autowired UserService userService;
  @Autowired CartService cartService;

  @Test
  public void createCart_shouldGenerate_userCart() {
    // given
    User user = prepareUser();
    Long userId = user.getId();

    // when
    CartDTO cartDTO = cartService.handleCreateCartAction(userId);

    // then
    assertThat(cartDTO).isNotNull();
  }

  @Test(expected = IllegalArgumentException.class)
  public void createCart_shouldThrow_whenTryingToCreateCartAgainForTheSameUser() {
    // given
    User user = prepareUser();
    Long userId = user.getId();
    CartDTO existing = cartService.handleCreateCartAction(userId);

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
    CartDTO created = cartService.handleCreateCartAction(userId);

    // when
    CartDTO found = cartService.handleGetCart(userId);

    // then
    assertThat(found.getCartId()).isEqualTo(created.getCartId());
  }

  @Transactional
  @Test
  public void addCartLine_shouldInsertCartLine() {
    // given: prepare user, cart
    User user = prepareUser();
    Long userId = user.getId();
    CartDTO created = cartService.handleCreateCartAction(userId);
    Long cartId = created.getCartId();
    assertThat(cartId).isGreaterThan(0L);

    // given: prepare product
    Category category = prepareCategory();
    Product product = prepareProduct(category);
    List<ProductOption> productOptionList = prepareProductOptions(product);

    // given: request
    List<CartLineOptionRequestDTO> optionsRequest = productOptionList
        .stream()
        .map(o -> {
          return CartLineOptionRequestDTO.builder()
              .productOptionId(o.getId())
              .quantity(1L)
              .build();
        })
        .collect(Collectors.toList());

    CartLineRequestDTO lineRequest = CartLineRequestDTO.builder()
        .productId(product.getId())
        .quantity(1L)
        .options(optionsRequest)
        .build();

    // when
    CartLine cartLine = cartService.addCartLine(cartId, lineRequest);

    // then: CartLine
    assertThat(cartLine).isNotNull();
    assertThat(cartLine.getProductId()).isEqualTo(lineRequest.getProductId());
    assertThat(cartLine.getQuantity()).isEqualTo(lineRequest.getQuantity());

    // then: CartLineOption
    List<CartLineOption> cartLineOptions = cartLine.getCartLineOptions();
    Map<Long, CartLineOptionRequestDTO> productOptionIdToRequest =
        optionsRequest
            .stream()
            .collect(Collectors.toMap(CartLineOptionRequestDTO::getProductOptionId,
                                      o -> o));

    for (CartLineOption cartLineOption : cartLineOptions) {
      CartLineOptionRequestDTO request =
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
    Long createdCartId = prepareCart(userId).getId();

    // given: CartLine
    CartLine cartLine1 = prepareCartLine(userId, productId);

    // when
    cartService.removeCartLine(userId, cartLine1.getId());
    Cart updatedCart = cartRepository.findById(createdCartId).get();

    // then
    assertThat(updatedCart.getCartLines().size()).isEqualTo(0);
  }

  @Transactional
  @Test
  public void updateCartLine_shouldModifyCartLine() {
    // given: prepare Product
    Category category = prepareCategory();
    Product product = prepareProduct(category);
    Long productId = product.getId();

    // given: User, Cart
    User user = prepareUser();
    Long userId = user.getId();
    Long createdCartId = prepareCart(userId).getId();

    // given: CartLine
    CartLine cartLine = prepareCartLine(userId, productId);
    Long cartLineId = cartLine.getId();

    Long newQuantity = cartLine.getQuantity() + 1;
    CartLineRequestDTO request = CartLineRequestDTO.builder()
        .cartLineId(cartLineId)
        .quantity(newQuantity)
        .build();

    // when
    CartLine updated = cartService.updateCartLine(userId, cartLineId, request);

    // then
    assertThat(updated.getProductId()).isEqualTo(cartLine.getProductId());
    assertThat(updated.getQuantity()).isEqualTo(newQuantity);
  }

}