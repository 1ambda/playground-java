package com.github.lambda.gateway.swagger.client.api;

import com.github.lambda.gateway.swagger.client.invoker.ApiClient;
import com.github.lambda.gateway.swagger.client.invoker.EncodingUtils;

import com.github.lambda.gateway.swagger.model.CartDTO;
import com.github.lambda.gateway.swagger.model.CartLineDTO;
import com.github.lambda.gateway.swagger.model.CartLineOptionDTO;
import com.github.lambda.gateway.swagger.model.CartLineOptionRequestDTO;
import com.github.lambda.gateway.swagger.model.CartLineRequestDTO;
import com.github.lambda.gateway.swagger.model.Failure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import feign.*;


public interface CartControllerApi extends ApiClient.Api {


  /**
   * 
   * 
    * @param body  (optional)
   * @return CartLineDTO
   */
  @RequestLine("POST /cart/user/lines")
  @Headers({
    "Content-Type: application/json",
    "Accept: application/json",
  })
  CartLineDTO addUserCartLine(CartLineDTO body);

  /**
   * 
   * 
   */
  @RequestLine("DELETE /cart/user/lines")
  @Headers({
    "Accept: application/json",
  })
  void clearUserCartLines();

  /**
   * 
   * 
   * @return CartDTO
   */
  @RequestLine("GET /cart/user/lines")
  @Headers({
    "Accept: application/json",
  })
  CartDTO getUserCartLines();

  /**
   * 
   * 
    * @param cartLineId  (required)
   */
  @RequestLine("DELETE /cart/user/line/{lineId}")
  @Headers({
    "Accept: application/json",
  })
  void removeUserCartLine(@Param("cartLineId") Long cartLineId);

  /**
   * 
   * 
    * @param cartLineId  (required)
    * @param cartLineOptionId  (required)
   */
  @RequestLine("DELETE /cart/user/line/{lineId}/option/{optionId}")
  @Headers({
    "Accept: application/json",
  })
  void removeUserCartLineOption(@Param("cartLineId") Long cartLineId, @Param("cartLineOptionId") Long cartLineOptionId);

  /**
   * 
   * 
    * @param cartLineId  (required)
    * @param body  (optional)
   * @return CartLineDTO
   */
  @RequestLine("PATCH /cart/user/line/{lineId}")
  @Headers({
    "Content-Type: application/json",
    "Accept: application/json",
  })
  CartLineDTO updateUserCartLine(@Param("cartLineId") Long cartLineId, CartLineRequestDTO body);

  /**
   * 
   * 
    * @param cartLineId  (required)
    * @param cartLineOptionId  (required)
    * @param body  (optional)
   * @return CartLineOptionDTO
   */
  @RequestLine("PATCH /cart/user/line/{lineId}/option/{optionId}")
  @Headers({
    "Content-Type: application/json",
    "Accept: application/json",
  })
  CartLineOptionDTO updateUserCartLineOption(@Param("cartLineId") Long cartLineId, @Param("cartLineOptionId") Long cartLineOptionId, CartLineOptionRequestDTO body);
}
