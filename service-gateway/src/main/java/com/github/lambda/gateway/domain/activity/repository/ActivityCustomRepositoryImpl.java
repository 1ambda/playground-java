package com.github.lambda.gateway.domain.activity.repository;

import com.github.lambda.gateway.domain.activity.entity.Activity;
import com.github.lambda.gateway.domain.base.YesNo;
import com.github.lambda.gateway.domain.catalog.entity.Product;
import com.github.lambda.gateway.domain.catalog.entity.QProduct;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * custom repository implementation for activity-related complex (read) queries using Query DSL.
 */
@Repository
public class ActivityCustomRepositoryImpl extends QuerydslRepositorySupport implements ActivityCustomRepository {

  public ActivityCustomRepositoryImpl() {
    super(Activity.class);
  }

}
