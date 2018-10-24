package com.github.lambda.gateway.domain.cart.controller;

import com.github.lambda.gateway.common.Request;
import com.github.lambda.gateway.domain.cart.CartService;
import com.github.lambda.gateway.exception.type.BadRequestException;
import com.github.lambda.gateway.security.SecurityService;
import com.github.lambda.gateway.swagger.model.CartDTO;
import com.github.lambda.gateway.swagger.model.CartLineDTO;
import com.github.lambda.gateway.swagger.model.CartLineOptionDTO;
import com.github.lambda.gateway.swagger.server.api.CartControllerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("api")
public class CartController implements CartControllerApi {

  private CartService cartService;
  private SecurityService securityService;

  public static final int MaxBatchDeleteCount = 100;

  @Autowired
  public CartController(CartService cartService,
                        SecurityService securityService) {

    this.cartService = cartService;
    this.securityService = securityService;
  }

  @RequestMapping(value = "/cart/user/lines",
                  produces = {"application/json"},
                  consumes = {"application/json"},
                  method = RequestMethod.POST)
  @Override
  public ResponseEntity<CartLineDTO> addUserCartLine(@RequestBody @Valid CartLineDTO body) {

    Long userId = securityService.getUserIdOrThrow();

    CartLineDTO cartLineDTO = cartService.handleAddCartLineRequest(userId, body);

    return ResponseEntity.ok(cartLineDTO);
  }

  @RequestMapping(value = "/cart/user/lines",
                  produces = {"application/json"},
                  method = RequestMethod.DELETE)
  @Override
  public ResponseEntity<Void> removeUserCartLines(@RequestParam(value = "idList",
                                                                required = false) @Valid String idList) {
    List<Long> cartLineIdList = new ArrayList<>();

    try {
      cartLineIdList = Request.parseCommaSeparatedLongValues(idList, MaxBatchDeleteCount);
      Collections.sort(cartLineIdList); // sort cartLineId for sequlential disk access :)
    } catch (NumberFormatException e) {
      throw new BadRequestException("Invalid cart line id list", e);
    }

    Long userId = securityService.getUserIdOrThrow();
    cartService.removeCartLines(userId, cartLineIdList);

    return ResponseEntity.noContent().build();
  }

  @RequestMapping(value = "/cart/user/lines",
                  produces = {"application/json"},
                  method = RequestMethod.GET)
  @Override
  public ResponseEntity<CartDTO> getUserCartLines() {
    Long userId = securityService.getUserIdOrThrow();

    CartDTO cartDTO = cartService.handleGetCartRequest(userId);
    return ResponseEntity.ok(cartDTO);
  }

  @RequestMapping(value = "/cart/user/line/{lineId}",
                  produces = {"application/json"},
                  method = RequestMethod.DELETE)
  @Override
  public ResponseEntity<Void> removeUserCartLine(@PathVariable("lineId") Long lineId) {
    Long userId = securityService.getUserIdOrThrow();

    CartLineDTO deleted = cartService.handleRemoveCartLineRequest(userId, lineId);

    return ResponseEntity.noContent().build();
  }

  @RequestMapping(value = "/cart/user/line/{lineId}",
                  produces = {"application/json"},
                  consumes = {"application/json"},
                  method = RequestMethod.PATCH)
  @Override
  public ResponseEntity<CartLineDTO> updateUserCartLine(@PathVariable("lineId") Long lineId,
                                                        @RequestBody @Valid CartLineDTO body) {

    Long userId = securityService.getUserIdOrThrow();
    CartLineDTO dto = cartService.handleUpdateCartLineRequest(userId, lineId, body);

    return ResponseEntity.ok(dto);
  }

  @RequestMapping(value = "/cart/user/line/{lineId}/option/{optionId}",
                  produces = {"application/json"},
                  consumes = {"application/json"},
                  method = RequestMethod.PATCH)
  @Override
  public ResponseEntity<CartLineOptionDTO> updateUserCartLineOption(@PathVariable("lineId") Long lineId,
                                                                    @PathVariable("optionId") Long optionId,
                                                                    @RequestBody @Valid CartLineOptionDTO body) {

    throw new NotImplementedException();
  }


  @RequestMapping(value = "/cart/user/line/{lineId}/option/{optionId}",
                  produces = {"application/json"},
                  method = RequestMethod.DELETE)
  @Override
  public ResponseEntity<Void> removeUserCartLineOption(@PathVariable("lineId") Long lineId,
                                                       @PathVariable("optionId") Long optionId) {
    throw new NotImplementedException();
  }
}
