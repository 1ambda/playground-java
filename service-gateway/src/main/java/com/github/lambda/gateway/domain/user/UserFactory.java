package com.github.lambda.gateway.domain.user;

import com.github.lambda.gateway.domain.user.entity.Role;
import com.github.lambda.gateway.domain.user.entity.User;
import com.github.lambda.gateway.swagger.model.UserDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserFactory {

  public UserDTO convertToUserDTO(User user) {
    UserDTO.UserDTOBuilder builder = UserDTO.builder();

    Set<Role.Code> currentRoles = user.getRoleToUsers().stream()
        .map(roleToUser -> roleToUser.getRole().getCode())
        .collect(Collectors.toSet());

    // set DTO fields and clean some sensitive fields
    Set<String> roles = currentRoles.stream()
        .map(Role.Code::value)
        .collect(Collectors.toSet());

    builder.roles(new ArrayList<>(roles))
        .username(user.getAuthIdentity().getUsername())
        .password(null)
        .email(null)
        .build();

    return builder.build();
  }

}
