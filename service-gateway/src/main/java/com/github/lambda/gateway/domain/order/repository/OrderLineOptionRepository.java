package com.github.lambda.gateway.domain.order.repository;

import com.github.lambda.gateway.domain.order.entity.OrderLineOption;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface OrderLineOptionRepository extends PagingAndSortingRepository<OrderLineOption, Long> {

}
