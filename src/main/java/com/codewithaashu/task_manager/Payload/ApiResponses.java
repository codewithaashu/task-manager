package com.codewithaashu.task_manager.Payload;

import java.util.List;

import lombok.Getter;

@Getter
public class ApiResponses<T> {

    private List<T> data;
    private String message;
    private Boolean status;

    public ApiResponses(List<T> dta, String msge, Boolean stats) {
        data = dta;
        message = msge;
        status = stats;
    }
}
