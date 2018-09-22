package com.github.lambda.playground.security;

import com.github.lambda.playground.domain.user.UserService;
import com.github.lambda.playground.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private UserService userRootRepository;

  @Autowired
  public UserDetailsServiceImpl(UserService userRootRepository) {
    this.userRootRepository = userRootRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRootRepository.getUserByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }

    UserPrincipal principal = UserPrincipal.builder().user(user).build();
    return principal;
  }
}
