package com.codewithaashu.task_manager.Payload;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubTaskDto {
    @NotBlank
    private String title;
    @NotBlank
    private String date;
    private String tag;
    private TaskDto taskDto;
}
