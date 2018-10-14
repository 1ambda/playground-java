package com.github.lambda.playground.domain.catalog.controller;

import com.github.lambda.playground.domain.catalog.CatalogService;
import com.github.lambda.playground.swagger.model.ProductDTO;
import com.github.lambda.playground.swagger.model.ProductListDTO;
import com.github.lambda.playground.swagger.server.api.CatalogControllerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api")
public class CatalogController implements CatalogControllerApi {

  private CatalogService catalogService;

  @Autowired
  public CatalogController(CatalogService catalogService) {
    this.catalogService = catalogService;
  }

  @Override
  public ResponseEntity<ProductDTO> findOneProduct(Long productId) {
    return null;
  }

  @Override
  public ResponseEntity<ProductListDTO> findPaginatedProducts(Long productId,
                                                              Long page,
                                                              Long count) {
    return null;
  }
}
