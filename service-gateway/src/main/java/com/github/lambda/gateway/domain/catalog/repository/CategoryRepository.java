package com.github.lambda.gateway.domain.catalog.repository;

import com.github.lambda.gateway.domain.catalog.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

  @Override
  List<Category> findAll();
}
