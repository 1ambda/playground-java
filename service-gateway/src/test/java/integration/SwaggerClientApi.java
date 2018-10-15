package integration;

import integration.swagger.client.api.AuthControllerApi;
import feign.Feign;
import feign.auth.BasicAuthRequestInterceptor;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
public class SwaggerClientApi {

  public String getBaseUrl(int port) {
    return new StringBuilder()
        .append("http://localhost:")
        .append(port)
        .append("/api")
        .toString();
  }

  public <T> T buildClient (Class<? extends T> type, int port, String username, String password) {
    Feign.Builder builder =
        Feign.builder()
            .decoder(new JacksonDecoder())
            .encoder(new JacksonEncoder())
            .errorDecoder(new SwaggerClientErrorDecoder());

    if (username != null && password != null) {
      builder
          .requestInterceptor(new BasicAuthRequestInterceptor(username, password, Charset.forName("UTF-8")));
    }

    T client = builder.target(type, getBaseUrl(port));
    return client;
  }

  public AuthControllerApi getAuthClientApi(int port) {
    return buildClient(AuthControllerApi.class, port, null, null);
  }

  public AuthControllerApi getAuthClientApiWithCredential(int port, String username, String password) {
    return buildClient(AuthControllerApi.class, port, username, password);
  }
}
