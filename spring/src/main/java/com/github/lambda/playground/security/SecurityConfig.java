package com.github.lambda.playground.security;

import com.github.lambda.playground.config.ProfileManager;
import com.github.lambda.playground.exception.SecurityExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.NullRequestCache;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  SecurityExceptionHandler securityEntryPoint;

  @Autowired
  private ProfileManager profileManager;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  SecurityLogoutHandler securityLogoutHandler;

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
  public void configure(WebSecurity web) throws Exception {
    if (profileManager.hasTestProfile()) {
      web.debug(true);
    }
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
        .cors().and().csrf().disable()
        .exceptionHandling().authenticationEntryPoint(securityEntryPoint).and()
        .httpBasic().authenticationEntryPoint(securityEntryPoint).realmName(SecurityExceptionHandler.REALM).and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
        .rememberMe().disable()
        // NullRequestCache prevents creation of session
        // when user is not authenticated so that
        // we have only authenticated users’ session ids stored in Redis.
        .requestCache().requestCache(new NullRequestCache());

    http.logout()
        .logoutSuccessHandler(securityLogoutHandler)
        .clearAuthentication(true)
        .invalidateHttpSession(true)
        .deleteCookies("SESSION")
        .logoutUrl("/api/auth/logout");

    http
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/v2/api-docs").permitAll()
        .antMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
        .antMatchers(HttpMethod.GET, "/configuration/**").permitAll()
        .antMatchers(HttpMethod.GET, "/documentation/**").permitAll()
        .antMatchers(HttpMethod.GET, "/api/auth/whoiam").permitAll()
        .antMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
        .antMatchers(HttpMethod.GET, "/api/auth/login").permitAll()
        .antMatchers("/api/**").hasRole("USER")
        .anyRequest().authenticated();
    // @formatter:on
  }
}
