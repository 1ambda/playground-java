package com.github.lambda.gateway.domain.catalog.repository;

import com.github.lambda.gateway.domain.catalog.entity.Product;

import java.util.List;

/**
 * define custom repository interface for complex product-related queries using Query DSL.
 */
public interface ProductCustomRepository {

  List<Product> findLockedProducts();
}
