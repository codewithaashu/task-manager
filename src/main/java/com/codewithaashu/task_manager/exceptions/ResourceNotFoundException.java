package com.codewithaashu.task_manager.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    private String resource;
    private String field;
    private String value;

    public ResourceNotFoundException(String resource, String field, String value) {
        super(String.format("%s is not found with %s: %s", resource, field, value));
    }
}
