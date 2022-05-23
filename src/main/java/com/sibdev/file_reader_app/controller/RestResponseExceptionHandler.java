package com.sibdev.file_reader_app.controller;

import com.sibdev.file_reader_app.exception.ErrorMessage;
import com.sibdev.file_reader_app.exception.FileProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(NOT_ACCEPTABLE)
    public ErrorMessage handleExceptions(FileProcessingException ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorMessage(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().body(new ErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorMessage handleExceptions(Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ErrorMessage(ex.getMessage());
    }
}

