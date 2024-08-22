package com.codewithaashu.task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithaashu.task_manager.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
