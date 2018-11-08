package com.github.lambda.gateway.domain.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

  private ActivityQueryFacade activityQueryFacade;
  private ActivityActionFacade activityActionFacade;

  @Autowired
  public ActivityService(ActivityQueryFacade activityQueryFacade,
                         ActivityActionFacade activityActionFacade) {

    this.activityQueryFacade = activityQueryFacade;
    this.activityActionFacade = activityActionFacade;
  }
}
