package com.github.lambda.playground.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * SecurityManager returns user principal (UserDetails).
 */
public class SecurityManager {

  /**
   * @return null if principal is invalid.
   */
  public static UserPrincipal getPrincipal() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null) {
      return null;
    }

    if (!(auth instanceof UserPrincipal)) {
      return null;
    }

    return (UserPrincipal) auth.getPrincipal();
  }
}
