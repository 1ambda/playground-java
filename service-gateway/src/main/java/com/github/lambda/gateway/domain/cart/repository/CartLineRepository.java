package com.github.lambda.gateway.domain.cart.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CartLineRepository extends PagingAndSortingRepository<CartLineRepository, Long> {

}
