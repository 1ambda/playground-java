package com.github.lambda.gateway.domain.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivityQueryFacade {

  @Autowired
  public ActivityQueryFacade() {
  }
}
