package com.github.lambda.playground.domain.order;

import javax.transaction.Transactional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

}
