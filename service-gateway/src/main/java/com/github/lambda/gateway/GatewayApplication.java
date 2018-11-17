package com.github.lambda.gateway;

import com.github.lambda.gateway.config.ProfileManager;
import com.github.lambda.gateway.environment.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class GatewayApplication {

  @Autowired
  private Environment environment;

  public static void main(String[] args) {
    SpringApplication.run(GatewayApplication.class, args);
  }

  @Autowired
  private ProfileManager profileManager;

  @PostConstruct
  void afterStartup() {
    // setup Timezone
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

    // set local
    Locale.setDefault(new Locale("en", "US"));

    // configure application environment based on the current profile
    environment.setup();
  }

  @PreDestroy
  void beforeShutdown() {

  }
}
