package com.github.lambda.gateway.domain.cart;

import com.github.lambda.gateway.domain.cart.entity.Cart;
import com.github.lambda.gateway.domain.cart.entity.CartLine;
import com.github.lambda.gateway.domain.cart.entity.CartLineOption;
import com.github.lambda.gateway.swagger.model.CartDTO;
import com.github.lambda.gateway.swagger.model.CartLineDTO;
import com.github.lambda.gateway.swagger.model.CartLineOptionDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartConverter {
  public CartDTO convertToCartDTO(Cart cart,
                                  List<CartLineDTO> cartLineDTOs,
                                  Long allProductsPrice) {

    CartDTO.CartDTOBuilder builder = CartDTO.builder()
        .cartId(cart.getId())
        .updatedAt(cart.getUpdateTimestamp())
        .itemCount((long) cartLineDTOs.size());

    builder.cartLines(cartLineDTOs);
    builder.totalPrice(allProductsPrice);

    return builder.build();
  }

  public CartLineDTO convertToCartLineDTO(CartLine cartLine,
                                          List<CartLineOptionDTO> cartLineOptionDTOs,
                                          Long productPrice,
                                          String productName,
                                          String productDescription,
                                          Long productTotalPrice) {

    CartLineDTO.CartLineDTOBuilder builder = CartLineDTO.builder()
        .cartLineId(cartLine.getId())
        .createdAt(cartLine.getCreateTimestamp())
        .updatedAt(cartLine.getUpdateTimestamp())
        .index(cartLine.getIndex())
        .productId(cartLine.getProductId())
        .quantity(cartLine.getQuantity());

    builder.cartLineOptions(cartLineOptionDTOs);

    builder
        .productPrice(productPrice)
        .productName(productName)
        .productDescription(productDescription)
        .totalPrice(productTotalPrice);

    return builder.build();
  }

  public CartLineOptionDTO convertToCartLineOptionDTO(CartLineOption cartLineOption,
                                                      Long productOptionPrice,
                                                      String productOptionName,
                                                      String productOptionDescription) {
    CartLineOptionDTO.CartLineOptionDTOBuilder builder = CartLineOptionDTO.builder()
        .cartLineOptionId(cartLineOption.getId())
        .createdAt(cartLineOption.getCreateTimestamp())
        .updatedAt(cartLineOption.getUpdateTimestamp())
        .productOptionId(cartLineOption.getProductOptionId())
        .quantity(cartLineOption.getQuantity());

    builder
        .productOptionPrice(productOptionPrice)
        .productOptionName(productOptionName)
        .productOptionDescription(productOptionDescription);

    return builder.build();
  }
}
