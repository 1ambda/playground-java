package com.github.lambda.gateway.domain.activity.repository;

import com.github.lambda.gateway.domain.activity.entity.Notification;
import com.github.lambda.gateway.domain.catalog.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long> {

  @Override
  List<Notification> findAll();
}
