package com.codewithaashu.task_manager.service;

import java.util.List;

import com.codewithaashu.task_manager.Entity.User;
import com.codewithaashu.task_manager.Payload.NotificationDto;

public interface NotificationService {

    List<NotificationDto> getUserNotifications(User user);

    void markNotificationRead(User user, Long id);

    void markNotificationsRead(User user);
}
