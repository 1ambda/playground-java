package com.github.lambda.gateway.security;

import com.github.lambda.gateway.domain.user.UserService;
import com.github.lambda.gateway.domain.user.entity.User;
import com.github.lambda.gateway.exception.type.custom.InvalidUserPrincipalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

/**
 * SecurityService returns user principal (UserDetails).
 */
@Service
public class SecurityService {

  private UserService userService;

  @Autowired
  public SecurityService(UserService userService) {
    this.userService = userService;
  }

  /**
   * @return null if principal is invalid.
   * @throws InvalidUserPrincipalException when username is null.
   */
  public UserPrincipal getPrincipalThrow() {
    UserPrincipal principal = getPrincipalOrNull();

    if (principal == null) {
      throw new InvalidUserPrincipalException("User principal is invalid.");
    }

    return principal;
  }

  public boolean isAnonymousUser(Authentication auth) {
    return auth instanceof AnonymousAuthenticationToken;
  }

  @Transactional
  public long getUserIdOrThrow() {
    UserPrincipal principal = getPrincipalThrow();

    User user = userService.getUserByUsername(principal.getUsername());
    if (user == null) {
      throw new InvalidUserPrincipalException("Invalid username");
    }

    long userId = user.getId();
    return userId;
  }

  public UserPrincipal getPrincipalOrNull() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null) {
      return null;
    }

    if (isAnonymousUser(auth)) {
      return null;
    }

    Object raw = auth.getPrincipal();
    if (!(raw instanceof UserPrincipal)) {
      return null;
    }

    UserPrincipal principal = (UserPrincipal) auth.getPrincipal();

    if (StringUtils.isEmpty(principal.getUsername())) {
      return null;
    }

    return principal;
  }

  public static String getOriginalRequestUri(HttpServletRequest request) {
    Object forwarded = request.getAttribute("javax.servlet.forward.request_uri");
    if (forwarded == null) {
      return request.getServletPath();
    }

    return forwarded.toString();
  }
}
