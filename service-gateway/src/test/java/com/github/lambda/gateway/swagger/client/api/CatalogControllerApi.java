package com.github.lambda.gateway.swagger.client.api;

import com.github.lambda.gateway.swagger.client.invoker.ApiClient;
import com.github.lambda.gateway.swagger.client.invoker.EncodingUtils;

import com.github.lambda.gateway.swagger.model.CategoryListDTO;
import com.github.lambda.gateway.swagger.model.Failure;
import com.github.lambda.gateway.swagger.model.PaginatedProductDTO;
import com.github.lambda.gateway.swagger.model.ProductDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import feign.*;


public interface CatalogControllerApi extends ApiClient.Api {


  /**
   * 
   * 
   * @return CategoryListDTO
   */
  @RequestLine("GET /catalog/categories")
  @Headers({
    "Accept: application/json",
  })
  CategoryListDTO findAllCategories();

  /**
   * 
   * 
    * @param productId  (required)
   * @return ProductDTO
   */
  @RequestLine("GET /catalog/product/{productId}")
  @Headers({
    "Accept: application/json",
  })
  ProductDTO findOneProduct(@Param("productId") Long productId);

  /**
   * 
   * 
    * @param page  (optional)
    * @param count  (optional)
   * @return PaginatedProductDTO
   */
  @RequestLine("GET /catalog/products?page={page}&count={count}")
  @Headers({
    "Accept: application/json",
  })
  PaginatedProductDTO findPaginatedProducts(@Param("page") Long page, @Param("count") Long count);

  /**
   * 
   * 
   * Note, this is equivalent to the other <code>findPaginatedProducts</code> method,
   * but with the query parameters collected into a single Map parameter. This
   * is convenient for services with optional query parameters, especially when
   * used with the {@link FindPaginatedProductsQueryParams} class that allows for
   * building up this map in a fluent style.
   * @param queryParams Map of query parameters as name-value pairs
   *   <p>The following elements may be specified in the query map:</p>
   *   <ul>
   *   <li>page -  (optional)</li>
   *   <li>count -  (optional)</li>
   *   </ul>
   * @return PaginatedProductDTO
   */
  @RequestLine("GET /catalog/products?page={page}&count={count}")
  @Headers({
  "Accept: application/json",
  })
  PaginatedProductDTO findPaginatedProducts(@QueryMap(encoded=true) Map<String, Object> queryParams);

  /**
   * A convenience class for generating query parameters for the
   * <code>findPaginatedProducts</code> method in a fluent style.
   */
  public static class FindPaginatedProductsQueryParams extends HashMap<String, Object> {
    public FindPaginatedProductsQueryParams page(final Long value) {
      put("page", EncodingUtils.encode(value));
      return this;
    }
    public FindPaginatedProductsQueryParams count(final Long value) {
      put("count", EncodingUtils.encode(value));
      return this;
    }
  }
}
