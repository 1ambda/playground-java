package com.github.lambda.gateway.domain.cart.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CartLineRepository extends PagingAndSortingRepository<CartLineRepository, Long> {

}
