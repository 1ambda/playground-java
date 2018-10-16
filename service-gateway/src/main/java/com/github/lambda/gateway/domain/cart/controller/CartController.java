package com.github.lambda.gateway.domain.cart.controller;

import com.github.lambda.gateway.swagger.model.*;
import com.github.lambda.gateway.swagger.server.api.CartControllerApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

public class CartController implements CartControllerApi {
  @RequestMapping(value = "/cart/user/lines",
                  produces = {"application/json"},
                  consumes = {"application/json"},
                  method = RequestMethod.POST)
  @Override
  public ResponseEntity<CartLineDTO> addUserCartLine(@RequestBody @Valid CartLineDTO body) {
    return null;
  }

  @RequestMapping(value = "/cart/user/lines",
                  produces = {"application/json"},
                  method = RequestMethod.DELETE)
  @Override
  public ResponseEntity<Void> clearUserCartLines() {
    return null;
  }

  @RequestMapping(value = "/cart/user/lines",
                  produces = {"application/json"},
                  method = RequestMethod.GET)
  @Override
  public ResponseEntity<CartDTO> getUserCartLines() {
    return null;
  }

  @RequestMapping(value = "/cart/user/line/{lineId}",
                  produces = {"application/json"},
                  method = RequestMethod.DELETE)
  @Override
  public ResponseEntity<Void> removeUserCartLine(@PathVariable("lineId") Long lineId) {
    return null;
  }

  @RequestMapping(value = "/cart/user/line/{lineId}/option/{optionId}",
                  produces = {"application/json"},
                  method = RequestMethod.DELETE)
  @Override
  public ResponseEntity<Void> removeUserCartLineOption(@PathVariable("lineId") Long lineId,
                                                       @PathVariable("optionId") Long optionId) {
    return null;
  }

  @RequestMapping(value = "/cart/user/line/{lineId}",
                  produces = {"application/json"},
                  consumes = {"application/json"},
                  method = RequestMethod.PATCH)
  @Override
  public ResponseEntity<CartLineDTO> updateUserCartLine(@PathVariable("lineId") Long lineId,
                                                        @RequestBody @Valid CartLineRequestDTO body) {
    return null;
  }

  @RequestMapping(value = "/cart/user/line/{lineId}/option/{optionId}",
                  produces = {"application/json"},
                  consumes = {"application/json"},
                  method = RequestMethod.PATCH)
  @Override
  public ResponseEntity<CartLineOptionDTO> updateUserCartLineOption(@PathVariable("lineId") Long lineId,
                                                                    @PathVariable("optionId") Long optionId,
                                                                    @RequestBody @Valid CartLineOptionRequestDTO body) {
    return null;
  }
}
