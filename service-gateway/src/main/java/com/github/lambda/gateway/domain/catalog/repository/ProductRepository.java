package com.github.lambda.gateway.domain.catalog.repository;

import com.github.lambda.gateway.domain.catalog.entity.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>,
                                           JpaSpecificationExecutor<Product> {

}
