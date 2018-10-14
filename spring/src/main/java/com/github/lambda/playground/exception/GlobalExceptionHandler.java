package com.github.lambda.playground.exception;

import com.github.lambda.playground.exception.factory.FailureFactory;
import com.github.lambda.playground.exception.type.BadRequestException;
import com.github.lambda.playground.exception.type.ForbiddenException;
import com.github.lambda.playground.exception.type.UnauthorizedException;
import com.github.lambda.playground.swagger.model.Failure;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private FailureFactory failureFactory;

  @Autowired
  public GlobalExceptionHandler(FailureFactory failureFactory) {
    this.failureFactory = failureFactory;
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

    // set path
    String path = null;
    if (request instanceof ServletWebRequest) {
      path = ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    // set request attributes
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
      request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
    }

    if (body instanceof Failure) {
      return new ResponseEntity<>(body, headers, status);
    }

    logger.error(ex);

    // if passe body is not `Failure` type,
    Failure failure = failureFactory.build(ex, "Unknown error occurred.", status.value(), path);
    return new ResponseEntity<>(failure, headers, status);
  }

  @ExceptionHandler(value = {
      BadRequestException.class,
      IllegalArgumentException.class,
  })
  protected ResponseEntity<Object> handleBadRequestException(Exception ex, WebRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, null, httpHeaders, status, request);
  }

  @ExceptionHandler(value = {UnauthorizedException.class})
  protected ResponseEntity<Object> handleUnauthorizedException(Exception ex, WebRequest request) {
    HttpStatus status = HttpStatus.UNAUTHORIZED;
    HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, null, httpHeaders, status, request);
  }

  @ExceptionHandler(value = {ForbiddenException.class})
  protected ResponseEntity<Object> handleForbiddenException(Exception ex, WebRequest request) {
    HttpStatus status = HttpStatus.FORBIDDEN;
    HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, null, httpHeaders, status, request);
  }

  @ExceptionHandler(value = {Exception.class})
  protected ResponseEntity<Object> handleUnknownException(Exception ex, WebRequest request) {
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    HttpHeaders httpHeaders = new HttpHeaders();

    return handleExceptionInternal(ex, null, httpHeaders, status, request);
  }
}
