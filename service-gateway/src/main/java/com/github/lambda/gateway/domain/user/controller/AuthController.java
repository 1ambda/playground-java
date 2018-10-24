package com.github.lambda.gateway.domain.user.controller;

import com.github.lambda.gateway.domain.user.UserService;
import com.github.lambda.gateway.security.SecurityService;
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
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api")
public class AuthController implements AuthControllerApi {

  private UserService userService;
  private SecurityService securityService;

  @Autowired
  public AuthController(UserService userService,
                        SecurityService securityService) {

    this.userService = userService;
    this.securityService = securityService;
  }

  @Override
  @ApiOperation(
      value = "",
      nickname = "login",
      notes = "",
      response = UserDTO.class,
      tags = {
          "auth-controller",
      },
      authorizations = {@Authorization(value = "basicAuth")})
  @RequestMapping(value = "/auth/login",
                  produces = {"application/json"},
                  method = RequestMethod.GET)
  public ResponseEntity<UserDTO> login() {
    UserPrincipal principal = securityService.getPrincipalThrow();

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
  @RequestMapping(value = "/auth/logout",
                  produces = {"application/json"},
                  consumes = {"application/json"},
                  method = RequestMethod.GET)
  public ResponseEntity<Void> logout() {
    return null;
  }

  @Override
  @RequestMapping(value = "/auth/register",
                  produces = {"application/json"},
                  consumes = {"application/json"},
                  method = RequestMethod.POST)
  public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO request) {

    UserDTO userDTO = userService.handleAddNewCustomerRequest(request);

    return ResponseEntity.ok(userDTO);
  }

  @Override
  @RequestMapping(value = "/auth/whoiam",
                  produces = {"application/json"},
                  method = RequestMethod.GET)
  public ResponseEntity<UserDTO> whoiam() {
    UserPrincipal principal = securityService.getPrincipalOrNull();
    String username = null;

    if (principal != null) {
      username = principal.getUsername();
    }

    UserDTO userDTO = UserDTO.builder().username(username).build();

    return ResponseEntity.ok(userDTO);
  }
}
