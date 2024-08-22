package com.codewithaashu.task_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithaashu.task_manager.Entity.SubTask;

public interface SubTaskRepository extends JpaRepository<SubTask, Long> {

}
