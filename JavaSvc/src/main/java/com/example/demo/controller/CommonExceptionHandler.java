package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import com.example.demo.api.ErrorResponse;

@ControllerAdvice
public class CommonExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public ErrorResponse handleError(Exception e, WebRequest req) {
        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                e.getMessage());
    }
}
