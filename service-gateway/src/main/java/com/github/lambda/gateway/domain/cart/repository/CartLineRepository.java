package com.github.lambda.gateway.domain.cart.repository;

import com.github.lambda.gateway.domain.cart.entity.CartLine;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartLineRepository extends PagingAndSortingRepository<CartLine, Long> {
}
