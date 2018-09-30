package com.github.lambda.playground.domain.user.controller;

import javax.validation.Valid;

import com.github.lambda.playground.domain.user.UserService;
import com.github.lambda.playground.security.SecurityManager;
import com.github.lambda.playground.security.UserPrincipal;
import com.github.lambda.playground.swagger.api.AuthControllerApi;
import com.github.lambda.playground.swagger.model.UserDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

//  @Override
//  public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginRequest body) {
//    UserPrincipal principal = SecurityManager.getPrincipal();
//
//    UserDTO userDTO = UserDTO.builder()
//        .username(principal.getUsername())
//        .build();
//
//    return ResponseEntity.ok(userDTO);
//  }

  @ApiOperation(
      value = "",
      nickname = "login",
      notes = "",
      response = UserDTO.class,
      tags = {"auth-controller",},
      authorizations = {@Authorization(value = "basicAuth")}
  )
  @Override
  public ResponseEntity<UserDTO> login() {
    UserPrincipal principal = SecurityManager.getPrincipal();

    UserDTO dto = UserDTO.builder()
        .username(principal.getUsername())
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
  public ResponseEntity<UserDTO> whoami() {
    return null;
  }
}
