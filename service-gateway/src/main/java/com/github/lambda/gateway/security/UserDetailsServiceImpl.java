package com.github.lambda.gateway.security;

import java.util.List;
import java.util.stream.Collectors;

import com.github.lambda.gateway.domain.user.UserService;
import com.github.lambda.gateway.domain.user.entity.AuthIdentity;
import com.github.lambda.gateway.domain.user.entity.RoleToUser;
import com.github.lambda.gateway.domain.user.entity.User;
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

    List<RoleToUser> roleToUsers = user.getRoleToUsers();

    // get roles
    List<GrantedAuthority> authorities = roleToUsers.stream().map(r -> {
      return new SimpleGrantedAuthority(r.getRole().getCode().value());
    }).collect(Collectors.toList());

    UserPrincipal principal = UserPrincipal.builder()
        .username(authIdentity.getUsername())
        .password(authIdentity.getPassword())
        .authorities(authorities)
        .build();

    return principal;
  }
}
