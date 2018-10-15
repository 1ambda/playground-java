package com.github.lambda.gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfig {
  @Bean
  @Profile(ProfileManager.PROFILE_LOCAL)
  public LoggingAspect loggingAspect(Environment env) {
    return new LoggingAspect(env);
  }
}
