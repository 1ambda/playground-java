package com.github.lambda.playground.domain.user.repository;

import javax.transaction.Transactional;

import com.github.lambda.playground.domain.user.entity.Permission;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PermissionRepository extends PagingAndSortingRepository<Permission, Long> {}
