package com.selectit.akamaidemo.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(value = "com.selectit.akamaidemo")
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundExc.class)
  public ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundExc exc) {

    Map<String, Object> responseBody = new HashMap<>(2);
    responseBody.put("timestamp", LocalDateTime.now());
    responseBody.put("message", exc.getMessage());

    return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
  }
  @ExceptionHandler(Throwable.class)
  public ResponseEntity<Object> globalExceptionHandler(Throwable exc) {

    Map<String, Object> responseBody = new HashMap<>(2);
    responseBody.put("timestamp", LocalDateTime.now());
    responseBody.put("message", exc.getMessage());

    return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
