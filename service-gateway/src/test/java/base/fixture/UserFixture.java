package base.fixture;

import com.github.lambda.gateway.domain.user.UserService;
import com.github.lambda.gateway.domain.user.entity.AuthIdentity;
import com.github.lambda.gateway.domain.user.entity.Role;
import com.github.lambda.gateway.domain.user.entity.User;
import com.github.lambda.gateway.domain.user.repository.RoleRepository;
import com.github.lambda.gateway.swagger.model.UserDTO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.doReturn;

public interface UserFixture {

  UserService getUserService();

  RoleRepository getMockRoleRepository();

  default User prepareUser() {
    UserDTO userDTO = UserDTO.builder()
        .name("Unknown")
        .email("user@test.com")
        .address("Seoul, South Korea")
        .provider(AuthIdentity.Provider.PASSWORD.value())
        .username("unknown")
        .password("password")
        .build();

    // given: mock repository
    RoleRepository mockRoleRepository = getMockRoleRepository();
    Role roleUser = Role.builder().code(Role.Code.ROLE_USER).build();
    doReturn(roleUser).when(mockRoleRepository).findByCode(Role.Code.ROLE_USER);
    Role roleCustomer = Role.builder().code(Role.Code.ROLE_CUSTOMER).build();
    doReturn(roleCustomer).when(mockRoleRepository).findByCode(Role.Code.ROLE_CUSTOMER);

    UserService userService = getUserService();
    UserDTO created = userService.handleAddNewCustomerRequest(userDTO);
    User user = userService.getUserByUsername(created.getUsername());

    return user;
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  default Long prepareUserInTransaction() {
    User user = prepareUser();
    Long userId = user.getId();

    return userId;
  }
}
