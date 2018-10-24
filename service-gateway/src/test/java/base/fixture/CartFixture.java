package base.fixture;

import com.github.lambda.gateway.domain.cart.CartService;
import com.github.lambda.gateway.domain.cart.entity.Cart;
import com.github.lambda.gateway.domain.cart.entity.CartLine;
import com.github.lambda.gateway.swagger.model.CartLineDTO;

public interface CartFixture {

  CartService getCartService();

  default Cart prepareCart(Long userId) {
    CartService cartService = getCartService();
    Cart cart = cartService.createCart(userId);
    return cart;
  }

  default CartLine prepareCartLine(Long userId, Long productId) {
    CartService cartService = getCartService();
    CartLineDTO request = CartLineDTO.builder()
        .productId(productId)
        .quantity(1L)
        .build();

    return cartService.addCartLine(userId, request);
  }
}
