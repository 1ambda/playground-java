package com.github.lambda.gateway.domain.user;

import javax.transaction.Transactional;

import base.AbstractServiceTest;
import com.github.lambda.gateway.domain.user.entity.AuthIdentity;
import com.github.lambda.gateway.domain.user.entity.Role;
import com.github.lambda.gateway.domain.user.entity.User;
import com.github.lambda.gateway.domain.user.repository.RoleRepository;
import com.github.lambda.gateway.swagger.model.UserDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static reactor.core.publisher.Mono.when;

@AutoConfigureTestEntityManager
public class UserServiceTest extends AbstractServiceTest {

  @Autowired
  UserService userService;

  @MockBean
  RoleRepository roleRepository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void addNewCustomer_shouldCreateUserAndSetRoles() {
    // given
    UserDTO userDTO = UserDTO.builder()
        .name("Unknown")
        .email("user@test.com")
        .address("Seoul, South Korea")
        .provider(AuthIdentity.Provider.PASSWORD.value())
        .username("user")
        .password("password")
        .build();

    // given: mock repository
    Role roleUser = Role.builder().code(Role.Code.ROLE_USER).build();
    doReturn(roleUser).when(roleRepository).findByCode(Role.Code.ROLE_USER);
    Role roleCustomer = Role.builder().code(Role.Code.ROLE_CUSTOMER).build();
    doReturn(roleCustomer).when(roleRepository).findByCode(Role.Code.ROLE_CUSTOMER);

    // when
    UserDTO created = userService.addNewCustomer(userDTO);

    // then
    assertThat(created.getRoles()).containsAnyOf(
        Role.Code.ROLE_USER.value(),
        Role.Code.ROLE_CUSTOMER.value()
    );
  }

  @Transactional
  @Test
  public void getUserByUsername_shouldReturnUser() {
    // given
    User created = prepareUser(AuthIdentity.Provider.PASSWORD);
    String username = created.getAuthIdentity().getUsername();

    // when
    User u = userService.getUserByUsername(username);

    // then
    assertThat(u).isNotNull();
  }

  @Test
  public void getUserByUsername_shouldReturnNull_whenNoSuchUserFound() {
    // given
    String username = "unknown";

    // when
    User u = userService.getUserByUsername(username);

    // then
    assertThat(u).isNull();
  }

  public User prepareUser(AuthIdentity.Provider provider) {
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
    entityManager.persist(user);
    entityManager.flush();

    return user;
  }
}