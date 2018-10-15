package com.github.lambda.gateway.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.lambda.gateway.exception.factory.FailureFactory;
import com.github.lambda.gateway.security.SecurityManager;
import com.github.lambda.gateway.swagger.model.Failure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
@ControllerAdvice
public class SecurityExceptionHandler extends BasicAuthenticationEntryPoint {
  public static final String REALM = "RELAM_GATEWAY";

  private MappingJackson2HttpMessageConverter converter;
  private FailureFactory failureFactory;

  @Autowired
  public SecurityExceptionHandler(
      MappingJackson2HttpMessageConverter converter, FailureFactory securityFailureFactory) {
    this.converter = converter;
    this.failureFactory = securityFailureFactory;
  }

  public static String getUnauthorizedExceptionMessage(Throwable e) {
    HttpStatus status = HttpStatus.UNAUTHORIZED;
    String message = status.getReasonPhrase();

    if (e instanceof InsufficientAuthenticationException) {
      message = "Username or Password is invalid.";
    } else if (e instanceof UsernameNotFoundException) {
      message = "Failed to find username";
    } else if (e instanceof BadCredentialsException) {
      message = "Credential is incorrect";
    } else if (e instanceof AccountStatusException) {
      message = "Account is unavailable (locked or expired). Please contact to admin";
    } else if (e instanceof SessionAuthenticationException) {
      message = "Session is invalid. You might exceed the number of concurrent session limit.";
    }

    return message;
  }

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    // 401
    String message = getUnauthorizedExceptionMessage(authException);
    HttpStatus status = HttpStatus.UNAUTHORIZED;
    String path = SecurityManager.getOriginalRequestUri(request);
    Failure failure = failureFactory.build(authException, message, status.value(), path);

    HttpOutputMessage output = new ServletServerHttpResponse(response);
    response.setStatus(failure.getCode().intValue());
    converter.write(failure, MediaType.APPLICATION_JSON_UTF8, output);
  }

  @ExceptionHandler(value = {AccessDeniedException.class})
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException {
    // 403
    String message = "Don't have enough permission.";
    HttpStatus status = HttpStatus.FORBIDDEN;
    String path = SecurityManager.getOriginalRequestUri(request);
    Failure failure = failureFactory.build(accessDeniedException, message, status.value(), path);

    HttpOutputMessage output = new ServletServerHttpResponse(response);
    response.setStatus(failure.getCode().intValue());
    converter.write(failure, MediaType.APPLICATION_JSON_UTF8, output);
  }

  @ExceptionHandler(value = {Exception.class})
  public void commence(
      HttpServletRequest request, HttpServletResponse response, Exception exception)
      throws IOException {
    // 500
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    String message = "Unknown error occurred while handling authentication";
    String path = SecurityManager.getOriginalRequestUri(request);
    Failure failure = failureFactory.build(exception, message, status.value(), path);

    HttpOutputMessage output = new ServletServerHttpResponse(response);
    response.setStatus(failure.getCode().intValue());
    converter.write(failure, MediaType.APPLICATION_JSON_UTF8, output);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    setRealmName(REALM);
    super.afterPropertiesSet();
  }
}
