package com.netcompany.skolelogin.broker.loadtest.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Kontaktperson implements Aktoer {
  private String type;
  private UUID aktoerUuid;
  private String cpr;
  private String efternavn;
  private String fornavn;
  private String localPersonId;
  private String uniId;
  private String institutionId;
  private String initialPassword;
  private UUID elevUuid;
  private boolean myndighed;

  @JsonGetter("@type")
  public String getType() {
    return "Kontaktperson";
  }

  public UUID getAktoerUuid() {
    return aktoerUuid;
  }

  public void setAktoerUuid(UUID aktoerUuid) {
    this.aktoerUuid = aktoerUuid;
  }

  public String getCpr() {
    return cpr;
  }

  public void setCpr(String cpr) {
    this.cpr = cpr;
  }

  public String getEfternavn() {
    return efternavn;
  }

  public void setEfternavn(String efternavn) {
    this.efternavn = efternavn;
  }

  public String getFornavn() {
    return fornavn;
  }

  public void setFornavn(String fornavn) {
    this.fornavn = fornavn;
  }

  public String getLocalPersonId() {
    return localPersonId;
  }

  public void setLocalPersonId(String localPersonId) {
    this.localPersonId = localPersonId;
  }

  public String getUniId() {
    return uniId;
  }

  public void setUniId(String uniId) {
    this.uniId = uniId;
  }

  public String getInstitutionId() {
    return institutionId;
  }

  public void setInstitutionId(String institutionId) {
    this.institutionId = institutionId;
  }

  public String getInitialPassword() {
    return initialPassword;
  }

  public void setInitialPassword(String initialPassword) {
    this.initialPassword = initialPassword;
  }

  public UUID getElevUuid() {
    return elevUuid;
  }

  public void setElevUuid(UUID elevUuid) {
    this.elevUuid = elevUuid;
  }

  public boolean isMyndighed() {
    return myndighed;
  }

  public void setMyndighed(boolean myndighed) {
    this.myndighed = myndighed;
  }

}
