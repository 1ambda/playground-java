package com.github.lambda.gateway.domain.cart;

import base.ServiceTest;
import base.fixture.CatalogFixture;
import base.fixture.UserFixture;
import com.github.lambda.gateway.domain.catalog.repository.CategoryRepository;
import com.github.lambda.gateway.domain.catalog.repository.ProductOptionRepository;
import com.github.lambda.gateway.domain.catalog.repository.ProductRepository;
import com.github.lambda.gateway.domain.user.UserService;
import com.github.lambda.gateway.domain.user.entity.User;
import com.github.lambda.gateway.domain.user.repository.RoleRepository;
import com.github.lambda.gateway.swagger.model.CartDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

@RunWith(SpringRunner.class)
@ServiceTest
public class CartServiceTest implements CatalogFixture,
                                        UserFixture {

  @MockBean
  RoleRepository mockRoleRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  ProductOptionRepository productOptionRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  UserService userService;

  @Autowired
  CartService cartService;

  @Test
  public void createCart_shouldReturnCartDTO() {
    // given
    User user = prepareUser();
    Long userId = user.getId();

    // when
    CartDTO cartDTO = cartService.createCart(userId);

    // then
    assertThat(cartDTO).isNotNull();
  }

  @Test(expected = IllegalArgumentException.class)
  public void createCart_shouldThrow_whenTryingToCreateCartAgainForTheSameUser() {
    // given
    User user = prepareUser();
    Long userId = user.getId();
    CartDTO existing = cartService.createCart(userId);

    // when
    cartService.createCart(userId);

    // then
    fail("should throw an exception");
  }

  @Test
  public void getCartDTO_shouldReturnCartDTO() {
    // given
    User user = prepareUser();
    Long userId = user.getId();
    CartDTO created = cartService.createCart(userId);

    // when
    CartDTO found = cartService.getCartDTO(userId);

    // then
    assertThat(found.getCartId()).isEqualTo(created.getCartId());
  }

  @Test
  public void getCartDTO_shouldReturn_complexCartDTO() {

  }

  @Override
  public ProductRepository getProductRepository() {
    return productRepository;
  }

  @Override
  public ProductOptionRepository getProductOptionRepository() {
    return productOptionRepository;
  }

  @Override
  public CategoryRepository getCategoryRepository() {
    return categoryRepository;
  }

  @Override
  public UserService getUserService() {
    return userService;
  }

  @Override
  public RoleRepository getMockRoleRepository() {
    return mockRoleRepository;
  }
}