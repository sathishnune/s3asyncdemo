package com.reactive.s3asyncdemo.advice;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestControllerServiceAdvice {

  /**
   * Generic Exception handler. Returns the customized exception to UI than complete stack trace.
   *
   * @param runtimeException Runtime exception
   * @return ResponseEntity with 500 as status code and exception message
   */
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException runtimeException) {

    return ResponseEntity.status(500)
        .body(ErrorResponse.builder().errorMessage(runtimeException.getLocalizedMessage()).build());
  }

  @Data
  @Builder
  static class ErrorResponse {
    private String errorMessage;
  }
}
