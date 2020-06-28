package com.example.demo.api;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ErrorResponse {

    private final HttpStatus status;

    private final String message;
}
