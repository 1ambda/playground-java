package com.github.lambda.gateway.domain.cart;

import com.github.lambda.gateway.domain.cart.entity.Cart;
import com.github.lambda.gateway.domain.catalog.CatalogService;
import com.github.lambda.gateway.swagger.model.CartDTO;
import com.github.lambda.gateway.swagger.model.CartLineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

  private CartConverter cartConverter;
  private CartActionFacade cartActionFacade;
  private CartQueryFacade cartQueryFacade;

  /**
   * other domain services
   */
  private CatalogService catalogService;

  @Autowired
  public CartService(CartConverter cartConverter,
                     CartActionFacade cartActionFacade,
                     CartQueryFacade cartQueryFacade) {

    this.cartConverter = cartConverter;
    this.cartActionFacade = cartActionFacade;
    this.cartQueryFacade = cartQueryFacade;
  }

  @Transactional
  public CartDTO createCart(Long userId) {
    Cart created = cartActionFacade.createCart(userId);
    List<CartLineDTO> cartLineDTOs = new ArrayList<>();
    Long allProductsPrice = 0L;

    CartDTO dto = cartConverter.convertToCartDTO(created,
                                                 cartLineDTOs,
                                                 allProductsPrice);

    return dto;
  }

  public CartDTO getCart() {



    return null;
  }
}
