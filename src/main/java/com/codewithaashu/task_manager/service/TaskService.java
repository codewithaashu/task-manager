package com.codewithaashu.task_manager.service;

import java.util.List;

import com.codewithaashu.task_manager.Entity.User;
import com.codewithaashu.task_manager.Payload.ActivitiesDto;
import com.codewithaashu.task_manager.Payload.SubTaskDto;
import com.codewithaashu.task_manager.Payload.TaskDto;
import com.codewithaashu.task_manager.enums.Stage;

import jakarta.mail.MessagingException;

public interface TaskService {
    TaskDto createTask(TaskDto taskDto, User logginUser);

    TaskDto getTask(Long id) throws MessagingException;

    void trashTask(Long id);

    TaskDto updateTask(Long id, TaskDto taskDto);

    void createSubTask(SubTaskDto subTask, Long id);

    void postTaskActivity(User logginUser, Long taskId, ActivitiesDto activites);

    void deleteRestoreTask(String operation, Long id);

    void deleteRestoreTasks(String operation);

    TaskDto duplicateTask(Long id);

    List<TaskDto> getTasksByStage(Stage stage);

    List<TaskDto> getTasksByTrashed(Boolean trashed);

}
