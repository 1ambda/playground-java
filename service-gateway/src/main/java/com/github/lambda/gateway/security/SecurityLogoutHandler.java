package com.github.lambda.gateway.security;

import com.github.lambda.gateway.exception.factory.FailureFactory;
import com.github.lambda.gateway.exception.type.custom.AlreadyLoggedOutException;
import com.github.lambda.gateway.swagger.model.Failure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SecurityLogoutHandler implements LogoutSuccessHandler {

  private MappingJackson2HttpMessageConverter converter;
  private FailureFactory failureFactory;

  @Autowired
  public SecurityLogoutHandler(MappingJackson2HttpMessageConverter converter,
                               FailureFactory failureFactory) {
    this.converter = converter;
    this.failureFactory = failureFactory;
  }

  @Override
  public void onLogoutSuccess(HttpServletRequest request,
                              HttpServletResponse response,
                              Authentication authentication)
      throws IOException, ServletException {

    if (authentication == null) {
      AlreadyLoggedOutException e = new AlreadyLoggedOutException("Already logged out.");
      HttpStatus status = HttpStatus.UNAUTHORIZED;
      String path = SecurityManager.getOriginalRequestUri(request);
      Failure failure = failureFactory.build(e, null, status.value(), path);

      HttpOutputMessage output = new ServletServerHttpResponse(response);
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      converter.write(failure, MediaType.APPLICATION_JSON_UTF8, output);
      return;
    }

    HttpOutputMessage output = new ServletServerHttpResponse(response);
    response.setStatus(HttpStatus.OK.value());
    converter.write("{}", MediaType.APPLICATION_JSON_UTF8, output);
  }
}
