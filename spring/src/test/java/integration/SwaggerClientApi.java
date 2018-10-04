package integration;

import com.github.lambda.playground.swagger.client.api.AuthControllerApi;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.stereotype.Component;

@Component
public class SwaggerClientApi {

  public String getBaseUrl(int port) {
    return new StringBuilder()
        .append("http://localhost:")
        .append(port)
        .append("/api")
        .toString();
  }

  public AuthControllerApi getAuthClientApi(int port) {
    AuthControllerApi client =
        Feign.builder()
            .decoder(new JacksonDecoder())
            .encoder(new JacksonEncoder())
            .target(AuthControllerApi.class, getBaseUrl(port));

    return client;
  }
}
