package com.github.lambda.playground.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.github.lambda.playground.domain.user.UserService;
import com.github.lambda.playground.domain.user.entity.AuthIdentity;
import com.github.lambda.playground.domain.user.entity.Permission;
import com.github.lambda.playground.domain.user.entity.Role;
import com.github.lambda.playground.domain.user.entity.RoleToUser;
import com.github.lambda.playground.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserService userRootRepository;

  @Autowired
  public UserDetailsServiceImpl(UserService userRootRepository) {
    this.userRootRepository = userRootRepository;
  }

  @Transactional
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRootRepository.getUserByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }

    AuthIdentity authIdentity = user.getAuthIdentity();

    // get permissions
    List<RoleToUser> roleToUsers = user.getRoleToUsers();
    List<GrantedAuthority> authorities = roleToUsers.stream().flatMap(r -> {
      return r.getRole().getPermissionToRoles().stream().map(p -> {
        return new SimpleGrantedAuthority(p.getPermission().getCode().value());
      });
    }).collect(Collectors.toList());

    UserPrincipal principal = UserPrincipal.builder()
        .username(authIdentity.getUsername())
        .password(authIdentity.getPassword())
        .authorities(authorities)
        .build();

    return principal;
  }
}
