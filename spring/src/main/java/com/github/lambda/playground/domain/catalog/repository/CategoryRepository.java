package com.github.lambda.playground.domain.catalog.repository;

import java.util.List;
import javax.transaction.Transactional;

import com.github.lambda.playground.domain.catalog.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

  @Override
  List<Category> findAll();
}
