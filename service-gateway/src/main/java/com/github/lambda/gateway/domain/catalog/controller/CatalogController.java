package com.github.lambda.gateway.domain.catalog.controller;

import com.github.lambda.gateway.domain.catalog.CatalogService;
import com.github.lambda.gateway.domain.user.UserService;
import com.github.lambda.gateway.swagger.model.CategoryListDTO;
import com.github.lambda.gateway.swagger.model.ProductDTO;
import com.github.lambda.gateway.swagger.model.ProductListDTO;
import com.github.lambda.gateway.swagger.server.api.CatalogControllerApi;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
  public ResponseEntity<CategoryListDTO> findAllCategories() {
    CategoryListDTO dto = catalogService.getAllCategories();

    return ResponseEntity.ok(dto);
  }

  @Override
  public ResponseEntity<ProductDTO> findOneProduct(@PathVariable("productId") Long productId) {
    Preconditions.checkArgument(productId > 0L, "Invalid productId");

    catalogService.getProductById(productId);

    return null;
  }

  @Override
  public ResponseEntity<ProductListDTO> findPaginatedProducts(
      Long productId, Long page, Long count) {

    Pageable pageable = PageRequest.of(page.intValue(), count.intValue());
    ProductListDTO dto = catalogService.getPaginatedProducts(pageable);

    return ResponseEntity.ok(dto);
  }
}
