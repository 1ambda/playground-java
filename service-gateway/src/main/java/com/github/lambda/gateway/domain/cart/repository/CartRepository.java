package com.github.lambda.gateway.domain.cart.repository;

import javax.transaction.Transactional;

import com.github.lambda.gateway.domain.cart.entity.Cart;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CartRepository extends PagingAndSortingRepository<Cart, Long> {

}
