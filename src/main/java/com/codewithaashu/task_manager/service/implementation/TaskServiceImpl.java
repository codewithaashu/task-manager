package com.codewithaashu.task_manager.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.codewithaashu.task_manager.Entity.Activites;
import com.codewithaashu.task_manager.Entity.Notification;
import com.codewithaashu.task_manager.Entity.SubTask;
import com.codewithaashu.task_manager.Entity.Task;
import com.codewithaashu.task_manager.Entity.User;
import com.codewithaashu.task_manager.Payload.ActivitiesDto;
import com.codewithaashu.task_manager.Payload.SubTaskDto;
import com.codewithaashu.task_manager.Payload.TaskDto;
import com.codewithaashu.task_manager.enums.ActivityType;
import com.codewithaashu.task_manager.enums.Stage;
import com.codewithaashu.task_manager.exceptions.ResourceNotFoundException;
import com.codewithaashu.task_manager.repository.ActivitiesRepository;
import com.codewithaashu.task_manager.repository.NotificationRepository;
import com.codewithaashu.task_manager.repository.SubTaskRepository;
import com.codewithaashu.task_manager.repository.TaskRepository;
import com.codewithaashu.task_manager.service.TaskService;
import com.codewithaashu.task_manager.utils.SendEmailService;

import jakarta.mail.MessagingException;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SubTaskRepository subTaskRepository;

    @Autowired
    private ActivitiesRepository activitiesRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private SendEmailService sendEmailService;

    @Override
    public TaskDto createTask(TaskDto taskDto, User logginUser) {
        // create task
        // change in entity form
        Task task = modelMapper.map(taskDto, Task.class);
        // save in db
        Task savedTask = taskRepository.save(task);

        // create notfication
        Notification notification = new Notification();
        // create notification text
        String notifcationText = "New task has been assigned to you ";
        if (task.getTeam().size() > 1) {
            notifcationText += String.format("and %s others.", (task.getTeam().size() - 1));
        }
        notifcationText += String.format(
                "The task priority is set a %s priority, so check and act accordingly. The task date is %s. Thank you!!!",
                task.getPriority().toString(), task.getDate().toString());
        notification.setText(notifcationText);
        // get team
        List<User> teams = task.getTeam();
        List<User> notifyTeams = new ArrayList<>();
        for (User team : teams) {
            User notifyTeam = new User();
            notifyTeam.setId(team.getId());
            notifyTeams.add(notifyTeam);
        }
        notification.setTeam(notifyTeams);
        notification.setTask(savedTask);
        // save it
        notificationRepository.save(notification);

        // create activities
        Activites activites = new Activites();
        activites.setType(ActivityType.assigned);
        activites.setActivity(notifcationText);
        activites.setBy(logginUser);
        activites.setTask(savedTask);
        // save activity
        Activites savedActivites = activitiesRepository.save(activites);
        // save in task
        savedTask.getActivites().add(savedActivites);
        // change in return form
        TaskDto saveTaskDto = modelMapper.map(savedTask, TaskDto.class);
        return saveTaskDto;
    }

    @Override
    public TaskDto getTask(Long id) throws MessagingException {
        // find the task
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", id.toString()));
            Context context = new Context();
            context.setVariable("name", "Ashish Ranjan");
            context.setVariable("taskName", "Web Developer Task");
            context.setVariable("date", "16-08-2024");
            context.setVariable("priority", "Medium");
            context.setVariable("priority", "Medium");
            context.setVariable("link", "https://studygyaan.com/spring-boot/send-email-via-smtp-spring-boot");
        sendEmailService.sendEmail("ashishrajk123@gmail.com", "ashya2616@gmail.com", "testing","AssignTaskEmail",context);
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
        List<User> teamUser = taskDto.getTeam().stream().map(team -> modelMapper.map(team, User.class))
                .collect(Collectors.toList());
        task.setTeam(taskDto.getTeam() == null ? task.getTeam() : teamUser);
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
    public void postTaskActivity(User logginUser, Long taskId, ActivitiesDto activitesDto) {
        // find the task
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId.toString()));

        // create a activities
        Activites activites = modelMapper.map(activitesDto, Activites.class);
        activites.setTask(task);
        activites.setBy(logginUser);
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
        List<User> teamUser = task.getTeam().stream().map(team -> modelMapper.map(team, User.class))
                .collect(Collectors.toList());
        duplicateTask.setTeam(teamUser);
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
