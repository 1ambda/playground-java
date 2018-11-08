package com.github.lambda.gateway.domain.cart;

import com.github.lambda.gateway.common.Time;
import com.github.lambda.gateway.domain.cart.entity.Cart;
import com.github.lambda.gateway.domain.cart.entity.CartLine;
import com.github.lambda.gateway.domain.cart.repository.CartLineOptionRepository;
import com.github.lambda.gateway.domain.cart.repository.CartLineRepository;
import com.github.lambda.gateway.domain.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CartActionFacade {
  private CartRepository cartRepository;
  private CartLineRepository cartLineRepository;
  private CartLineOptionRepository cartLineOptionRepository;

  @Autowired
  public CartActionFacade(CartRepository cartRepository,
                          CartLineRepository cartLineRepository,
                          CartLineOptionRepository cartLineOptionRepository) {

    this.cartRepository = cartRepository;
    this.cartLineRepository = cartLineRepository;
    this.cartLineOptionRepository = cartLineOptionRepository;
  }

  public Cart createCart(Long userId) {
    Cart cart = Cart.builder()
        .userId(userId)
        .build();

    cartRepository.save(cart);
    return cart;
  }

  public CartLine addCartLine(Long userId, CartLine request) {
    Cart cart = cartRepository.findByUserId(userId);

    if (cart == null) {
      throw new IllegalStateException("Cart does not exist");
    }

    cart.addCartLine(request);
    cartRepository.save(cart);

    int index = cart.getCartLines().size() - 1;
    CartLine created = cart.getCartLines().get(index);

    return created;
  }

  public CartLine removeCartLine(Long userId, Long cartLineId) {
    Cart cart = cartRepository.findByUserId(userId);
    List<CartLine> cartLines = cart.getCartLines();

    CartLine cartLine = cartLines
        .stream()
        .filter(line -> cartLineId == line.getId())
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("CartLine does not exist"));

    LocalDateTime now = Time.getCurrentUTCDateTime();

    // soft delete CartLine
    cartLine.setDeletedAt(now);

    // soft delete CartLineOption
    cartLine.getCartLineOptions()
        .forEach(cartLineOption -> {
          cartLineOption.setDeletedAt(now);
        });

    // remove relation from CartLine w/ Cart
    cart.removeCartLine(cartLine);
    cartLineRepository.save(cartLine);
    cartRepository.save(cart);

    return cartLine;
  }

  public CartLine updateCartLineQuantity(Long userId, Long cartLineId, Long quantity) {
    Cart cart = cartRepository.findByUserId(userId);
    List<CartLine> cartLines = cart.getCartLines();

    CartLine existing = cartLines
        .stream()
        .filter(line -> cartLineId == line.getId())
        .findFirst()
        .orElseThrow(() -> new NoSuchElementException("CartLine does not exist"));

    existing.setQuantity(quantity);
    cartRepository.save(cart);

    return existing;
  }
}
