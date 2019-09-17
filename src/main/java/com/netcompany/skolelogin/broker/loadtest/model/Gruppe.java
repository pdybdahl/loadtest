package com.netcompany.skolelogin.broker.loadtest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class Gruppe {
  private UUID uuid;
  private String id;
  private String name;
  private String institution;
  private String code;

  public Gruppe(UUID uuid, String name, String institution, String code) {
    this.uuid = uuid;
    this.id = uuid.toString();
    this.name = name;
    this.institution = institution;
    this.code = code;
  }

  @JsonIgnore
  public UUID getUuid() {
    return uuid;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getInstitution() {
    return institution;
  }

  public String getCode() {
    return code;
  }

}
