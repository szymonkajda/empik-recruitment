package com.empik.complaintsmanagement.adapter.in.web;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.empik.complaintsmanagement.openapi.model.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class RestControllerExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handle(IllegalArgumentException exception) {
    ErrorResponse errorResponse = new ErrorResponse().message(exception.getMessage());
    return ResponseEntity.status(BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handle(EntityNotFoundException exception) {
    ErrorResponse errorResponse = new ErrorResponse().message(exception.getMessage());
    return ResponseEntity.status(NOT_FOUND).body(errorResponse);
  }
}
