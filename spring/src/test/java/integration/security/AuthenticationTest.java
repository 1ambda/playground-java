package integration.security;

import com.github.lambda.playground.domain.user.UserService;
import com.github.lambda.playground.domain.user.entity.AuthIdentity;
import com.github.lambda.playground.swagger.client.api.AuthControllerApi;
import com.github.lambda.playground.swagger.model.UserDTO;
import integration.AbstractIntegrationTest;
import integration.SwaggerClientApi;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthenticationTest extends AbstractIntegrationTest {

  @Autowired SwaggerClientApi swaggerClientApi;

  @Test
  public void shouldRegister() {
    // given
    UserDTO request =
        UserDTO.builder()
            .username("1ambda")
            .password("password")
            .address("Seoul")
            .email("1ambda@github.com")
            .name("Kun")
            .provider(AuthIdentity.Provider.PASSWORD.value())
            .build();

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
}
