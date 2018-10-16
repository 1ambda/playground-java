package com.github.lambda.gateway.security;

import com.github.lambda.gateway.exception.type.custom.InvalidUserPrincipalException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * SecurityService returns user principal (UserDetails).
 */
public class SecurityService {

  /**
   * @return null if principal is invalid.
   * @throws InvalidUserPrincipalException when username is null.
   */
  public static UserPrincipal getPrincipal() {
    UserPrincipal principal = getPrincipalOrNull();

    if (principal == null) {
      throw new InvalidUserPrincipalException("User principal is invalid.");
    }

    return principal;
  }

  public static boolean isAnonymousUser(Authentication auth) {
    return auth instanceof AnonymousAuthenticationToken;
  }

  public static UserPrincipal getPrincipalOrNull() {
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
