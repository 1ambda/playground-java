package base.fixture;

import com.github.lambda.gateway.domain.cart.CartActionFacade;
import com.github.lambda.gateway.domain.cart.CartService;
import com.github.lambda.gateway.domain.cart.entity.Cart;
import com.github.lambda.gateway.domain.cart.entity.CartLine;
import com.github.lambda.gateway.domain.cart.entity.CartLineOption;
import com.github.lambda.gateway.domain.cart.repository.CartRepository;
import com.github.lambda.gateway.swagger.model.CartLineRequestDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface CartFixture {

  CartService getCartService();

  default Cart prepareCart(Long userId) {
    CartService cartService = getCartService();
    Cart cart = cartService.createCart(userId);
    return cart;
  }

  default CartLine prepareCartLine(Long userId, Long productId) {
    CartService cartService = getCartService();
    CartLineRequestDTO request = CartLineRequestDTO.builder()
        .productId(productId)
        .quantity(1L)
        .build();

    return cartService.addCartLine(userId, request);
  }

//  default List<CartLineOption> prepareCartLineOptions(Long userId,
//                                                      Long cartLineId,
//                                                      List<Long> productOptionIdList) {
//
//    CartService cartService = getCartService();
//
//    List<CartLineOption> cartLineOptions = productOptionIdList
//        .stream()
//        .map(productOptionId -> {
//
//        })
//
//
//  }
}
