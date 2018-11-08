package base.fixture;

import com.github.lambda.gateway.domain.cart.CartService;
import com.github.lambda.gateway.domain.cart.entity.Cart;
import com.github.lambda.gateway.domain.cart.entity.CartLine;
import com.github.lambda.gateway.domain.cart.repository.CartLineRepository;
import com.github.lambda.gateway.swagger.model.CartLineDTO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface CartFixture {

  CartService getCartService();

  CartLineRepository getCartLineRepository();

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

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  default List<Long> prepareCartLinesInTransaction(Long userId, Long productId, int cartLineCount) {
    List<Long> cartLineIdList = new ArrayList<>();

    for (int i = 0; i < cartLineCount; i++) {
      CartLine cartLine = prepareCartLine(userId, productId);
      cartLineIdList.add(cartLine.getId());
    }

    return cartLineIdList;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  default boolean isCartLineDeleted(Long cartLineId) {
    CartLineRepository cartLineRepository = getCartLineRepository();
    CartLine cartLine = cartLineRepository.findById(cartLineId).get();
    return cartLine.isDeleted() && cartLine.getCartId() == null;
  }
}
