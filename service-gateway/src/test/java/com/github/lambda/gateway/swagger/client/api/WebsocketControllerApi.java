package com.github.lambda.gateway.swagger.client.api;

import com.github.lambda.gateway.swagger.client.invoker.ApiClient;
import com.github.lambda.gateway.swagger.client.invoker.EncodingUtils;

import com.github.lambda.gateway.swagger.model.UserDTO;
import com.github.lambda.gateway.swagger.model.WebsocketMessage;

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
   * @return WebsocketMessage
   */
  @RequestLine("POST /websocket/connection")
  @Headers({
    "Content-Type: application/json",
    "Accept: application/json",
  })
  WebsocketMessage connection(UserDTO body);
}
