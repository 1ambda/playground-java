package com.github.lambda.gateway.domain.user.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.github.lambda.gateway.domain.user.UserService;
import com.github.lambda.gateway.security.SecurityManager;
import com.github.lambda.gateway.security.UserPrincipal;
import com.github.lambda.gateway.swagger.model.UserDTO;
import com.github.lambda.gateway.swagger.server.api.AuthControllerApi;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api")
public class AuthController implements AuthControllerApi {

  private UserService userService;

  @Autowired
  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @ApiOperation(
      value = "",
      nickname = "login",
      notes = "",
      response = UserDTO.class,
      tags = {
        "auth-controller",
      },
      authorizations = {@Authorization(value = "basicAuth")})
  @Override
  public ResponseEntity<UserDTO> login() {
    UserPrincipal principal = SecurityManager.getPrincipal();

    List<String> roles = principal.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());

    UserDTO dto = UserDTO.builder()
        .username(principal.getUsername())
        .roles(roles)
        .build();
    return ResponseEntity.ok(dto);
  }

  @Override
  public ResponseEntity<Void> logout() {
    return null;
  }

  @Override
  public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO request) {

    UserDTO userDTO = userService.addNewCustomer(request);

    return ResponseEntity.ok(userDTO);
  }

  @Override
  public ResponseEntity<UserDTO> whoiam() {
    UserPrincipal principal = SecurityManager.getPrincipalOrNull();
    String username = null;

    if (principal != null) {
      username = principal.getUsername();
    }

    UserDTO userDTO = UserDTO.builder().username(username).build();

    return ResponseEntity.ok(userDTO);
  }
}
