package com.codewithaashu.task_manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codewithaashu.task_manager.Entity.Task;
import com.codewithaashu.task_manager.enums.Stage;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByIsTrashed(Boolean isTrashed);

    List<Task> findByStage(Stage stage);

}
