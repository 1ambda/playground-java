package com.github.lambda.gateway.domain.catalog.repository;

import javax.transaction.Transactional;

import com.github.lambda.gateway.domain.catalog.entity.Image;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ImageRepository extends PagingAndSortingRepository<Image, Long> {

}
