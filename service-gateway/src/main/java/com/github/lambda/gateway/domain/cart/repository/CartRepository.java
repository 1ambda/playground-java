package com.github.lambda.gateway.domain.cart.repository;

import com.github.lambda.gateway.domain.cart.entity.Cart;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends PagingAndSortingRepository<Cart, Long> {

  Cart findByUserId(@Param("userId") Long userId);
}
