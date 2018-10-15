package com.github.lambda.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ProfileManager {
  Environment environment;

  public static final String PROFILE_LOCAL = "local";
  public static final String PROFILE_TEST = "test";
  public static final String PROFILE_NON_TEST = "!test";
  public static final String PROFILE_INTEGRATION = "integration";
  public static final String PROFILE_STAGING = "stag";
  public static final String PROFILE_PROD = "prod";

  @Autowired
  public ProfileManager(Environment environment) {
    this.environment = environment;
  }

  public boolean hasLocalProfile() {
    for (final String profileName : environment.getActiveProfiles()) {
      if (PROFILE_LOCAL.equalsIgnoreCase(profileName)) {
        return true;
      }
    }

    return false;
  }

  public boolean hasTestProfile() {
    for (final String profileName : environment.getActiveProfiles()) {
      if (PROFILE_TEST.equalsIgnoreCase(profileName)) {
        return true;
      }
    }

    return false;
  }
}

