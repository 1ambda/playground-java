package com.github.lambda.gateway.domain.user.repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import base.AbstractRepositoryTest;
import com.github.lambda.gateway.common.Time;
import com.github.lambda.gateway.domain.user.entity.AuthIdentity;
import com.github.lambda.gateway.domain.user.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Transactional
  @Test
  public void findActiveOneByUsername_shouldReturnValidUser() {
    User valid = prepareUser(null,
                             "username1",
                             AuthIdentity.Provider.PASSWORD);
    User invalid = prepareUser(Time.getCurrentUTCDateTime(),
                               "username2",
                               AuthIdentity.Provider.PASSWORD);

    User found1 = userRepository.findActiveOneByUsername(
        valid.getAuthIdentity().getUsername(), Time.getCurrentUTCDateTime());

    assertThat(found1).isNotNull();

    User found2 = userRepository.findActiveOneByUsername(
        invalid.getAuthIdentity().getUsername(),
        LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
    assertThat(found2).isNull();
  }

  public User prepareUser(LocalDateTime deletedAt,
                          String username,
                          AuthIdentity.Provider provider) {

    User user = User.builder()
        .name("Kun")
        .email(username + "@github.com")
        .address("Seoul, South Korea")
        .build();

    user.setDeletedAt(deletedAt);
    AuthIdentity authIdentity = AuthIdentity.builder()
        .username(username)
        .password("password")
        .provider(provider)
        .build();

    user.setAuthIdentity(authIdentity);
    authIdentity.setUser(user);

    entityManager.persist(user);
    entityManager.flush();

    return user;
  }

}