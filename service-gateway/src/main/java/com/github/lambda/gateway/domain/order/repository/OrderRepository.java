package com.github.lambda.gateway.domain.order.repository;

import com.github.lambda.gateway.domain.order.entity.Order;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

}
