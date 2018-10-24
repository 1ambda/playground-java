package com.github.lambda.gateway;

import com.github.lambda.gateway.config.ProfileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class GatewayApplication {

  @Autowired
  private LocalEnvironment localEnvironment;

  public static void main(String[] args) {
    SpringApplication.run(GatewayApplication.class, args);
  }

  @Autowired
  private ProfileManager profileManager;

  @PostConstruct
  void after() {
    // setup Timezone
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

    // set local
    Locale.setDefault(new Locale("en", "US"));

    // configure application
    if (profileManager.hasLocalProfile()) {
      localEnvironment.setup();
    }
  }

}
