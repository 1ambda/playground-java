package com.github.lambda.gateway.domain.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.github.lambda.gateway.domain.user.entity.AuthIdentity;
import com.github.lambda.gateway.domain.user.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles( {"test"})
@AutoConfigureTestEntityManager
public class UserRepositoryTest {

  @Autowired
  TestEntityManager em;

  @Autowired
  UserRepository userRepository;

  @Transactional
  @Rollback
  @Test
  public void findActiveOneByUsername_shouldReturnValidUser() {
    User valid = persistUser(em, null,
        "username1", AuthIdentity.Provider.PASSWORD);
    User invalid = persistUser(em, LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC),
        "username2", AuthIdentity.Provider.PASSWORD);

    User found1 = userRepository.findActiveOneByUsername(
        valid.getAuthIdentity().getUsername(),
        LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
    assertThat(found1).isNotNull();

    User found2 = userRepository.findActiveOneByUsername(
        invalid.getAuthIdentity().getUsername(),
        LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC));
    assertThat(found2).isNull();
  }

  public static User persistUser(TestEntityManager em,
                                 LocalDateTime deletedAt,
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
    em.persist(user);
    em.flush();

    return user;
  }

}