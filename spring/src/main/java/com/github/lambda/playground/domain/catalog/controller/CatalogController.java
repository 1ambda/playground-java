package com.github.lambda.playground.domain.catalog.controller;

import javax.validation.constraints.PositiveOrZero;

import com.github.lambda.playground.domain.catalog.CatalogService;
import com.github.lambda.playground.domain.user.UserService;
import com.github.lambda.playground.swagger.model.CategoryListDTO;
import com.github.lambda.playground.swagger.model.ProductDTO;
import com.github.lambda.playground.swagger.model.ProductListDTO;
import com.github.lambda.playground.swagger.server.api.CatalogControllerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
  public ResponseEntity<ProductDTO> findOneProduct(@PositiveOrZero Long productId) {
    //     Preconditions.checkArgument(p);

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
