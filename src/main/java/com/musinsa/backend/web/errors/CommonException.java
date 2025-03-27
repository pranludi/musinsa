package com.musinsa.backend.web.errors;

public class CommonException extends RuntimeException {

  private int code;
  private String message;

  public CommonException(int code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }

}
