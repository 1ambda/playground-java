package com.github.lambda.gateway.domain.catalog.repository;

import com.github.lambda.gateway.domain.base.YesNo;
import com.github.lambda.gateway.domain.catalog.entity.Product;
import com.github.lambda.gateway.domain.catalog.entity.QProduct;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * custom repository implementation for product-related complex (read) queries using Query DSL.
 */
@Repository
public class ProductCustomRepositoryImpl extends QuerydslRepositorySupport implements ProductCustomRepository {

  public ProductCustomRepositoryImpl() {
    super(Product.class);
  }

  @Override
  public List<Product> findLockedProducts() {

    QProduct product = QProduct.product;

    List<Product> products = from(product)
        .where(product.deletedAt.isNull()
                   .and(product.locked.eq(YesNo.Y)))
        .fetch();

    return products;
  }
}
