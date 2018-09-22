package com.github.lambda.playground.domain.user.dto;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.lambda.playground.domain.user.entity.AuthIdentity;
import com.github.lambda.playground.domain.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
public class UserDTO {
  @JsonProperty("username")
  String username;

  @ToString.Exclude
  @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
  String password;

  @JsonProperty("provider")
  AuthIdentity.Provider provider;

  @ToString.Exclude
  @JsonProperty("name")
  String name;

  @ToString.Exclude
  @JsonProperty("email")
  String email;

  @ToString.Exclude
  @JsonProperty("address")
  String address;

  @Builder.Default
  @JsonProperty(value = "roles", access = JsonProperty.Access.READ_ONLY)
  Set<Role.Code> roles = new HashSet<>();
}
