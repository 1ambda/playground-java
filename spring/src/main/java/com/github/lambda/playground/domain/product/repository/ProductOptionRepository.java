package com.github.lambda.playground.domain.product.repository;

import javax.transaction.Transactional;

import com.github.lambda.playground.domain.product.entity.Product;
import com.github.lambda.playground.domain.product.entity.ProductOption;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ProductOptionRepository extends PagingAndSortingRepository<ProductOption, Long> {

}
