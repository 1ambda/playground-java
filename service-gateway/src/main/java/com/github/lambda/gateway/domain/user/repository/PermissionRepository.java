package com.github.lambda.gateway.domain.user.repository;

import com.github.lambda.gateway.domain.user.entity.Permission;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends PagingAndSortingRepository<Permission, Long> {
}
