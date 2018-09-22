package com.github.lambda.playground.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import com.github.lambda.playground.domain.user.dto.UserDTO;
import com.github.lambda.playground.domain.user.entity.AuthIdentity;
import com.github.lambda.playground.domain.user.entity.Role;
import com.github.lambda.playground.domain.user.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles( {"test"})
@AutoConfigureTestEntityManager
public class UserServiceTest {

  @Autowired
  TestEntityManager em;

  @Autowired
  UserService userService;

  @Test
  @Transactional
  @Rollback
  public void addNewCustomer_shouldCreateUserAndSetRoles() {
    // given
    UserDTO userDTO = UserDTO.builder()
        .name("Kun")
        .email("user@test.com")
        .address("Seoul, South Korea")
        .provider(AuthIdentity.Provider.PASSWORD)
        .username("user")
        .password("password")
        .build();

    // when
    UserDTO created = userService.addNewCustomer(userDTO);

    // then
    assertThat(created.getRoles()).containsAnyOf(
        Role.Code.ROLE_USER,
        Role.Code.ROLE_CUSTOMER);
  }

  @Test
  @Transactional
  @Rollback
  public void getUserByUsername_shouldReturnUser() {
    // given
    User created = persistUser(em, AuthIdentity.Provider.PASSWORD);
    String username = created.getAuthIdentity().getUsername();

    // when
    User u = userService.getUserByUsername(username);

    // then
    assertThat(u).isNotNull();
  }

  @Test
  @Transactional
  @Rollback
  public void getUserByUsername_shouldReturnNull_whenNoSuchUserFound() {
    // given
    String username = "unknown";

    // when
    User u = userService.getUserByUsername(username);

    // then
    assertThat(u).isNull();
  }

  public static User persistUser(TestEntityManager em, AuthIdentity.Provider provider) {
    User user = User.builder()
        .name("Unknown")
        .email("user@test.com")
        .address("Somewhere")
        .build();
    AuthIdentity authIdentity = AuthIdentity.builder()
        .username("username")
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