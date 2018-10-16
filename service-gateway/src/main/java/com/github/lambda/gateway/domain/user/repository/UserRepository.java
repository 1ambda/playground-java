package com.github.lambda.gateway.domain.user.repository;

import com.github.lambda.gateway.domain.user.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
  @Query(
      "SELECT u FROM User u JOIN u.authIdentity a WHERE a.username = :username AND (u.deletedAt > :timestamp OR u.deletedAt = NULL)")
  User findActiveOneByUsername(@Param("username") String username,
                               @Param("timestamp") LocalDateTime timestamp);
}
