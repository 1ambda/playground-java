package com.github.lambda.playground.security;

import com.github.lambda.playground.config.ProfileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private AuthenticationHandler authenticationHandler;

  @Autowired
  private ProfileManager profileManager;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Bean
  public DaoAuthenticationProvider buildAuthenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    AuthenticationProvider provider = buildAuthenticationProvider();
    auth.authenticationProvider(provider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors().and().csrf().disable()
        .exceptionHandling().authenticationEntryPoint()
        .formLogin().successHandler(authenticationHandler).failureHandler(authenticationHandler).and()
        .httpBasic().and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
        .rememberMe().disable();

    http
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/v2/api-docs").permitAll()
        .antMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
        .antMatchers(HttpMethod.GET, "/configuration/**").permitAll()
        .antMatchers(HttpMethod.GET, "/documentation/**").permitAll()
        .antMatchers(HttpMethod.GET, "/api/auth/whoami").permitAll()
        .antMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
        .antMatchers(HttpMethod.GET, "/api/auth/login").permitAll()
        .antMatchers("/api/**").hasRole("USER")
        .anyRequest().authenticated();
  }

}
