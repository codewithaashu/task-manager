package com.codewithaashu.task_manager.Payload;

import java.util.ArrayList;
import java.util.List;

import com.codewithaashu.task_manager.enums.Priority;
import com.codewithaashu.task_manager.enums.Stage;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDto {
    private Long id;
    @NotNull(message = "title is required field")
    private String title;
    private String date;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Enumerated(EnumType.STRING)
    private Stage stage;
    private List<ActivitiesDto> activites = new ArrayList<>();
    private List<SubTaskDto> subTasks;
    private List<String> assets;
    private Boolean isTrashed;
    private List<UserDto> team;
}
