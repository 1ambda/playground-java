package com.github.lambda.gateway.domain.activity.repository;

import com.github.lambda.gateway.domain.activity.entity.Activity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends PagingAndSortingRepository<Activity, Long>,
                                            JpaSpecificationExecutor<Activity> {

  @Override
  List<Activity> findAll();
}
