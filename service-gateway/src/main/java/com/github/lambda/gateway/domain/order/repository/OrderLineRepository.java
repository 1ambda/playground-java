package com.github.lambda.gateway.domain.order.repository;

import com.github.lambda.gateway.domain.order.entity.OrderLine;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends PagingAndSortingRepository<OrderLine, Long> {

}
