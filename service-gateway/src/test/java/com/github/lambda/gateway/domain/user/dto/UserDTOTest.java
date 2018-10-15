package com.github.lambda.gateway.domain.user.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lambda.gateway.domain.user.entity.Role;
import com.github.lambda.gateway.swagger.model.UserDTO;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
public class UserDTOTest {
  MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
  ObjectMapper mapper = converter.getObjectMapper();

  @Test
  public void shouldHidePassword_inSerialization() throws Exception {
    // given
    UserDTO given = UserDTO.builder()
        .password("password")
        .build();
    String serialized = mapper.writeValueAsString(given);

    // when
    UserDTO deserialized = mapper.readValue(serialized, UserDTO.class);

    // then
    assertThat(deserialized.getPassword()).isNullOrEmpty();
  }

  @Test
  public void shouldIgnoreRoles_inDeserialization() throws Exception {
    // given
    List<String> roles = Sets.newHashSet(Role.Code.ROLE_USER, Role.Code.ROLE_CUSTOMER).stream()
        .map(Role.Code::value)
        .collect(Collectors.toList());

    UserDTO given = UserDTO.builder()
        .roles(roles)
        .build();
    String serialized = mapper.writeValueAsString(given);

    // when
    UserDTO deserialized = mapper.readValue(serialized, UserDTO.class);

    // then
    assertThat(deserialized.getRoles()).isNullOrEmpty();
  }
}