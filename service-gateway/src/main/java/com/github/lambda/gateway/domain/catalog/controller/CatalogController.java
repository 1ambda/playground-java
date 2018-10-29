package com.github.lambda.gateway.domain.catalog.controller;

import com.github.lambda.gateway.domain.catalog.CatalogService;
import com.github.lambda.gateway.domain.catalog.specification.ProductSpecificationBuilder;
import com.github.lambda.gateway.domain.catalog.specification.ProductSpecificationRequest;
import com.github.lambda.gateway.domain.user.UserService;
import com.github.lambda.gateway.swagger.model.CategoryListDTO;
import com.github.lambda.gateway.swagger.model.Failure;
import com.github.lambda.gateway.swagger.model.PaginatedProductDTO;
import com.github.lambda.gateway.swagger.model.ProductContainerDTO;
import com.github.lambda.gateway.swagger.server.api.CatalogControllerApi;
import com.google.common.base.Preconditions;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;


@Controller
@RequestMapping("api")
public class CatalogController implements CatalogControllerApi {

  private CatalogService catalogService;
  private UserService userService;

  @Autowired
  public CatalogController(CatalogService catalogService, UserService userService) {

    this.catalogService = catalogService;
    this.userService = userService;
  }

  @Override
  @RequestMapping(value = "/catalog/categories",
      produces = {"application/json"},
      method = RequestMethod.GET)
  public ResponseEntity<CategoryListDTO> findAllCategories() {
    CategoryListDTO dto = catalogService.handleGetCategoryRequest();

    return ResponseEntity.ok(dto);
  }

  @Override
  @RequestMapping(value = "/catalog/product/{productId}",
      produces = {"application/json"},
      method = RequestMethod.GET)
  public ResponseEntity<ProductContainerDTO> findOneProduct(@PathVariable("productId") Long productId) {
    Preconditions.checkArgument(productId > 0L, "Invalid productId");

    ProductContainerDTO dto = catalogService.handleGetProductByIdRequest(productId);

    return ResponseEntity.ok(dto);
  }

  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "OK", response = PaginatedProductDTO.class),
      @ApiResponse(code = 500, message = "error", response = Failure.class)})
  @ApiImplicitParams({
      @ApiImplicitParam(name = "page",
          dataType = "long",
          paramType = "query",
          value = "Results page you want to retrieve (0..N)"),
      @ApiImplicitParam(name = "size",
          dataType = "long",
          paramType = "query",
          value = "Number of records per page."),
      @ApiImplicitParam(name = "sort",
          allowMultiple = true,
          dataType = "string",
          paramType = "query",
          value = "Sorting criteria in the format: property(sort=name,asc&sort=numberOfHands,desc)")
  })
  @RequestMapping(value = "/catalog/products",
      produces = {"application/json"},
      method = RequestMethod.GET)
  public ResponseEntity<PaginatedProductDTO> findPaginatedProducts(@ApiIgnore Pageable pageable,
                                                                   @ApiParam ProductSpecificationRequest query) {

    PaginatedProductDTO dto = catalogService.handleGetPaginatedProductsRequest(query, pageable);

    return ResponseEntity.ok(dto);
  }

  /**
   * overrided endpoints
   */

  @ApiIgnore
  @RequestMapping(value = "/ignored/catalog/products",
      produces = {"application/json"},
      method = RequestMethod.GET)
  @Override
  public ResponseEntity<PaginatedProductDTO> findPaginatedProducts(Long page,
                                                                   Long size,
                                                                   List<String> sort,
                                                                   Long categoryId,
                                                                   String search,
                                                                   Long minPrice,
                                                                   Long maxPrice,
                                                                   Long minRate,
                                                                   List<String> tags,
                                                                   String minShippingDate) {

    throw new NotImplementedException();
  }
}
