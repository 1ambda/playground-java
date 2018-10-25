package com.github.lambda.gateway.domain.catalog.repository;

import com.github.lambda.gateway.domain.catalog.entity.Product;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

/**
 * custom repository implementation for product-related complex (read) queries using Query DSL.
 */
@Repository
public class ProductCustomRepositoryImpl
    extends QuerydslRepositorySupport implements ProductCustomRepository {

  public ProductCustomRepositoryImpl() {
    super(Product.class);
  }
}
