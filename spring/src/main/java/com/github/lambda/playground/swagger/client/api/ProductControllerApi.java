package com.github.lambda.playground.swagger.client.api;

import com.github.lambda.playground.swagger.client.invoker.ApiClient;
import com.github.lambda.playground.swagger.client.invoker.EncodingUtils;

import com.github.lambda.playground.swagger.model.Failure;
import com.github.lambda.playground.swagger.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import feign.*;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2018-10-04T19:55:13.446+09:00")
public interface ProductControllerApi extends ApiClient.Api {


  /**
   * 
   * 
    * @param productID  (required)
   * @return Product
   */
  @RequestLine("GET /product/{productID}")
  @Headers({
    "Accept: application/json",
  })
  Product findOneWithOptions(@Param("productID") String productID);
}
