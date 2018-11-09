package com.github.lambda.gateway.domain.activity;

import com.github.lambda.gateway.domain.activity.entity.*;
import com.github.lambda.gateway.domain.activity.repository.ActivityRepository;
import com.github.lambda.gateway.domain.activity.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActivityService {

  private ActivityQueryFacade activityQueryFacade;
  private ActivityActionFacade activityActionFacade;

  // TODO: remove
  private ActivityRepository activityRepository;
  private NotificationRepository notificationRepository;

  @Autowired
  public ActivityService(ActivityQueryFacade activityQueryFacade,
                         ActivityActionFacade activityActionFacade,
                         ActivityRepository activityRepository,
                         NotificationRepository notificationRepository) {

    this.activityQueryFacade = activityQueryFacade;
    this.activityActionFacade = activityActionFacade;
    this.activityRepository = activityRepository;
    this.notificationRepository = notificationRepository;
  }

  @Transactional
  public Activity addNewTestActivity(Long userId, Long targetUserId) {

    Long actorId = userId;
    Long objectId = null;

    Activity activity = Activity.builder()
        .action(ActivityAction.TEST)
        .actorId(userId)
        .objectId(objectId)
        .build();

    activity = activityRepository.save(activity);

    Long recipientId = targetUserId;

    Notification notification = Notification.builder()
        .activity(activity)
        .segment(NotificationSegment.USER)
        .platform(NotificationPlatform.ALL)
        .state(NotificationState.SENT)
        .recipientId(recipientId)
        .build();

    notification = notificationRepository.save(notification);

    return activity;
  }
}
