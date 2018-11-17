package com.github.lambda.gateway.swagger.client.api;

import com.github.lambda.gateway.swagger.client.invoker.ApiClient;
import com.github.lambda.gateway.swagger.client.invoker.EncodingUtils;

import com.github.lambda.gateway.swagger.model.WebsocketMessageBase;
import com.github.lambda.gateway.swagger.model.WebsocketMessageInclusive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import feign.*;


public interface WebsocketControllerApi extends ApiClient.Api {


  /**
   * 
   * 
    * @param body  (optional)
   * @return WebsocketMessageInclusive
   */
  @RequestLine("GET /pseudo/controller/websocket")
  @Headers({
    "Content-Type: application/json",
    "Accept: application/json",
  })
  WebsocketMessageInclusive connection(WebsocketMessageBase body);
}
