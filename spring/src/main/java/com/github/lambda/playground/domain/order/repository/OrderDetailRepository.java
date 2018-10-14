package com.github.lambda.playground.domain.order.repository;

import javax.transaction.Transactional;

import com.github.lambda.playground.domain.order.entity.OrderDetail;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface OrderDetailRepository extends PagingAndSortingRepository<OrderDetail, Long> {

}