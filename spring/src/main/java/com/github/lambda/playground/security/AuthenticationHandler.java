package com.github.lambda.playground.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.lambda.playground.swagger.model.Failure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

  private static final MediaType CONTENT_TYPE_JSON = MediaType.APPLICATION_JSON_UTF8;
  private MappingJackson2HttpMessageConverter converter;

  @Autowired
  public AuthenticationHandler(MappingJackson2HttpMessageConverter converter) {
    this.converter = converter;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication)
      throws IOException, ServletException {

  }

  @Override
  public void onAuthenticationFailure(HttpServletRequest request,
                                      HttpServletResponse response,
                                      AuthenticationException exception)
      throws IOException, ServletException {

//    Failure failure = Failure.builder()
//    HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);
//    converter.write(result, CONTENT_TYPE_JSON, outputMessage); // Responseに書き込む
//    response.setStatus(HttpStatus.OK.value()); // 200 OK.
  }

}
