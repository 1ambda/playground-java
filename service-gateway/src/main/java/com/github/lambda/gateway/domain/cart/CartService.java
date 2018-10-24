package com.github.lambda.gateway.domain.cart;

import com.github.lambda.gateway.domain.cart.entity.Cart;
import com.github.lambda.gateway.domain.cart.entity.CartLine;
import com.github.lambda.gateway.domain.cart.entity.CartLineOption;
import com.github.lambda.gateway.domain.cart.exception.CartAlreadyExistException;
import com.github.lambda.gateway.domain.catalog.CatalogService;
import com.github.lambda.gateway.domain.catalog.entity.Product;
import com.github.lambda.gateway.domain.catalog.entity.ProductOption;
import com.github.lambda.gateway.security.SecurityService;
import com.github.lambda.gateway.swagger.model.CartDTO;
import com.github.lambda.gateway.swagger.model.CartLineDTO;
import com.github.lambda.gateway.swagger.model.CartLineOptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CartService {

  private CartConverter cartConverter;
  private CartActionFacade cartActionFacade;
  private CartQueryFacade cartQueryFacade;

  /**
   * other domain services
   */
  private SecurityService securityService;
  private CatalogService catalogService;

  @Autowired
  public CartService(CartConverter cartConverter,
                     CartActionFacade cartActionFacade,
                     CartQueryFacade cartQueryFacade,
                     SecurityService securityService,
                     CatalogService catalogService) {

    this.cartConverter = cartConverter;
    this.cartActionFacade = cartActionFacade;
    this.cartQueryFacade = cartQueryFacade;
    this.securityService = securityService;
    this.catalogService = catalogService;
  }

  @Transactional
  public CartDTO handleCreateCartAction(Long userId) {
    Cart created = createCart(userId);
    List<CartLineDTO> cartLineDTOs = new ArrayList<>();
    Long allProductsPrice = 0L;

    CartDTO dto = cartConverter.convertToCartDTO(created,
                                                 cartLineDTOs,
                                                 allProductsPrice);

    return dto;
  }

  @Transactional
  public Cart createCart(Long userId) {
    Cart existing = cartQueryFacade.getCart(userId);

    if (existing != null) {
      throw CartAlreadyExistException.create(userId);
    }

    Cart created = cartActionFacade.createCart(userId);

    return created;
  }

  @Transactional
  public CartLine addCartLine(Long userId, CartLineDTO request) {
    Long productId = request.getProductId();


    Long productQuantity = request.getQuantity();
    Long optionQuantity = 1L; // supports 1 option quantity only

    List<CartLineOption> cartLineOptions = request
        .getCartLineOptions()
        .stream()
        .map(optionRequest -> {
          CartLineOption option = CartLineOption.builder()
              .productOptionId(optionRequest.getProductOptionId())
              .quantity(optionQuantity)
              .build();

          return option;
        })
        .collect(Collectors.toList());

    CartLine cartLine = CartLine.builder()
        .productId(productId)
        .quantity(productQuantity)
        .index(0L)
        .build();

    for (int i = 0; i < request.getCartLineOptions().size(); i++) {
      CartLineOption cartLineOption = cartLineOptions.get(i);
      cartLine.addCartLineOption(cartLineOption);
    }

    // TODO cart full exception
    CartLine created = cartActionFacade.addCartLine(userId, cartLine);
    return created;
  }

  @Transactional
  public CartLineDTO handleAddCartLineRequest(Long userId,
                                              CartLineDTO request) {

    CartLine cartLine = addCartLine(userId, request);

    CartLineDTO dto = buildCartLineDTO(cartLine);
    return dto;
  }

  @Transactional
  public Cart getUserCartOrThrow(Long userId) {
    Cart cart = cartQueryFacade.getCart(userId);
    if (cart == null) {
      throw new IllegalStateException("Cart does not exist");
    }

    return cart;
  }

  @Transactional
  public CartDTO handleGetCartRequest(Long userId) {
    Cart cart = getUserCartOrThrow(userId);

    List<CartLineDTO> cartLineDTOs = new ArrayList<>();
    List<CartLine> cartLines = cart.getCartLines();
    Long allProductsPrice = 0L;

    for (CartLine cartLine : cartLines) {
      CartLineDTO cartLineDTO = buildCartLineDTO(cartLine);
      cartLineDTOs.add(cartLineDTO);
      allProductsPrice += cartLineDTO.getTotalPrice();
    }

    CartDTO cartDTO = cartConverter.convertToCartDTO(cart, cartLineDTOs, allProductsPrice);
    return cartDTO;
  }

  @Transactional
  public CartLineDTO handleRemoveCartLineRequest(Long userId, Long cartLineId) {
    CartLine deleted = cartActionFacade.removeCartLine(userId, cartLineId);

    CartLineDTO dto = buildCartLineDTO(deleted);

    return dto;
  }

  @Transactional(rollbackOn = Exception.class)
  public void removeCartLines(Long userId, List<Long> cartLineIdList) {
    cartLineIdList.forEach(cartLineId -> {
      cartActionFacade.removeCartLine(userId, cartLineId);
    });
  }

  @Transactional
  public CartLine removeCartLine(Long userId, Long cartLineId) {
    CartLine deleted = cartActionFacade.removeCartLine(userId, cartLineId);

    return deleted;
  }

  @Transactional
  public CartLine updateCartLine(Long userId,
                                 Long cartLineId,
                                 CartLineDTO request) {

    // support quantity update only
    Long quantity = request.getQuantity();
    CartLine updated = cartActionFacade.updateCartLineQuantity(userId, cartLineId, quantity);

    return updated;
  }

  public CartLineDTO handleUpdateCartLineRequest(Long userId,
                                                 Long cartLineId,
                                                 CartLineDTO request) {

    Long quantity = request.getQuantity();
    CartLine cartLine =
        cartActionFacade.updateCartLineQuantity(userId, cartLineId, quantity);

    CartLineDTO cartLineDTO = buildCartLineDTO(cartLine);

    return cartLineDTO;
  }

  public CartLineOption updateCartLineOption(Long userId,
                                             Long cartLineId,
                                             Long cartLineOptionId,
                                             CartLineOptionDTO request) {

    throw new NotImplementedException();
  }

  public CartLineOption addCartLineOption(Long userId,
                                          Long cartLineId,
                                          CartLineOptionDTO request) {

    throw new NotImplementedException();
  }

  public CartLineOption removeCartLineOption(Long userId,
                                             Long cartLineId,
                                             CartLineOptionDTO request) {
    throw new NotImplementedException();
  }

  private CartLineDTO buildCartLineDTO(CartLine cartLine) {
    List<CartLineOption> cartLineOptions = cartLine.getCartLineOptions();

    // build Product
    Product product = catalogService.getProductById(cartLine.getProductId());
    Long productPrice = product.getPrice();
    Long productTotalPrice = product.getTotalPrice();

    // build ProductOptions
    List<CartLineOptionDTO> cartLineOptionDTOs = new ArrayList<>();
    Map<Long, ProductOption> productOptions = product
        .getProductOptions()
        .stream()
        .collect(Collectors.toMap(ProductOption::getId, p -> p));

    // build CartLineOptionDTOs
    for (CartLineOption cartLineOption : cartLineOptions) {
      Long productOptionId = cartLineOption.getProductOptionId();

      if (!productOptions.containsKey(productOptionId)) {
        // TODO: ProductInconsistentException(cartLineOptionId, productOptionId)
        throw new IllegalStateException("ProductOption does not exist");
      }

      ProductOption productOption = productOptions.get(productOptionId);
      CartLineOptionDTO cartLineOptionDTO =
          cartConverter.convertToCartLineOptionDTO(cartLineOption,
                                                   productOption.getPrice(),
                                                   productOption.getName(),
                                                   productOption.getDescription());
      cartLineOptionDTOs.add(cartLineOptionDTO);
    }

    CartLineDTO cartLineDTO = cartConverter.convertToCartLineDTO(cartLine,
                                                                 cartLineOptionDTOs,
                                                                 productPrice,
                                                                 product.getName(),
                                                                 product.getDescription(),
                                                                 productTotalPrice);
    return cartLineDTO;
  }

}
