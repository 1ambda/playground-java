package com.github.lambda.gateway.swagger.client.api;

import com.github.lambda.gateway.swagger.client.invoker.ApiClient;
import com.github.lambda.gateway.swagger.client.invoker.EncodingUtils;

import com.github.lambda.gateway.swagger.model.CartDTO;
import com.github.lambda.gateway.swagger.model.CartLineDTO;
import com.github.lambda.gateway.swagger.model.CartLineOptionDTO;
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
    * @param lineId  (required)
   */
  @RequestLine("DELETE /cart/user/line/{lineId}")
  @Headers({
    "Accept: application/json",
  })
  void removeUserCartLine(@Param("lineId") Long lineId);

  /**
   * 
   * 
    * @param lineId  (required)
    * @param optionId  (required)
   */
  @RequestLine("DELETE /cart/user/line/{lineId}/option/{optionId}")
  @Headers({
    "Accept: application/json",
  })
  void removeUserCartLineOption(@Param("lineId") Long lineId, @Param("optionId") Long optionId);

  /**
   * 
   * 
    * @param lineId  (required)
    * @param body  (optional)
   * @return CartLineDTO
   */
  @RequestLine("PATCH /cart/user/line/{lineId}")
  @Headers({
    "Content-Type: application/json",
    "Accept: application/json",
  })
  CartLineDTO updateUserCartLine(@Param("lineId") Long lineId, CartLineDTO body);

  /**
   * 
   * 
    * @param lineId  (required)
    * @param optionId  (required)
    * @param body  (optional)
   * @return CartLineOptionDTO
   */
  @RequestLine("PATCH /cart/user/line/{lineId}/option/{optionId}")
  @Headers({
    "Content-Type: application/json",
    "Accept: application/json",
  })
  CartLineOptionDTO updateUserCartLineOption(@Param("lineId") Long lineId, @Param("optionId") Long optionId, CartLineOptionDTO body);
}
