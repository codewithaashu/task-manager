package com.codewithaashu.task_manager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.codewithaashu.task_manager.Payload.ApiResponseWithoutData;

@ControllerAdvice // to make exception handler class
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseWithoutData> handleResourceNotFoundException(ResourceNotFoundException exception) {
        // get message
        String message = exception.getMessage();
        return new ResponseEntity<>(new ApiResponseWithoutData(message, false), HttpStatus.NOT_FOUND);
    }

}
