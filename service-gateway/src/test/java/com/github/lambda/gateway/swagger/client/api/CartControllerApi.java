package com.github.lambda.gateway.swagger.client.api;

import com.github.lambda.gateway.swagger.client.invoker.ApiClient;
import com.github.lambda.gateway.swagger.client.invoker.EncodingUtils;

import com.github.lambda.gateway.swagger.model.CartDTO;
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
   * @return CartDTO
   */
  @RequestLine("GET /cart/my")
  @Headers({
    "Accept: application/json",
  })
  CartDTO findUserCart();
}
