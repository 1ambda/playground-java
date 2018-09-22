package com.github.lambda.playground.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.lambda.playground.domain.user.entity.Role;
import com.github.lambda.playground.domain.user.entity.RoleToUser;
import com.github.lambda.playground.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString(exclude = {"user"})
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPrincipal implements UserDetails {
  private User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<RoleToUser> roleToUsers = user.getRoleToUsers();

    List<GrantedAuthority> authorities = roleToUsers.stream().flatMap(r -> {
      return r.getRole().getPermissionToRoles().stream().map(p -> {
        return new SimpleGrantedAuthority(p.getPermission().getCode().value());
      });
    }).collect(Collectors.toList());

    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getAuthIdentity().getPassword();
  }

  @Override
  public String getUsername() {
    return user.getAuthIdentity().getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
