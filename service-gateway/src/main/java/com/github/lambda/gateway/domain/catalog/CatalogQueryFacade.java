package com.github.lambda.gateway.domain.catalog;

import com.github.lambda.gateway.domain.catalog.entity.Category;
import com.github.lambda.gateway.domain.catalog.entity.Product;
import com.github.lambda.gateway.domain.catalog.entity.ProductOption;
import com.github.lambda.gateway.domain.catalog.exception.ProductUnavailableException;
import com.github.lambda.gateway.domain.catalog.repository.CategoryRepository;
import com.github.lambda.gateway.domain.catalog.repository.ProductCustomRepository;
import com.github.lambda.gateway.domain.catalog.repository.ProductRepository;
import com.github.lambda.gateway.exception.type.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ProductQueryFacade handles all queries for catalog-bounded context operations.
 */
@Service
@Transactional
public class CatalogQueryFacade {

  private CategoryRepository categoryRepository;
  private ProductRepository productRepository;
  private ProductCustomRepository productCustomRepository;

  @Autowired
  public CatalogQueryFacade(CategoryRepository categoryRepository,
                            ProductRepository productRepository,
                            ProductCustomRepository productCustomRepository) {

    this.categoryRepository = categoryRepository;
    this.productRepository = productRepository;
    this.productCustomRepository = productCustomRepository;
  }

  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  public Page<Product> getPaginatedAvailableProducts(Specification<Product> specification, Pageable pageable) {
    Page<Product> products = productRepository.findAll(specification, pageable);
    return products;
  }

  // TODO: move this function into Product entity
  public Product getAvailableProductById(Long productId, LocalDateTime then) {
    Product product = productRepository
        .findById(productId)
        .orElseThrow(() -> new BadRequestException("Failed to find the requested product"));

    if (!product.isAvailable()) {
      throw ProductUnavailableException.create(productId);
    }

    List<ProductOption> filtered = product
        .getProductOptions()
        .stream()
        .filter(option -> option.isAvailableAt(then))
        .collect(Collectors.toList());

    // we set product options for view purpose instead of calling `removeProductOption`
    product.setProductOptions(filtered);
    // filtered.forEach(product::removeProductOption);

    return product;
  }
}
