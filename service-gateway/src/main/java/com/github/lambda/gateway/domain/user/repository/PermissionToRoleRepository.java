package com.github.lambda.gateway.domain.user.repository;

import com.github.lambda.gateway.domain.user.entity.PermissionToRole;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionToRoleRepository extends PagingAndSortingRepository<PermissionToRole, Long> {

}
