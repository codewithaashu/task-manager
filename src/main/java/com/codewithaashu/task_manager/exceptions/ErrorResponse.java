package com.codewithaashu.task_manager.exceptions;

import lombok.Data;

@Data
public class ErrorResponse<T> {
    T errors;
    Boolean status;

    public ErrorResponse(T errors, Boolean status) {
        this.errors = errors;
        this.status = status;
    }
}
