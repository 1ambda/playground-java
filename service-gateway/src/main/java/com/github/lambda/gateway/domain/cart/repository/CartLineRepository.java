package com.github.lambda.gateway.domain.cart.repository;

import com.github.lambda.gateway.domain.cart.entity.CartLine;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CartLineRepository extends PagingAndSortingRepository<CartLine, Long> {
}
