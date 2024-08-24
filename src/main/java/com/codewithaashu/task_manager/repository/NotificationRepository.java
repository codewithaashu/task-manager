package com.codewithaashu.task_manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codewithaashu.task_manager.Entity.Notification;
import com.codewithaashu.task_manager.Entity.User;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByTeamIn(List<User> users);
}
