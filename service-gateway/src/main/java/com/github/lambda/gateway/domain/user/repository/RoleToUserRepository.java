package com.github.lambda.gateway.domain.user.repository;

import com.github.lambda.gateway.domain.user.entity.RoleToUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleToUserRepository extends PagingAndSortingRepository<RoleToUser, Long> {

}
