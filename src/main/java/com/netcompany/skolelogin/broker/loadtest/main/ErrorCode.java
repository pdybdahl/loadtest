package com.netcompany.skolelogin.broker.loadtest.main;

public enum ErrorCode {
  NOT_FOUND(1, "Requested entity not found"),
  WRONG_AKTOER_TYPE(2, "AktoerType was incompatible with method"),
  NO_BROKER_ID_FOUND(3, "Der er ingen aktører tilknyttet brugeren."),
  UNKNOWN_ERROR(4, "Ukendt fejl"),
  NO_ASSOCIATION(5, "No association between aktoer and student");

  private int code;
  private String message;

  private ErrorCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}