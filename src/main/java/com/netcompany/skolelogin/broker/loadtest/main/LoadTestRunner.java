package com.netcompany.skolelogin.broker.loadtest.main;

import com.netcompany.skolelogin.broker.loadtest.client.AktørClient;
import com.netcompany.skolelogin.broker.loadtest.client.GruppeClient;
import com.netcompany.skolelogin.broker.loadtest.model.Aktoer;
import com.netcompany.skolelogin.broker.loadtest.model.Gruppe;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LoadTestRunner {

  public static void main(String[] args) {
    Long start = System.currentTimeMillis();

    DataFactory dataFactory = new DataFactory();

    try {
      dataFactory.produceLoadData(10000);
      loadGrupper(dataFactory.getGrupper());
      loadAktører(dataFactory.getAktører());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      System.out.println("Took: " + (System.currentTimeMillis() - start) + " ms");
    }
  }

  private static void loadGrupper(List<Gruppe> grupper) throws IOException {
    int chunkSize = 500;
    int chunkNumber = 1;
    int antal = grupper.size();
    long start;

    while (chunkNumber * chunkSize < antal) {
      loadGruppeChunk(grupper, (chunkNumber - 1) * chunkSize, chunkNumber * chunkSize);
      chunkNumber++;
    }

    loadGruppeChunk(grupper, (chunkNumber - 1) * chunkSize, antal - 1);
  }

  private static void loadGruppeChunk(List<Gruppe> grupper, int startIndex, int slutIndex) throws IOException {
    GruppeClient gruppeClient = new GruppeClient();
    long start;

    log("Sender " + startIndex + " - " + slutIndex + " grupper");

    start = System.currentTimeMillis();
    gruppeClient.sendToBrokerLT(grupper.subList(startIndex, slutIndex));
    log("broker took: " + (System.currentTimeMillis() - start) + " ms");

    start = System.currentTimeMillis();
    gruppeClient.sendToIdpLT(grupper.subList(startIndex, slutIndex));
    log("idp took: " + (System.currentTimeMillis() - start) + " ms");
  }

  private static void loadAktører(List<Aktoer> aktører) throws IOException {
    int chunkSize = 100;
    int chunkNumber = 1;
    int antal = aktører.size();
    long start;

    while (chunkNumber * chunkSize < antal) {
      loadAktørChunk(aktører, (chunkNumber - 1) * chunkSize, chunkNumber * chunkSize);
      chunkNumber++;
    }

    loadAktørChunk(aktører, (chunkNumber - 1) * chunkSize, antal - 1);
  }

  private static void loadAktørChunk(List<Aktoer> aktører, int startIndex, int slutIndex) throws IOException {
    AktørClient aktørClient = new AktørClient();
    long start;

    log("Sender " + startIndex + " - " + slutIndex + " aktører");

    start = System.currentTimeMillis();
    aktørClient.sendToBrokerLT(aktører.subList(startIndex, slutIndex));
    log("broker took: " + (System.currentTimeMillis() - start) + " ms");

    start = System.currentTimeMillis();
    aktørClient.sendToIdpLT(aktører.subList(startIndex, slutIndex));
    log("idp took: " + (System.currentTimeMillis() - start) + " ms");
  }

  private static void log(String msg) {
    System.out.println(msg);
  }
}
