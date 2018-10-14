package com.github.lambda.playground.domain.order.repository;

import javax.transaction.Transactional;

import com.github.lambda.playground.domain.order.entity.Order;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

}
