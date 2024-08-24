package com.codewithaashu.task_manager.Payload;

import java.util.List;

import lombok.Data;

@Data
public class NotificationDto {
    private Long id;
    private List<UserDto> team;
    private String text;
    private TaskDto task;
    private String notiType;
    private List<UserDto> isRead;
}
