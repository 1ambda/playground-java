package com.github.lambda.gateway.domain.catalog;

import com.github.lambda.gateway.common.Time;
import com.github.lambda.gateway.domain.catalog.entity.Category;
import com.github.lambda.gateway.domain.catalog.entity.Product;
import com.github.lambda.gateway.domain.catalog.exception.ProductOptionUnavailableException;
import com.github.lambda.gateway.domain.catalog.exception.ProductUnavailableException;
import com.github.lambda.gateway.domain.catalog.specification.ProductSpecificationBuilder;
import com.github.lambda.gateway.domain.catalog.specification.ProductSpecificationRequest;
import com.github.lambda.gateway.swagger.model.CategoryListDTO;
import com.github.lambda.gateway.swagger.model.PaginatedProductDTO;
import com.github.lambda.gateway.swagger.model.ProductContainerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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
  public ProductContainerDTO handleGetProductByIdRequest(Long productId) {
    Product product = getProductById(productId);
    ProductContainerDTO dto = catalogConverter.convertToProductDTO(product);

    return dto;
  }

  /**
   * @param productId
   * @return Product
   * @throws ProductUnavailableException
   * @throws ProductOptionUnavailableException
   */
  @Transactional
  public Product getProductById(Long productId) {
    LocalDateTime now = Time.getCurrentUTCDateTime();
    Product product = productQueryFacade.getAvailableProductById(productId, now);

    return product;
  }

  @Transactional
  public PaginatedProductDTO handleGetPaginatedProductsRequest(ProductSpecificationRequest request, Pageable pageable) {

    Specification<Product> rangePrice = ProductSpecificationBuilder.rangePrice(
        request.getMinPrice(), request.getMaxPrice());
    Specification<Product> spec = Specification.where(rangePrice); // TODO: and, ...

    Page<Product> paginated = productQueryFacade.getPaginatedAvailableProducts(spec, pageable);
    PaginatedProductDTO dto = catalogConverter.convertToPaginatedProductDTO(paginated);

    return dto;
  }

  @Transactional
  public CategoryListDTO handleGetCategoryRequest() {
    List<Category> categories = productQueryFacade.getAllCategories();
    CategoryListDTO dto = catalogConverter.convertToCategoryListDTO(categories);

    return dto;
  }
}
