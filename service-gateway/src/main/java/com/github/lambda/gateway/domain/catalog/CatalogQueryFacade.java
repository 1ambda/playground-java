package com.github.lambda.gateway.domain.catalog;

import java.util.List;
import javax.transaction.Transactional;

import com.github.lambda.gateway.domain.catalog.entity.Category;
import com.github.lambda.gateway.domain.catalog.entity.Product;
import com.github.lambda.gateway.domain.catalog.repository.CategoryRepository;
import com.github.lambda.gateway.domain.catalog.repository.ProductRepository;
import com.github.lambda.gateway.exception.type.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/** ProductQueryFacade handles all queries for catalog-bounded context operations. */
@Service
@Transactional
public class CatalogQueryFacade {

  private CategoryRepository categoryRepository;
  private ProductRepository productRepository;

  @Autowired
  public CatalogQueryFacade(CategoryRepository categoryRepository,
                            ProductRepository productRepository) {

    this.categoryRepository = categoryRepository;
    this.productRepository = productRepository;
  }

  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  public Page<Product> getPaginatedProducts(Pageable pageable) {
    Page<Product> products = productRepository.findAll(pageable);
    return products;
  }

  public Product getProductById(Long productId) {
    return productRepository
        .findById(productId)
        .orElseThrow(() -> new BadRequestException("Failed to find the requested product"));
  }
}
