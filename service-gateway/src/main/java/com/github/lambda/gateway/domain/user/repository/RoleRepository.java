package com.github.lambda.gateway.domain.user.repository;

import com.github.lambda.gateway.domain.user.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
  Role findByCode(Role.Code code);
}
