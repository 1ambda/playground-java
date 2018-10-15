package integration.security;

import java.util.List;

import com.github.lambda.gateway.domain.user.UserService;
import com.github.lambda.gateway.domain.user.entity.AuthIdentity;
import com.github.lambda.gateway.exception.type.InternalServerException;
import com.github.lambda.gateway.exception.type.UnauthorizedException;
import integration.swagger.client.api.AuthControllerApi;
import com.github.lambda.gateway.swagger.model.Failure;
import com.github.lambda.gateway.swagger.model.UserDTO;
import base.AbstractIntegrationTest;
import integration.SwaggerClientApi;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class AuthenticationTest extends AbstractIntegrationTest {

  @Autowired private SwaggerClientApi swaggerClientApi;
  @Autowired private UserService userService;
  @Autowired private RedisTemplate<String, String> redisTemplate;

  @Test
  public void shouldRegister() {
    // given
    UserDTO request = createUserDTO();

    // when
    AuthControllerApi client = swaggerClientApi.getAuthClientApi(randomServerPort);
    UserDTO response = client.register(request);

    // then
    assertThat(response).isNotNull();
    assertThat(response.getUsername()).isEqualTo(request.getUsername());

    // then: password and email should be empty
    assertThat(response.getEmail()).isNullOrEmpty();
    assertThat(response.getPassword()).isNullOrEmpty();
  }

  @Test(expected = InternalServerException.class)
  public void shouldFailToRegister_WhenUserAlreadyRegistered() {
    // given: user already exists
    UserDTO request = createUserDTO();
    userService.addNewCustomer(request);

    // when
    AuthControllerApi client = swaggerClientApi.getAuthClientApi(randomServerPort);
    client.register(request);

    // then
    fail("should throw an exception");
  }

  @Test
  public void shouldLogin() {
    // given
    UserDTO request = createUserDTO();
    userService.addNewCustomer(request);

    // when
    AuthControllerApi client =
        swaggerClientApi.getAuthClientApiWithCredential(
            randomServerPort, request.getUsername(), request.getPassword());
    UserDTO response = client.login();

    // then
    assertThat(response).isNotNull();
    assertThat(response.getUsername()).isNotBlank();
  }

  @Test(expected = UnauthorizedException.class)
  public void shouldFailToLogin_WhenNoCredentialIncludedInRequest() {
    // when
    AuthControllerApi client =
        swaggerClientApi.getAuthClientApiWithCredential(randomServerPort, null, null);
    UserDTO response = client.login();

    // then
    fail("should throw an exception");
  }

  @Test(expected = UnauthorizedException.class)
  public void shouldFailToLogin_WhenPasswordIsInvalid() {
    // given
    UserDTO request = createUserDTO();
    userService.addNewCustomer(request);

    // when
    AuthControllerApi client =
        swaggerClientApi.getAuthClientApiWithCredential(
            randomServerPort, request.getUsername(), request.getPassword() + "!");
    UserDTO response = client.login();

    // then
    fail("should throw an exception");
  }

  @Test(expected = UnauthorizedException.class)
  public void shouldFailToLogin_WhenUsernameDoesNotExist() {
    // given
    UserDTO request = createUserDTO();
    userService.addNewCustomer(request);

    // when
    AuthControllerApi client =
        swaggerClientApi.getAuthClientApiWithCredential(
            randomServerPort, request.getUsername() + "z", request.getPassword());
    UserDTO response = client.login();

    // then
    fail("should throw an exception");
  }

  @Test
  public void shouldCreateSessionAndReturn_AfterLogin() {
    // given: register user
    UserDTO request = createUserDTO();
    userService.addNewCustomer(request);

    String redisSessionKey =
        new StringBuilder()
            .append("spring:session:index:")
            .append(
                "org.springframework.session.FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME:")
            .append(request.getUsername())
            .toString();

    // given: no keys in redis
    List<String> previousKeys = getRedisKeys(redisTemplate);
    assertThat(previousKeys).doesNotContain(redisSessionKey);

    // when: login
    TestRestTemplate rest = new TestRestTemplate(request.getUsername(), request.getPassword());
    ResponseEntity<UserDTO> response =
        rest.getForEntity(getBaseUrl() + "/auth/login", UserDTO.class);

    // then: validate response body
    UserDTO body = response.getBody();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(body).isNotNull();
    assertThat(body.getUsername()).isEqualTo(request.getUsername());

    // then: validate `SESSION` cookie in the response header.
    // example: `SESSION=MDIxYjZmYjUtZWZmYy00MTUzLTk5MjYtMzYxODFlZGQzZDFk; Path=/; HttpOnly`
    String setCookieValue = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    String sessionCookie = setCookieValue.split(";")[0];
    assertThat(sessionCookie).contains("SESSION");
    String sessionValue = sessionCookie.split("=")[1];
    assertThat(sessionValue).isNotBlank();

    // then: check redis session
    List<String> redisKeys = getRedisKeys(redisTemplate);
    assertThat(redisKeys.size()).isGreaterThan(0);
    assertThat(redisKeys).contains(redisSessionKey);
  }

  @Test
  public void shouldSendAlreadyLoggedOutFailure_WhenNoSessionHeaderExistsInRequest() {
    // given: register user
    UserDTO request = createUserDTO();
    userService.addNewCustomer(request);

    String redisSessionKey =
        new StringBuilder()
            .append("spring:session:index:")
            .append("org.springframework.session.FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME:")
            .append(request.getUsername())
            .toString();

    // given: no keys in redis
    List<String> previousKeys = getRedisKeys(redisTemplate);
    assertThat(previousKeys).doesNotContain(redisSessionKey);

    // given: login
    TestRestTemplate rest = new TestRestTemplate(request.getUsername(), request.getPassword());
    ResponseEntity<UserDTO> loginResponse =
        rest.getForEntity(getBaseUrl() + "/auth/login", UserDTO.class);

    // given: has set-cookie header
    String setCookieValue = loginResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    String sessionCookie = setCookieValue.split(";")[0];
    assertThat(sessionCookie).contains("SESSION");
    String sessionValue = sessionCookie.split("=")[1];
    assertThat(sessionValue).isNotBlank();

    // given: check redis session
    List<String> keysAfterLogin = getRedisKeys(redisTemplate);
    assertThat(keysAfterLogin.size()).isGreaterThan(0);
    assertThat(keysAfterLogin).contains(redisSessionKey);

    // when: logout
    HttpHeaders httpHeaders = new HttpHeaders();
    ResponseEntity<Failure> logoutResponse = new TestRestTemplate()
        .exchange(getBaseUrl() + "/auth/logout", HttpMethod.GET, new HttpEntity<Void>(httpHeaders), Failure.class);
    Failure logoutBody = logoutResponse.getBody();

    // then
    assertThat(logoutBody.getCode()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    assertThat(logoutBody.getMessage()).isEqualTo("Already logged out.");
  }

  @Test
  public void shouldRemoveSessionInRedis_AfterLogout() {
    // given: register user
    UserDTO request = createUserDTO();
    userService.addNewCustomer(request);

    String redisSessionKey =
        new StringBuilder()
            .append("spring:session:index:")
            .append("org.springframework.session.FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME:")
            .append(request.getUsername())
            .toString();

    // given: no keys in redis
    List<String> previousKeys = getRedisKeys(redisTemplate);
    assertThat(previousKeys).doesNotContain(redisSessionKey);

    // given: login
    TestRestTemplate rest = new TestRestTemplate(request.getUsername(), request.getPassword());
    ResponseEntity<UserDTO> loginResponse =
        rest.getForEntity(getBaseUrl() + "/auth/login", UserDTO.class);

    // given: has set-cookie header
    String setCookieValue = loginResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    String sessionCookie = setCookieValue.split(";")[0];
    assertThat(sessionCookie).contains("SESSION");
    String sessionValue = sessionCookie.split("=")[1];
    assertThat(sessionValue).isNotBlank();

    // given: check redis session
    List<String> keysAfterLogin = getRedisKeys(redisTemplate);
    assertThat(keysAfterLogin.size()).isGreaterThan(0);
    assertThat(keysAfterLogin).contains(redisSessionKey);

    // when: logout
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set("Cookie", sessionCookie);
    ResponseEntity<String> logoutResponse = new TestRestTemplate()
        .exchange(getBaseUrl() + "/auth/logout", HttpMethod.GET, new HttpEntity<Void>(httpHeaders), String.class);
    String logoutBody = logoutResponse.getBody();

    // then: validate logout response
    assertThat(logoutResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(logoutBody).isNotBlank();

    // then: validate session in redis
    List<String> keysAfterLogout = getRedisKeys(redisTemplate);
    assertThat(keysAfterLogout).doesNotContain(redisSessionKey);
  }

  public UserDTO createUserDTO() {
    return UserDTO.builder()
        .username("1ambda")
        .password("password")
        .address("Seoul")
        .email("1ambda@github.com")
        .name("Kun")
        .provider(AuthIdentity.Provider.PASSWORD.value())
        .build();
  }

}
