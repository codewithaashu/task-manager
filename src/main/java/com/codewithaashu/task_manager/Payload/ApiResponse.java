package com.codewithaashu.task_manager.Payload;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private T data;
    private String message;
    private Boolean status;

    public ApiResponse(T dta, String msge, Boolean stats) {
        data = dta;
        message = msge;
        status = stats;
    }
}
