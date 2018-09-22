package com.github.lambda.playground.domain.user.repository;

import javax.transaction.Transactional;

import com.github.lambda.playground.domain.user.entity.PermissionToRole;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PermissionToRoleRepository extends PagingAndSortingRepository<PermissionToRole, Long> {

}
