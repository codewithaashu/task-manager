package com.codewithaashu.task_manager.service;

import java.util.List;

import com.codewithaashu.task_manager.Payload.ActivitiesDto;
import com.codewithaashu.task_manager.Payload.SubTaskDto;
import com.codewithaashu.task_manager.Payload.TaskDto;
import com.codewithaashu.task_manager.enums.Stage;

public interface TaskService {
    TaskDto createTask(TaskDto taskDto);

    TaskDto getTask(Long id);

    void trashTask(Long id);

    TaskDto updateTask(Long id, TaskDto taskDto);

    void createSubTask(SubTaskDto subTask, Long id);

    void postTaskActivity(Long taskId, Long userId, ActivitiesDto activites);

    void deleteRestoreTask(String operation, Long id);

    void deleteRestoreTasks(String operation);

    TaskDto duplicateTask(Long id);

    List<TaskDto> getTasksByStage(Stage stage);

    List<TaskDto> getTasksByTrashed(Boolean trashed);

}
