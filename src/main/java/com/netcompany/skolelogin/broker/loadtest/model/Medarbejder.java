package com.netcompany.skolelogin.broker.loadtest.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@XmlRootElement
public class Medarbejder implements Aktoer {
  private String type;
  private UUID aktoerUuid;
  private String cpr;
  private String efternavn;
  private String fornavn;
  private String localPersonId;
  private String uniId;
  private String institutionId;
  private String initialPassword;
  private String rolle;
  private List<String> grupper = new ArrayList<String>();

  @JsonGetter("@type")
  public String getType() {
    return "Medarbejder";
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

  public String getRolle() {
    return rolle;
  }

  public void setRolle(String rolle) {
    this.rolle = rolle;
  }

  public List<String> getGrupper() {
    return grupper;
  }

  public void setGrupper(List<String> grupper) {
    this.grupper = grupper;
  }
}
