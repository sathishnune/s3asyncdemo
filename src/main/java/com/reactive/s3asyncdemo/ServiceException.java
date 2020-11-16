package com.reactive.s3asyncdemo;

public class ServiceException extends RuntimeException {

  public ServiceException() {
    super();
  }

  public ServiceException(String message) {
    super(message);
  }
}
