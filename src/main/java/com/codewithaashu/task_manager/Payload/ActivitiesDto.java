package com.codewithaashu.task_manager.Payload;

import com.codewithaashu.task_manager.enums.ActivityType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActivitiesDto {
    private Long id;
    @Enumerated(EnumType.STRING)
    private ActivityType type;
    @NotBlank(message = "activity is required field")
    private String activity;
    private String date;
    private UserDto by;
    // private TaskDto task;
}
