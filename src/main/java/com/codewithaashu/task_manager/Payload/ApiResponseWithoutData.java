package com.codewithaashu.task_manager.Payload;

import lombok.Getter;

@Getter
public class ApiResponseWithoutData {
    private String message;
    private Boolean status;

    public ApiResponseWithoutData(String msge, Boolean stats) {
        this.message = msge;
        this.status = stats;
    }

}
