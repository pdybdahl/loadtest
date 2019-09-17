package com.netcompany.skolelogin.broker.loadtest.main;

public class CreateUpdateResponseDTO {
  private String uuid;
  private boolean success;
  private ErrorCode error;

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public ErrorCode getError() {
    return error;
  }

  public void setError(ErrorCode error) {
    this.error = error;
  }
}