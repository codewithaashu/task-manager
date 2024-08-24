package com.codewithaashu.task_manager.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithaashu.task_manager.Entity.Notification;
import com.codewithaashu.task_manager.Entity.User;
import com.codewithaashu.task_manager.Payload.NotificationDto;
import com.codewithaashu.task_manager.exceptions.ResourceNotFoundException;
import com.codewithaashu.task_manager.repository.NotificationRepository;
import com.codewithaashu.task_manager.repository.UserRepository;
import com.codewithaashu.task_manager.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;

    public List<NotificationDto> getUserNotifications(Long userId) {
        // get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));
        // get user's all notifications
        List<User> users = new ArrayList<>();
        users.add(user);
        List<Notification> notifications = notificationRepository.findByTeamIn(users);
        // get user's unread notifications
        List<Notification> unreadNotifications = notifications.stream().filter(notifi -> {
            Set<User> readUsers = notifi.getIsRead();
            return !readUsers.contains(user);
        }).collect(Collectors.toList());
        // change in return form and return it
        List<NotificationDto> notificationDtos = unreadNotifications.stream()
                .map(notify -> modelMapper.map(notify, NotificationDto.class)).collect(Collectors.toList());
        return notificationDtos;
    }

    @Override
    public void markNotificationRead(Long userId, Long id) {
        // get the notification
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", id.toString()));
        // get the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
        // add user in notificationReadUser list
        Set<User> notificationReadUser = notification.getIsRead();
        notificationReadUser.add(user);
        notification.setIsRead(notificationReadUser);
        // save in repository
        notificationRepository.save(notification);
    }

    @Override
    public void markNotificationsRead(Long userId) {
        // get the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));
        List<User> users = new ArrayList<>();
        users.add(user);
        // get all notifications
        List<Notification> notifications = notificationRepository.findByTeamIn(users);
        // add users to all notification isRead field
        notifications.stream().forEach(notifi -> {
            Set<User> notificationReadUser = notifi.getIsRead();
            notificationReadUser.add(user);
            notifi.setIsRead(notificationReadUser);
        });

        // save in repository
        notificationRepository.saveAll(notifications);
    }
}
