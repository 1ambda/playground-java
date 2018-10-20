package com.github.lambda.gateway.domain.user;

import com.github.lambda.gateway.domain.user.entity.AuthIdentity;
import com.github.lambda.gateway.domain.user.entity.Role;
import com.github.lambda.gateway.domain.user.entity.RoleToUser;
import com.github.lambda.gateway.domain.user.entity.User;
import com.github.lambda.gateway.domain.user.repository.*;
import com.github.lambda.gateway.swagger.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserActionFacade {
  private AuthIdentityRepository authIdentityRepository;
  private PermissionRepository permissionRepository;
  private PermissionToRoleRepository permissionToRoleRepository;
  private RoleRepository roleRepository;
  private RoleToUserRepository roleToUserRepository;
  private UserRepository userRepository;

  @Autowired
  public UserActionFacade(AuthIdentityRepository authIdentityRepository,
                          PermissionRepository permissionRepository,
                          PermissionToRoleRepository permissionToRoleRepository,
                          RoleRepository roleRepository,
                          RoleToUserRepository roleToUserRepository,
                          UserRepository userRepository) {
    this.authIdentityRepository = authIdentityRepository;
    this.permissionRepository = permissionRepository;
    this.permissionToRoleRepository = permissionToRoleRepository;
    this.roleRepository = roleRepository;
    this.roleToUserRepository = roleToUserRepository;
    this.userRepository = userRepository;
  }

  public User addNewCustomer(UserDTO userDTO,
                             AuthIdentity.Provider provider,
                             String encodedPassword) {

    AuthIdentity authIdentity = AuthIdentity.builder()
        .username(userDTO.getUsername())
        .password(encodedPassword)
        .provider(provider)
        .build();

    User user = User.builder()
        .name(userDTO.getName())
        .email(userDTO.getEmail())
        .address(userDTO.getAddress())
        .build();

    // create `User` and `AuthIdentity`
    user.setAuthIdentity(authIdentity);
    authIdentity.setUser(user);
    user = userRepository.save(user);

    // give `USER` role
    RoleToUser userRoleToUser = RoleToUser.builder().build();
    Role userRole = roleRepository.findByCode(Role.Code.ROLE_USER);
    userRole.addRoleToUser(userRoleToUser);
    user.addRoleToUser(userRoleToUser);
    roleRepository.save(userRole);

    // give `CUSTOMER` role
    RoleToUser customerRoleToUser = RoleToUser.builder().build();
    Role customerRole = roleRepository.findByCode(Role.Code.ROLE_CUSTOMER);
    customerRole.addRoleToUser(customerRoleToUser);
    user.addRoleToUser(customerRoleToUser);
    roleRepository.save(customerRole);

    return user;
  }
}
