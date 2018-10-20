package com.github.lambda.gateway.domain.cart;

import com.github.lambda.gateway.domain.cart.entity.Cart;
import com.github.lambda.gateway.domain.cart.repository.CartLineOptionRepository;
import com.github.lambda.gateway.domain.cart.repository.CartLineRepository;
import com.github.lambda.gateway.domain.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CartQueryFacade {

  private CartRepository cartRepository;
  private CartLineRepository cartLineRepository;
  private CartLineOptionRepository cartLineOptionRepository;

  @Autowired
  public CartQueryFacade(CartRepository cartRepository,
                         CartLineRepository cartLineRepository,
                         CartLineOptionRepository cartLineOptionRepository) {

    this.cartRepository = cartRepository;
    this.cartLineRepository = cartLineRepository;
    this.cartLineOptionRepository = cartLineOptionRepository;
  }

  public Cart getCart(long userId) {
    return cartRepository.findByUserId(userId);
  }
}
