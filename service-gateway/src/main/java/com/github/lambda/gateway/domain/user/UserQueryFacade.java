package com.github.lambda.gateway.domain.user;

import com.github.lambda.gateway.common.Time;
import com.github.lambda.gateway.domain.user.entity.User;
import com.github.lambda.gateway.domain.user.repository.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserQueryFacade {
  private AuthIdentityRepository authIdentityRepository;
  private PermissionRepository permissionRepository;
  private PermissionToRoleRepository permissionToRoleRepository;
  private RoleRepository roleRepository;
  private RoleToUserRepository roleToUserRepository;
  private UserRepository userRepository;

  public UserQueryFacade(AuthIdentityRepository authIdentityRepository,
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

  public User getUserByUsername(String username) {
    User user = userRepository.findActiveOneByUsername(username, Time.getCurrentUTCDateTime());

    return user;
  }
}
