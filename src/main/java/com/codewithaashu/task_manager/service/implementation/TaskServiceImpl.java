package com.codewithaashu.task_manager.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithaashu.task_manager.Entity.Activites;
import com.codewithaashu.task_manager.Entity.SubTask;
import com.codewithaashu.task_manager.Entity.Task;
import com.codewithaashu.task_manager.Entity.User;
import com.codewithaashu.task_manager.Payload.ActivitiesDto;
import com.codewithaashu.task_manager.Payload.SubTaskDto;
import com.codewithaashu.task_manager.Payload.TaskDto;
import com.codewithaashu.task_manager.enums.Stage;
import com.codewithaashu.task_manager.exceptions.ResourceNotFoundException;
import com.codewithaashu.task_manager.repository.ActivitiesRepository;
import com.codewithaashu.task_manager.repository.SubTaskRepository;
import com.codewithaashu.task_manager.repository.TaskRepository;
import com.codewithaashu.task_manager.repository.UserRepository;
import com.codewithaashu.task_manager.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivitiesRepository activitiesRepository;

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        // change in entity
        Task task = modelMapper.map(taskDto, Task.class);
        Task savedTask = taskRepository.save(task);
        TaskDto saveTaskDto = modelMapper.map(savedTask, TaskDto.class);
        return saveTaskDto;
    }

    @Override
    public TaskDto getTask(Long id) {
        // find the task
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id.toString()));
        // we get task, so we convert into return form
        return modelMapper.map(task, TaskDto.class);
    }

    @Override
    public void trashTask(Long id) {
        // find the task
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id.toString()));
        // if task exist, change the isTrash value
        task.setIsTrashed(true);
        // save in repository
        taskRepository.save(task);
    }

    @Override
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        // find the task
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id.toString()));

        // set the value

        task.setTitle(taskDto.getTitle() == null ? task.getTitle() : taskDto.getTitle());
        task.setDate(taskDto.getDate() == null ? task.getDate() : taskDto.getDate());
        task.setAssets(taskDto.getAssets() == null ? task.getAssets() : taskDto.getAssets());
        // task.setTeam(taskDto.getTeam() == null ? task.getTeam() : taskDto.getTeam());
        task.setStage(taskDto.getStage() == null ? task.getStage() : taskDto.getStage());
        task.setPriority(taskDto.getPriority() == null ? task.getPriority() : taskDto.getPriority());

        // save in repo
        Task updatedTask = taskRepository.save(task);

        // change in return form
        return modelMapper.map(updatedTask, TaskDto.class);
    }

    @Override
    public void createSubTask(SubTaskDto subTaskDto, Long id) {
        // find the task
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id.toString()));
        // if task present then create a subTask
        SubTask subTask = modelMapper.map(subTaskDto, SubTask.class);
        // set the task in it
        subTask.setTask(task);
        // saved it
        subTaskRepository.save(subTask);
    }

    @Override
    public void postTaskActivity(Long taskId, Long userId, ActivitiesDto activitesDto) {
        // find the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId.toString()));

        // find the task
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId.toString()));

        // create a activities
        Activites activites = modelMapper.map(activitesDto, Activites.class);
        activites.setTask(task);
        activites.setBy(user);
        activitiesRepository.save(activites);
    }

    @Override
    public void deleteRestoreTask(String operation, Long id) {
        // find the task
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id.toString()));
        if (operation.equals("delete")) {
            // if we get task then delete it
            taskRepository.delete(task);
        } else if (operation.equals("restore")) {
            // if we get task then set isTrashed to be false
            task.setIsTrashed(false);
            // and save it
            taskRepository.save(task);
        }
    }

    @Override
    public void deleteRestoreTasks(String operation) {
        if (operation.equals("delete")) {
            List<Task> tasks = taskRepository.findByIsTrashed(true);
            tasks.forEach(task -> taskRepository.delete(task));
        } else if (operation.equals("restore")) {
            List<Task> tasks = taskRepository.findByIsTrashed(true);
            tasks.forEach(task -> {
                task.setIsTrashed(false);
                taskRepository.save(task);
            });
        }
    }

    @Override
    public TaskDto duplicateTask(Long id) {
        // find the task
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id.toString()));
        // create duplicate task and copy previous task and change the title only
        Task duplicateTask = new Task();
        duplicateTask.setTitle(task.getTitle() + " - Duplicate");
        duplicateTask.setAssets(task.getAssets());
        duplicateTask.setDate(task.getDate());
        duplicateTask.setPriority(task.getPriority());
        duplicateTask.setStage(task.getStage());
        duplicateTask.setIsTrashed(task.getIsTrashed());
        // save in repo
        Task savedTask = taskRepository.save(duplicateTask);
        return modelMapper.map(savedTask, TaskDto.class);
    }

    @Override
    public List<TaskDto> getTasksByStage(Stage stage) {
        List<TaskDto> taskDtos = new ArrayList<>();
        List<Task> tasks = taskRepository.findByStage(stage);
        taskDtos = tasks.stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
        return taskDtos;
    }

    @Override
    public List<TaskDto> getTasksByTrashed(Boolean isTrashed) {
        List<TaskDto> taskDtos = new ArrayList<>();
        List<Task> tasks = taskRepository.findByIsTrashed(isTrashed);
        taskDtos = tasks.stream().map(task -> modelMapper.map(task, TaskDto.class)).collect(Collectors.toList());
        return taskDtos;
    }
}
