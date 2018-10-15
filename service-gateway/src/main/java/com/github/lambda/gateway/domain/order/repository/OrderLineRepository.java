package com.github.lambda.gateway.domain.order.repository;

import javax.transaction.Transactional;

import com.github.lambda.gateway.domain.order.entity.OrderLine;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface OrderLineRepository extends PagingAndSortingRepository<OrderLine, Long> {

}
