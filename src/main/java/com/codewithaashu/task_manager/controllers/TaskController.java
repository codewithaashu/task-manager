package com.codewithaashu.task_manager.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithaashu.task_manager.Payload.ActivitiesDto;
import com.codewithaashu.task_manager.Payload.ApiResponse;
import com.codewithaashu.task_manager.Payload.ApiResponseWithoutData;
import com.codewithaashu.task_manager.Payload.ApiResponses;
import com.codewithaashu.task_manager.Payload.SubTaskDto;
import com.codewithaashu.task_manager.Payload.TaskDto;
import com.codewithaashu.task_manager.enums.Stage;
import com.codewithaashu.task_manager.service.implementation.TaskServiceImpl;

@RestController // to make this class to be controller class
@RequestMapping("/api/v1/task") // to define the leading url
public class TaskController {
    @Autowired
    private TaskServiceImpl taskServiceImpl;

    @PostMapping("")
    public ResponseEntity<ApiResponse<TaskDto>> createTaskController(@RequestBody TaskDto taskDto) {
        TaskDto savedTaskDto = taskServiceImpl.createTask(taskDto);
        return new ResponseEntity<>(new ApiResponse<TaskDto>(savedTaskDto, "Task created successfull", true),
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> getTaskController(@PathVariable Long id) {
        TaskDto taskDto = taskServiceImpl.getTask(id);
        return new ResponseEntity<>(new ApiResponse<TaskDto>(taskDto, "Fetched successfully", true),
                HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponseWithoutData> trashTaskController(@PathVariable Long id) {
        taskServiceImpl.trashTask(id);
        return new ResponseEntity<>(new ApiResponseWithoutData("Task trashed successfully", true), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> updateTaskController(@PathVariable Long id,
            @RequestBody TaskDto taskDto) {
        TaskDto updateTask = taskServiceImpl.updateTask(id, taskDto);
        return new ResponseEntity<>(new ApiResponse<TaskDto>(updateTask, "Task updated successfully", true),
                HttpStatus.OK);
    }

    @PostMapping("/sub-task/{id}")
    public ResponseEntity<ApiResponseWithoutData> createSubTaskController(@PathVariable Long id,
            @RequestBody SubTaskDto subTaskDto) {
        taskServiceImpl.createSubTask(subTaskDto, id);
        return new ResponseEntity<>(new ApiResponseWithoutData("SubTask added successfully.", true), HttpStatus.OK);
    }

    @PostMapping("/activities/{userId}/{taskId}")
    public ResponseEntity<ApiResponseWithoutData> postTaskActivityController(
            @PathVariable(name = "userId", required = true) Long userId,
            @PathVariable(name = "taskId", required = true) Long taskId,
            @RequestBody ActivitiesDto activites) {
        taskServiceImpl.postTaskActivity(taskId, userId, activites);
        return new ResponseEntity<>(new ApiResponseWithoutData("Activity posted successfully.", true), HttpStatus.OK);
    }

    @DeleteMapping("/{operation}/{id}")
    public ResponseEntity<ApiResponseWithoutData> deleteRestoreTaskController(
            @PathVariable(name = "operation", required = true) String operation,
            @PathVariable(name = "id", required = true) Long id) {
        taskServiceImpl.deleteRestoreTask(operation, id);
        return new ResponseEntity<>(new ApiResponseWithoutData("Operation performed successfully.", true),
                HttpStatus.OK);
    }

    @DeleteMapping("/{operation}")
    public ResponseEntity<ApiResponseWithoutData> deleteRestoreTasksController(
            @PathVariable(name = "operation", required = true) String operation) {
        taskServiceImpl.deleteRestoreTasks(operation);
        return new ResponseEntity<>(new ApiResponseWithoutData("Operation performed successfully.", true),
                HttpStatus.OK);
    }

    @GetMapping("/duplicate/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> duplicateTaskController(
            @PathVariable(name = "id", required = true) Long id) {
        TaskDto taskDto = taskServiceImpl.duplicateTask(id);
        return new ResponseEntity<>(new ApiResponse<TaskDto>(taskDto, "Task duplicated successfully", true),
                HttpStatus.CREATED);
    }

    @GetMapping("trashed-tasks/{trashed}")
    public ResponseEntity<ApiResponses<TaskDto>> getTasksController(
            @PathVariable(name = "trashed") Boolean trashed) {
        List<TaskDto> taskDtos = taskServiceImpl.getTasksByTrashed(trashed);
        return new ResponseEntity<>(new ApiResponses<>(taskDtos, "Tasks retrieved successfully", true), HttpStatus.OK);
    }

    @GetMapping("staged-tasks/{stage}")
    public ResponseEntity<ApiResponses<TaskDto>> getTasksController(
            @PathVariable(name = "stage") Stage stage) {
        List<TaskDto> taskDtos = taskServiceImpl.getTasksByStage(stage);
        return new ResponseEntity<>(new ApiResponses<>(taskDtos, "Tasks retrieved successfully", true), HttpStatus.OK);
    }
}
