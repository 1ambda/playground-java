package com.github.lambda.gateway.environment;

import com.github.lambda.gateway.config.ProfileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"stag", "prod"})
@Configuration
public class DeployEnvironment implements Environment {

  @Autowired
  private ProfileManager profileManager;

  @Override
  public void setup() {

  }
}
