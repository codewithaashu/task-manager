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

import com.codewithaashu.task_manager.Entity.User;
import com.codewithaashu.task_manager.Payload.ApiResponseWithoutData;
import com.codewithaashu.task_manager.Payload.ApiResponses;
import com.codewithaashu.task_manager.Payload.NotificationDto;
import com.codewithaashu.task_manager.repository.UserRepository;
import com.codewithaashu.task_manager.service.implementation.NotificationServiceImpl;
import com.codewithaashu.task_manager.utils.MiddlewareUtils;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {
    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    @Autowired
    private MiddlewareUtils middleware;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity<ApiResponses<NotificationDto>> getUserNotificationController() {
        // middleware performed
        // User loggedInUser = middleware.getLoggedInUser(request);
        User loggedInUser = userRepository.findByEmail("admin@mts.com");
        List<NotificationDto> notificationDtos = notificationServiceImpl.getUserNotifications(loggedInUser);
        return new ResponseEntity<>(
                new ApiResponses<>(notificationDtos, "Successfully fetched user notifications", true), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseWithoutData> markNotificationReadController(@PathVariable("userId") Long userId,
            @PathVariable("id") Long id) {
        // middleware performed
        // User loggedInUser = middleware.getLoggedInUser(request);
        User loggedInUser = userRepository.findByEmail("admin@mts.com");
        notificationServiceImpl.markNotificationRead(loggedInUser, id);
        return new ResponseEntity<>(new ApiResponseWithoutData("Successfully marked as read", true), HttpStatus.OK);
    }

    @PatchMapping("")
    public ResponseEntity<ApiResponseWithoutData> markNotificationsReadController() {
        // middleware performed
        // User loggedInUser = middleware.getLoggedInUser(request);
        User loggedInUser = userRepository.findByEmail("admin@mts.com");
        notificationServiceImpl.markNotificationsRead(loggedInUser);
        return new ResponseEntity<>(new ApiResponseWithoutData("Successfully marked all notification as read", true),
                HttpStatus.OK);
    }
}
