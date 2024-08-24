package com.codewithaashu.task_manager.service;

import java.util.List;

import com.codewithaashu.task_manager.Payload.NotificationDto;

public interface NotificationService {

    List<NotificationDto> getUserNotifications(Long userId);

    void markNotificationRead(Long userId, Long id);

    void markNotificationsRead(Long userId);
}
