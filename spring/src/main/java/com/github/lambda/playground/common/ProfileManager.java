package com.github.lambda.playground.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ProfileManager {
  Environment environment;

  public static final String PROFILE_LOCAL = "local";
  public static final String PROFILE_TEST = "test";

  @Autowired
  public ProfileManager(Environment environment) {
    this.environment = environment;
  }

  public boolean hasLocalProfile() {
    for (final String profileName : environment.getActiveProfiles()) {
      if (PROFILE_LOCAL.equals(profileName)) {
        return true;
      }
    }

    return false;
  }

  public boolean hasTestProfile() {
    for (final String profileName : environment.getActiveProfiles()) {
      if (PROFILE_TEST.equals(profileName)) {
        return true;
      }
    }

    return false;
  }
}

