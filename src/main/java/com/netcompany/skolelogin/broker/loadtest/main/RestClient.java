package com.netcompany.skolelogin.broker.loadtest.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcompany.skolelogin.broker.loadtest.model.Aktoer;
import com.netcompany.skolelogin.broker.loadtest.model.Medarbejder;
import com.netcompany.skolelogin.broker.loadtest.util.ErstatningspersonnummerGenerator;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RestClient {
  public static void main(String[] args) {
    String urlBrokerTest = "https://nginx-broker.skolelogin.127.0.0.1.nip.io/auth/realms/broker/aktoer-service/aktoer";
    String urlIdpTest = "https://nginx-unilogin.skolelogin.127.0.0.1.nip.io/auth/realms/idp/aktoerrestservice/aktoer";

    String urlBrokerLT = "https://lt-broker.unilogin.dk/auth/realms/broker/aktoer-service/aktoer";
    String urlIdpLT = "https://lt-idp.unilogin.dk/auth/realms/idp/aktoerrestservice/aktoer";

    try {
      Medarbejder medarbejder = createMedarbejder();
      List<Aktoer> aktører = new ArrayList<>();
      aktører.add(medarbejder);

      ObjectMapper objectMapper = new ObjectMapper();

      DefaultHttpClient httpClient = new DefaultHttpClient();

      HttpPost postrequest = new HttpPost(urlIdpTest);

      String json = objectMapper.writeValueAsString(aktører);
      StringEntity input = new StringEntity(json, "UTF-8");
      input.setContentType("application/json");
      input.setContentEncoding("UTF-8");
      postrequest.setEntity(input);

      HttpResponse response = httpClient.execute(postrequest);
      System.out.println("HTTP responsecode: " + response.getStatusLine().getStatusCode());

      httpClient.getConnectionManager().shutdown();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Medarbejder createMedarbejder() {
    Medarbejder medarbejder = new Medarbejder();
    medarbejder.setCpr("111063001");
    medarbejder.setAktoerUuid(UUID.randomUUID());
    medarbejder.setEfternavn("ÆØÅ");
    medarbejder.setFornavn("æøå");
    medarbejder.setLocalPersonId("pdyb1");
    medarbejder.setUniId("pdyb1");

    medarbejder.setInstitutionId("100001");
    medarbejder.setInitialPassword("1234");
    return medarbejder;
  }
}
