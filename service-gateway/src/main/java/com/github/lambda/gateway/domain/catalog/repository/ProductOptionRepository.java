package com.github.lambda.gateway.domain.catalog.repository;

import com.github.lambda.gateway.domain.catalog.entity.ProductOption;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOptionRepository extends PagingAndSortingRepository<ProductOption, Long> {

}
