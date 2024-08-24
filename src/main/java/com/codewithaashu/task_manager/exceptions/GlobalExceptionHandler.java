package com.codewithaashu.task_manager.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@ControllerAdvice // to make exception handler class
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        // get message
        String message = exception.getMessage();
        System.out.println("message---" + message);
        return new ResponseEntity<>(new ErrorResponse<String>(message, false), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse<String>> handleConstraintVoilationException(
            SQLIntegrityConstraintViolationException exception) {
        String message = exception.getMessage();
        String errorMessage = String.format("%s for value : '%s' in your table", message.split("'")[0],
                message.split("'")[1]);
        return new ResponseEntity<>(new ErrorResponse<String>(errorMessage, false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse<String>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        String message = exception.getMessage();
        String errorMessage = String.format("%s%s", message.split("`")[0], message.split("`")[2]);
        return new ResponseEntity<>(new ErrorResponse<String>(errorMessage, false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<HashMap<String, String>>> handleArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        HashMap<String, String> errorMap = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(ex -> {
            String errorMessage = ex.getDefaultMessage();
            String field = ((FieldError) ex).getField();

            errorMap.put(field, errorMessage);
        });
        return new ResponseEntity<>(new ErrorResponse<HashMap<String, String>>(errorMap, false),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse<String>> handleBadCredentialException(BadCredentialsException exception) {
        return new ResponseEntity<>(new ErrorResponse<String>(exception.getMessage(), false), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse<String>> handleExpiredJwtException(ExpiredJwtException exception) {
        return new ResponseEntity<>(new ErrorResponse<String>("Token provided is expired", false),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse<String>> handleJwtException(JwtException exception) {
        return new ResponseEntity<>(new ErrorResponse<String>(exception.getMessage(), false),
                HttpStatus.UNAUTHORIZED);
    }

}
