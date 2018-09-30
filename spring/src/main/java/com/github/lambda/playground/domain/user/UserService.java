package com.github.lambda.playground.domain.user;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import com.github.lambda.playground.common.Time;
import com.github.lambda.playground.domain.user.entity.AuthIdentity;
import com.github.lambda.playground.domain.user.entity.Role;
import com.github.lambda.playground.domain.user.entity.RoleToUser;
import com.github.lambda.playground.domain.user.entity.User;
import com.github.lambda.playground.domain.user.repository.AuthIdentityRepository;
import com.github.lambda.playground.domain.user.repository.PermissionRepository;
import com.github.lambda.playground.domain.user.repository.PermissionToRoleRepository;
import com.github.lambda.playground.domain.user.repository.RoleRepository;
import com.github.lambda.playground.domain.user.repository.RoleToUserRepository;
import com.github.lambda.playground.domain.user.repository.UserRepository;
import com.github.lambda.playground.swagger.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private AuthIdentityRepository authIdentityRepository;
  private PermissionRepository permissionRepository;
  private PermissionToRoleRepository permissionToRoleRepository;
  private RoleRepository roleRepository;
  private RoleToUserRepository roleToUserRepository;
  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(AuthIdentityRepository authIdentityRepository,
                     PermissionRepository permissionRepository,
                     PermissionToRoleRepository permissionToRoleRepository,
                     RoleRepository roleRepository,
                     RoleToUserRepository roleToUserRepository,
                     UserRepository userRepository, PasswordEncoder passwordEncoder) {

    this.authIdentityRepository = authIdentityRepository;
    this.permissionRepository = permissionRepository;
    this.permissionToRoleRepository = permissionToRoleRepository;
    this.roleRepository = roleRepository;
    this.roleToUserRepository = roleToUserRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public User getUserByUsername(@NotNull String username) {
    User user = userRepository.findActiveOneByUsername(username, Time.getUTCDateTime());

    return user;
  }

  @Transactional(rollbackOn = Exception.class)
  public UserDTO addNewCustomer(@NotNull UserDTO userDTO) {

    // TODO: custom exception for invalid provider
    AuthIdentity.Provider provider = AuthIdentity.Provider.valueOf(userDTO.getProvider());
    String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

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

    Set<Role.Code> currentRoles = user.getRoleToUsers().stream()
        .map(roleToUser -> roleToUser.getRole().getCode())
        .collect(Collectors.toSet());

    // set DTO fields and clean some sensitive fields
    Set<String> roles = currentRoles.stream()
        .map(Role.Code::value)
        .collect(Collectors.toSet());
    userDTO.setRoles(new ArrayList<>(roles));
    userDTO.setPassword(null);
    userDTO.setEmail(null);

    return userDTO;
  }
}
