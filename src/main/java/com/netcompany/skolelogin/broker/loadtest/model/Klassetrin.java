package com.netcompany.skolelogin.broker.loadtest.model;

import java.util.Arrays;
import java.util.List;

public enum Klassetrin {
  DT("DT", null),
  NULTE("0", 0),
  FOERSTE("1", 1),
  ANDEN("2", 2),
  TREDJE("3", 3),
  FJERDE("4", 4),
  FEMTE("5", 5),
  SJETTE("6", 6),
  SYVENDE("7", 7),
  OTTENDE("8", 8),
  NIENDE("9", 9),
  TIENDE("10", 10),
  U1("U1", null),
  U2("U2", null),
  U3("U3", null),
  U4("U4", null),
  VU("VU", null),
  ANDET("Andet", null);

  public static List<Klassetrin> folkeskoleTrin = Arrays.asList(NULTE, FOERSTE, ANDEN, TREDJE, FJERDE, FEMTE, SJETTE, SYVENDE, OTTENDE, NIENDE, TIENDE);

  private String trin;
  private Integer grundskoleTrin;

  Klassetrin(String trin, Integer grundskoleTrin) {
    this.trin = trin;
    this.grundskoleTrin = grundskoleTrin;
  }

  public Integer getGrundskoleTrin() {
    return grundskoleTrin;
  }
}
