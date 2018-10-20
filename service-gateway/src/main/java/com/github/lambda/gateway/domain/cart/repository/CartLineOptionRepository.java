package com.github.lambda.gateway.domain.cart.repository;

import com.github.lambda.gateway.domain.cart.entity.CartLineOption;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CartLineOptionRepository extends PagingAndSortingRepository<CartLineOption, Long> {

}
