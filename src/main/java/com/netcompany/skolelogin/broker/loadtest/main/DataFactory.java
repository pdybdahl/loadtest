package com.netcompany.skolelogin.broker.loadtest.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcompany.skolelogin.broker.loadtest.model.*;
import com.netcompany.skolelogin.broker.loadtest.util.ErstatningspersonnummerGenerator;
import com.netcompany.skolelogin.broker.loadtest.util.ValuesFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public class DataFactory {
  public static final String INITIAL_PASSWORD = "1234";
  private List<String> muligeDrengenavne;
  private List<String> muligePigenavne;
  private List<String> muligeEfternavne;
  private List<String> muligeInstitutionsnumre;
  private List<Klassetrin> muligeKlassetrin;

  private Map<String, Map<Klassetrin, List<Gruppe>>> klasserPrInstitution;
  private Map<String, Map<Klassetrin, Gruppe>> årgangePrInstitution;
  private Map<String, List<Gruppe>> lærerteamsPrInstitution;

  private List<Gruppe> grupper;
  private List<Aktoer> aktører;

  private int drengenavnIndex;
  private int pigenavnIndex;
  private int efternavnIndex;
  private int institutionsnummerIndex;

  private int elevTæller;
  private int kontaktpersonTæller;
  private int medarbejderTæller;
  private int medarbejderOgKontaktpersonTæller;
  private int elevOgMedarbejderTæller;

  public void produceLoadData(int startLøbenummer) throws IOException {
    initialize(startLøbenummer);

    createGroupData();
    createPersonData();
  }

  public List<Gruppe> getGrupper() {
    return grupper;
  }

  public List<Aktoer> getAktører() {
    return aktører;
  }

  private void createPersonData() throws IOException {
    aktører = new ArrayList<>();

    int elevStartår = 2000;
    int elevSlutår = 2010;
    int antalEleverPrDag = 2;

    List<Elev> elever = new ArrayList<>();
    for (int år = elevStartår; år <= elevSlutår; år++) {
      for (int måned = 1; måned <= 12; måned++) {
        int månedslængde = YearMonth.of(år, måned).lengthOfMonth();
        for (int dag = 1; dag <= månedslængde; dag++) {
          for (int i = 1; i <= antalEleverPrDag; i++) {
            Elev elev = createElevMedKlassetrin(år, måned, dag, i);
            aktører.add(elev);
            elever.add(elev);
          }
        }
      }
    }

    int medarbejderStartår = 1980;
    int medarbejderSlutår = 1985;
    int antalMedarbejderePrDag = 1;

    for (int år = medarbejderStartår; år <= medarbejderSlutår; år++) {
      for (int måned = 1; måned <= 12; måned++) {
        int månedslængde = YearMonth.of(år, måned).lengthOfMonth();
        for (int dag = 1; dag <= månedslængde; dag++) {
          for (int i = 1; i <= antalMedarbejderePrDag; i++) {
            String uniId = "m" + medarbejderTæller;
            medarbejderTæller++;
            aktører.add(createMedarbejder(år, måned, dag, i, uniId));
          }
        }
      }
    }

    int kontaktpersonEtBarnStartår = 1960;
    int kontaktpersonEtBarnSlutår = 1964;
    int antalKontaktpersonerMedEtBarnPrDag = 1;

    for (int år = kontaktpersonEtBarnStartår; år <= kontaktpersonEtBarnSlutår; år++) {
      for (int måned = 1; måned <= 12; måned++) {
        int månedslængde = YearMonth.of(år, måned).lengthOfMonth();
        for (int dag = 1; dag <= månedslængde; dag++) {
          for (int i = 1; i <= antalKontaktpersonerMedEtBarnPrDag; i++) {
            Collections.shuffle(elever);
            Elev elev = elever.get(0);
            aktører.addAll(createKontaktperson(år, måned, dag, i, elev));
          }
        }
      }
    }

    int kontaktpersonToBørnStartår = 1965;
    int kontaktpersonToBørnSlutår = 1969;
    int antalKontaktpersonerMedToBørnPrDag = 1;

    for (int år = kontaktpersonToBørnStartår; år <= kontaktpersonToBørnSlutår; år++) {
      for (int måned = 1; måned <= 12; måned++) {
        int månedslængde = YearMonth.of(år, måned).lengthOfMonth();
        for (int dag = 1; dag <= månedslængde; dag++) {
          for (int i = 1; i <= antalKontaktpersonerMedToBørnPrDag; i++) {
            Collections.shuffle(elever);
            Elev barn1 = elever.get(0);
            Elev barn2 = elever.get(1);
            aktører.addAll(createKontaktperson(år, måned, dag, i, barn1, barn2));
          }
        }
      }
    }

    int medarbejderOgKontaktpersonÅrgang = 1971;
    for (int måned = 1; måned <= 12; måned++) {
      int månedslængde = YearMonth.of(medarbejderOgKontaktpersonÅrgang, måned).lengthOfMonth();
      for (int dag = 1; dag <= månedslængde; dag++) {
        Collections.shuffle(elever);
        Elev elev = elever.get(0);
        aktører.addAll(createMedarbejderOgKontaktperson(medarbejderOgKontaktpersonÅrgang, måned, dag, 1, elev));
      }
    }

    int elevOgMedarbejderÅrgang = 1999;
    for (int måned = 1; måned <= 12; måned++) {
      int månedslængde = YearMonth.of(medarbejderOgKontaktpersonÅrgang, måned).lengthOfMonth();
      for (int dag = 1; dag <= månedslængde; dag++) {
        aktører.addAll(createElevOgMedarbejder(elevOgMedarbejderÅrgang, måned, dag, 1));
      }
    }

  }

  private void createGroupData() throws IOException {
    grupper = new ArrayList<>();

    for (String institutionsnr : muligeInstitutionsnumre) {
      for (Klassetrin klassetrin : muligeKlassetrin) {
        Gruppe årgang = årgangePrInstitution.get(institutionsnr).get(klassetrin);
        grupper.add(årgang);

        for (Gruppe klasse : klasserPrInstitution.get(institutionsnr).get(klassetrin)) {
          grupper.add(klasse);
        }
      }

      List<Gruppe> lærerteams = lærerteamsPrInstitution.get(institutionsnr);
      for (Gruppe lærerteam : lærerteams) {
        grupper.add(lærerteam);
      }
    }

  }

  public void initialize(int startLøbenummer) throws IOException {
    muligeDrengenavne = ValuesFactory.genererMuligeDrengenavne();
    Collections.shuffle(muligeDrengenavne);
    muligePigenavne = ValuesFactory.genererMuligePigenavne();
    Collections.shuffle(muligePigenavne);
    muligeEfternavne = ValuesFactory.genererMuligeEfternavne();
    Collections.shuffle(muligeEfternavne);
    muligeInstitutionsnumre = ValuesFactory.genererMuligeInstitutionsnumre();
    muligeInstitutionsnumre = muligeInstitutionsnumre.subList(0, 20);
    muligeKlassetrin = new ArrayList<>(Klassetrin.folkeskoleTrin);

    klasserPrInstitution = new HashMap<>();
    årgangePrInstitution = new HashMap<>();
    lærerteamsPrInstitution = new HashMap<>();

    for (String institutionsnr : muligeInstitutionsnumre) {
      årgangePrInstitution.put(institutionsnr, new HashMap<>());
      klasserPrInstitution.put(institutionsnr, new HashMap<>());
      lærerteamsPrInstitution.put(institutionsnr, new ArrayList<>());

      int institutionLøbenummer = 1;

      for (Klassetrin klassetrin : muligeKlassetrin) {
        Gruppe årgang = new Gruppe(UUID.randomUUID(), klassetrin.getGrundskoleTrin().toString() + ". årgang", institutionsnr, institutionsnr + "-" + institutionLøbenummer);
        institutionLøbenummer++;

        årgangePrInstitution.get(institutionsnr).put(klassetrin, årgang);

        klasserPrInstitution.get(institutionsnr).put(klassetrin, new ArrayList<>());
        for (String spor : Arrays.asList("a", "b", "c", "d")) {
          Gruppe klasse = new Gruppe(UUID.randomUUID(), klassetrin.getGrundskoleTrin() + spor, institutionsnr, institutionsnr + "-" + institutionLøbenummer);
          institutionLøbenummer++;

          klasserPrInstitution.get(institutionsnr).get(klassetrin).add(klasse);
        }
      }

      List<String> teams = Arrays.asList("Matematik - indskoling", "Matematik - mellemtrin", "Matematik - udskoling", "Dansk - indskoling", "Dansk - mellemtrin", "Dansk - udskoling", "Idræt - indskoling", "Idræt - mellemtrin", "Idræt - udskoling");
      for (String team : teams) {
        Gruppe lærerteam = new Gruppe(UUID.randomUUID(), team, institutionsnr, institutionsnr + "-" + institutionLøbenummer);
        institutionLøbenummer++;
        lærerteamsPrInstitution.get(institutionsnr).add(lærerteam);
      }
    }

    drengenavnIndex = 0;
    pigenavnIndex = 0;
    efternavnIndex = 0;
    institutionsnummerIndex = 0;

    elevTæller = startLøbenummer;
    kontaktpersonTæller = startLøbenummer;
    medarbejderTæller = startLøbenummer;
    medarbejderOgKontaktpersonTæller = startLøbenummer;
    elevOgMedarbejderTæller = startLøbenummer;
  }

  private Elev createElevMedKlassetrin(int år, int måned, int dag, int løbenummer) {
    Elev elev = new Elev();
    elev.setCpr(ErstatningspersonnummerGenerator.genererPersonnummer(år, måned, dag, løbenummer));
    elev.setAktoerUuid(UUID.randomUUID());
    elev.setKlassetrin(getRandomKlassetrin());
    elev.setEfternavn(getRandomEfternavn());
    if (løbenummer % 2 == 0) {
      elev.setFornavn(getRandomPigenavn());
    } else {
      elev.setFornavn(getRandomDrengenavn());
    }
    String uniId = "e" + elevTæller;
    elevTæller++;
    elev.setLocalPersonId(uniId);
    elev.setUniId(uniId);

    elev.setInstitutionId(getRandomInstitutionsnummer());
    elev.setInitialPassword(INITIAL_PASSWORD);
    Gruppe hovedgruppe = getKlasse(elev.getInstitutionId(), elev.getKlassetrin());
    elev.setHovedgruppeUuid(hovedgruppe.getUuid());
    List<String> grupper = new ArrayList<>();
    grupper.add(hovedgruppe.getId());
    Gruppe årgang = årgangePrInstitution.get(elev.getInstitutionId()).get(elev.getKlassetrin());
    grupper.add(årgang.getId());
    elev.setGrupper(grupper);

    return elev;
  }

  private Medarbejder createMedarbejder(int år, int måned, int dag, int løbenummer, String uniId) {
    Medarbejder medarbejder = new Medarbejder();
    medarbejder.setCpr(ErstatningspersonnummerGenerator.genererPersonnummer(år, måned, dag, løbenummer));
    medarbejder.setAktoerUuid(UUID.randomUUID());
    medarbejder.setEfternavn(getRandomEfternavn());
    if (løbenummer % 2 == 0) {
      medarbejder.setFornavn(getRandomPigenavn());
    } else {
      medarbejder.setFornavn(getRandomDrengenavn());
    }
    medarbejder.setLocalPersonId(uniId);
    medarbejder.setUniId(uniId);

    medarbejder.setInstitutionId(getRandomInstitutionsnummer());
    medarbejder.setInitialPassword(INITIAL_PASSWORD);

    List<Gruppe> grupper = new ArrayList<>();
    grupper.add(getKlasse(medarbejder.getInstitutionId()));
    grupper.add(getKlasse(medarbejder.getInstitutionId()));
    grupper.add(getLærerteam(medarbejder.getInstitutionId()));
    grupper.add(getLærerteam(medarbejder.getInstitutionId()));
    medarbejder.setGrupper(grupper.stream().map(g -> g.getId()).collect(Collectors.toList()));

    return medarbejder;
  }

  private List<Kontaktperson> createKontaktperson(int år, int måned, int dag, int løbenummer, Elev... børn) {
    List<Kontaktperson> result = new ArrayList<>();

    Kontaktperson kontaktperson = new Kontaktperson();
    kontaktperson.setCpr(ErstatningspersonnummerGenerator.genererPersonnummer(år, måned, dag, løbenummer));
    kontaktperson.setAktoerUuid(UUID.randomUUID());
    kontaktperson.setEfternavn(getRandomEfternavn());
    if (løbenummer % 2 == 0) {
      kontaktperson.setFornavn(getRandomPigenavn());
    } else {
      kontaktperson.setFornavn(getRandomDrengenavn());
    }
    String uniId = "k" + kontaktpersonTæller;
    kontaktpersonTæller++;
    kontaktperson.setLocalPersonId(uniId);
    kontaktperson.setUniId(uniId);

    kontaktperson.setInitialPassword(INITIAL_PASSWORD);

    if (børn.length == 0) {
      result.add(kontaktperson);
    } else if (børn.length == 1) {
      kontaktperson.setElevUuid(børn[0].getAktoerUuid());
      result.add(kontaktperson);
    } else {
      kontaktperson.setElevUuid(børn[0].getAktoerUuid());
      result.add(kontaktperson);

      for (int i = 1; i < børn.length; i++) {
        Kontaktperson aktør = new Kontaktperson();
        aktør.setCpr(kontaktperson.getCpr());
        aktør.setAktoerUuid(UUID.randomUUID());
        aktør.setEfternavn(kontaktperson.getEfternavn());
        aktør.setFornavn(kontaktperson.getFornavn());
        aktør.setLocalPersonId(kontaktperson.getLocalPersonId());
        aktør.setUniId(kontaktperson.getUniId());
        aktør.setInitialPassword(kontaktperson.getInitialPassword());
        aktør.setElevUuid(børn[i].getAktoerUuid());
        result.add(aktør);
      }
    }

    return result;
  }

  private List<Aktoer> createMedarbejderOgKontaktperson(int år, int måned, int dag, int løbenummer, Elev barn) {
    List<Aktoer> result = new ArrayList<>();

    String uniId = "mk" + medarbejderOgKontaktpersonTæller;
    medarbejderOgKontaktpersonTæller++;

    Medarbejder medarbejder = createMedarbejder(år, måned, dag, løbenummer, uniId);
    result.add(medarbejder);

    Kontaktperson kontaktperson = new Kontaktperson();
    kontaktperson.setCpr(medarbejder.getCpr());
    kontaktperson.setAktoerUuid(UUID.randomUUID());
    kontaktperson.setEfternavn(medarbejder.getEfternavn());
    kontaktperson.setFornavn(medarbejder.getFornavn());
    kontaktperson.setLocalPersonId(medarbejder.getLocalPersonId());
    kontaktperson.setUniId(medarbejder.getUniId());
    kontaktperson.setInitialPassword(medarbejder.getInitialPassword());
    kontaktperson.setElevUuid(barn.getAktoerUuid());
    result.add(kontaktperson);

    return result;
  }

  private List<Aktoer> createElevOgMedarbejder(int år, int måned, int dag, int løbenummer) {
    List<Aktoer> result = new ArrayList<>();

    String uniId = "em" + elevOgMedarbejderTæller;
    elevOgMedarbejderTæller++;

    Medarbejder medarbejder = createMedarbejder(år, måned, dag, løbenummer, uniId);
    result.add(medarbejder);

    Elev elev = new Elev();
    elev.setCpr(medarbejder.getCpr());
    elev.setAktoerUuid(UUID.randomUUID());
    elev.setKlassetrin(getRandomKlassetrin());
    elev.setEfternavn(medarbejder.getEfternavn());
    elev.setFornavn(medarbejder.getFornavn());
    elev.setLocalPersonId(medarbejder.getLocalPersonId());
    elev.setUniId(medarbejder.getUniId());

    elev.setInstitutionId(medarbejder.getInstitutionId());
    elev.setInitialPassword(INITIAL_PASSWORD);
    Gruppe hovedgruppe = getKlasse(elev.getInstitutionId(), elev.getKlassetrin());
    elev.setHovedgruppeUuid(hovedgruppe.getUuid());
    List<String> grupper = new ArrayList<>();
    grupper.add(hovedgruppe.getId());
    Gruppe årgang = årgangePrInstitution.get(elev.getInstitutionId()).get(elev.getKlassetrin());
    grupper.add(årgang.getId());
    elev.setGrupper(grupper);
    result.add(elev);

    return result;
  }

  private Gruppe getLærerteam(String institutionsnr) {
    List<Gruppe> lærerteams = lærerteamsPrInstitution.get(institutionsnr);
    Collections.shuffle(lærerteams);
    return lærerteams.get(0);
  }

  private Gruppe getKlasse(String institutionsnr) {
    Klassetrin klassetrin = getRandomKlassetrin();
    return getKlasse(institutionsnr, klassetrin);
  }

  private Gruppe getKlasse(String institutionsnr, Klassetrin klassetrin) {
    List<Gruppe> klasser = klasserPrInstitution.get(institutionsnr).get(klassetrin);
    Collections.shuffle(klasser);
    return klasser.get(0);
  }

  private String createUniId(String fornavn, String efternavn, String cpr) {
    return fornavn.substring(0, Math.min(2, fornavn.length())) + efternavn.substring(0, Math.min(2, efternavn.length())) + cpr.substring(6, 10);
  }

  private String getRandomDrengenavn() {
    if (drengenavnIndex >= muligeDrengenavne.size()) {
      drengenavnIndex = 0;
    }
    String result = muligeDrengenavne.get(drengenavnIndex);
    drengenavnIndex++;
    return result;
  }

  private String getRandomPigenavn() {
    if (pigenavnIndex >= muligePigenavne.size()) {
      pigenavnIndex = 0;
    }
    String result = muligePigenavne.get(pigenavnIndex);
    pigenavnIndex++;
    return result;
  }

  private String getRandomEfternavn() {
    if (efternavnIndex >= muligeEfternavne.size()) {
      efternavnIndex = 0;
    }
    String result = muligeEfternavne.get(efternavnIndex);
    efternavnIndex++;
    return result;
  }

  private String getRandomInstitutionsnummer() {
    if (institutionsnummerIndex >= muligeInstitutionsnumre.size()) {
      institutionsnummerIndex = 0;
    }
    String result = muligeInstitutionsnumre.get(institutionsnummerIndex);
    institutionsnummerIndex++;
    return result;
  }

  private Klassetrin getRandomKlassetrin() {
    Collections.shuffle(muligeKlassetrin);
    return muligeKlassetrin.get(0);
  }
}
