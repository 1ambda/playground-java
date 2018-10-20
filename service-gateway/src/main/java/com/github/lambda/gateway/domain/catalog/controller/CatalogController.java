package com.github.lambda.gateway.domain.catalog.controller;

import com.github.lambda.gateway.domain.catalog.CatalogService;
import com.github.lambda.gateway.domain.user.UserService;
import com.github.lambda.gateway.swagger.model.CategoryListDTO;
import com.github.lambda.gateway.swagger.model.PaginatedProductDTO;
import com.github.lambda.gateway.swagger.model.ProductDTO;
import com.github.lambda.gateway.swagger.server.api.CatalogControllerApi;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    CategoryListDTO dto = catalogService.getCategoryListDTO();

    return ResponseEntity.ok(dto);
  }

  @Override
  @RequestMapping(value = "/catalog/product/{productId}",
                  produces = {"application/json"},
                  method = RequestMethod.GET)
  public ResponseEntity<ProductDTO> findOneProduct(@PathVariable("productId") Long productId) {
    Preconditions.checkArgument(productId > 0L, "Invalid productId");

    catalogService.getProductDTOById(productId);

    return null;
  }

  @Override
  @RequestMapping(value = "/catalog/products",
                  produces = {"application/json"},
                  method = RequestMethod.GET)
  public ResponseEntity<PaginatedProductDTO> findPaginatedProducts(@RequestParam(value = "page",
                                                                                 required = false) Long page,
                                                                   @RequestParam(value = "count",
                                                                                 required = false) Long count) {

    Preconditions.checkArgument(page >= 0L, "invalid page value");
    Preconditions.checkArgument(count > 0L, "invalid count value");

    Pageable pageable = PageRequest.of(page.intValue(), count.intValue());
    PaginatedProductDTO dto = catalogService.getPaginatedProductDTO(pageable);

    return ResponseEntity.ok(dto);
  }
}
