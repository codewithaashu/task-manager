package com.codewithaashu.task_manager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithaashu.task_manager.Payload.ApiResponseWithoutData;
import com.codewithaashu.task_manager.Payload.ApiResponses;
import com.codewithaashu.task_manager.Payload.NotificationDto;
import com.codewithaashu.task_manager.service.implementation.NotificationServiceImpl;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {
    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponses<NotificationDto>> getUserNotificationController(@PathVariable Long userId) {
        List<NotificationDto> notificationDtos = notificationServiceImpl.getUserNotifications(userId);
        return new ResponseEntity<>(
                new ApiResponses<>(notificationDtos, "Successfully fetched user notifications", true), HttpStatus.OK);
    }

    @PatchMapping("/{userId}/{id}")
    public ResponseEntity<ApiResponseWithoutData> markNotificationReadController(@PathVariable("userId") Long userId,
            @PathVariable("id") Long id) {
        notificationServiceImpl.markNotificationRead(userId, id);
        return new ResponseEntity<>(new ApiResponseWithoutData("Successfully marked as read", true), HttpStatus.OK);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<ApiResponseWithoutData> markNotificationsReadController(@PathVariable("userId") Long userId) {
        notificationServiceImpl.markNotificationsRead(userId);
        return new ResponseEntity<>(new ApiResponseWithoutData("Successfully marked all notification as read", true),
                HttpStatus.OK);
    }
}
