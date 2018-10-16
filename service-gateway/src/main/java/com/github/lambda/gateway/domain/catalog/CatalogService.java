package com.github.lambda.gateway.domain.catalog;

import com.github.lambda.gateway.domain.catalog.entity.Category;
import com.github.lambda.gateway.domain.catalog.entity.Product;
import com.github.lambda.gateway.swagger.model.CategoryListDTO;
import com.github.lambda.gateway.swagger.model.PaginatedProductDTO;
import com.github.lambda.gateway.swagger.model.ProductContainerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * CatalogService handled all operations for the catalog domain.
 */
@Service
public class CatalogService {

  private CatalogActionFacade productActionFacade;
  private CatalogQueryFacade productQueryFacade;
  private CatalogConverter catalogConverter;

  @Autowired
  public CatalogService(CatalogActionFacade productActionFacade,
                        CatalogQueryFacade productQueryFacade,
                        CatalogConverter catalogConverter) {

    this.productActionFacade = productActionFacade;
    this.productQueryFacade = productQueryFacade;
    this.catalogConverter = catalogConverter;
  }

  @Transactional
  public ProductContainerDTO getProductById(Long productId) {
    Product product = productQueryFacade.getProductById(productId);
    ProductContainerDTO dto = catalogConverter.convertToProductDTO(product);

    return dto;
  }

  @Transactional
  public PaginatedProductDTO getPaginatedProducts(Pageable pageable) {
    Page<Product> paginated = productQueryFacade.getPaginatedProducts(pageable);
    PaginatedProductDTO dto = catalogConverter.convertToPaginatedProductDTO(paginated);

    return dto;
  }

  @Transactional
  public CategoryListDTO getAllCategories() {
    List<Category> categories = productQueryFacade.getAllCategories();
    CategoryListDTO dto = catalogConverter.convertToCategoryListDTO(categories);

    return dto;
  }
}
