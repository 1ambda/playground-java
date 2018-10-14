package com.github.lambda.playground.domain.catalog;

import java.util.List;
import javax.transaction.Transactional;

import com.github.lambda.playground.domain.catalog.entity.Category;
import com.github.lambda.playground.domain.catalog.entity.Product;
import com.github.lambda.playground.swagger.model.CategoryListDTO;
import com.github.lambda.playground.swagger.model.ProductDTO;
import com.github.lambda.playground.swagger.model.ProductListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/** CatalogService handled all operations for the catalog domain. */
@Service
public class CatalogService {

  private CatalogActionFacade productActionFacade;
  private CatalogQueryFacade productQueryFacade;
  private CatalogFactory catalogFactory;

  @Autowired
  public CatalogService(
      CatalogActionFacade productActionFacade,
      CatalogQueryFacade productQueryFacade,
      CatalogFactory catalogFactory) {

    this.productActionFacade = productActionFacade;
    this.productQueryFacade = productQueryFacade;
    this.catalogFactory = catalogFactory;
  }

  @Transactional
  public ProductDTO getProductById(Long productId) {
    Product product = productQueryFacade.getProductById(productId);
    ProductDTO dto = catalogFactory.convertToProductDTO(product);

    return dto;
  }

  @Transactional
  public ProductListDTO getPaginatedProducts(Pageable pageable) {
    Page<Product> paginated = productQueryFacade.getPaginatedProducts(pageable);
    ProductListDTO dto = catalogFactory.convertToProductListDTO(paginated);

    return dto;
  }

  @Transactional
  public CategoryListDTO getAllCategories() {
    List<Category> categories = productQueryFacade.getAllCategories();
    CategoryListDTO dto = catalogFactory.convertToCategoryListDTO(categories);

    return dto;
  }
}
