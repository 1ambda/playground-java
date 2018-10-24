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
    * @param idList  (optional)
   */
  @RequestLine("DELETE /cart/user/lines?idList={idList}")
  @Headers({
    "Accept: application/json",
  })
  void removeUserCartLines(@Param("idList") String idList);

  /**
   * 
   * 
   * Note, this is equivalent to the other <code>removeUserCartLines</code> method,
   * but with the query parameters collected into a single Map parameter. This
   * is convenient for services with optional query parameters, especially when
   * used with the {@link RemoveUserCartLinesQueryParams} class that allows for
   * building up this map in a fluent style.
   * @param queryParams Map of query parameters as name-value pairs
   *   <p>The following elements may be specified in the query map:</p>
   *   <ul>
   *   <li>idList -  (optional)</li>
   *   </ul>
   */
  @RequestLine("DELETE /cart/user/lines?idList={idList}")
  @Headers({
  "Accept: application/json",
  })
  void removeUserCartLines(@QueryMap(encoded=true) Map<String, Object> queryParams);

  /**
   * A convenience class for generating query parameters for the
   * <code>removeUserCartLines</code> method in a fluent style.
   */
  public static class RemoveUserCartLinesQueryParams extends HashMap<String, Object> {
    public RemoveUserCartLinesQueryParams idList(final String value) {
      put("idList", EncodingUtils.encode(value));
      return this;
    }
  }

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
