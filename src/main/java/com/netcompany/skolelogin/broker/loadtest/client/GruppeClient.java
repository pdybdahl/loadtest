package com.netcompany.skolelogin.broker.loadtest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcompany.skolelogin.broker.loadtest.model.Gruppe;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.List;

public class GruppeClient {
  private static final String BROKER_URL_TEST = "https://nginx-broker.skolelogin.127.0.0.1.nip.io/auth/realms/broker/aktoer-service/gruppe";
  private static final String IDP_URL_TEST = "https://nginx-unilogin.skolelogin.127.0.0.1.nip.io/auth/realms/idp/aktoerrestservice/gruppe";

  private static final String BROKER_URL_LT = "https://lt-broker.unilogin.dk/auth/realms/broker/aktoer-service/gruppe";
  private static final String IDP_URL_LT = "https://lt-idp.unilogin.dk/auth/realms/idp/aktoerrestservice/gruppe";

  public HttpResponse sendToIdp(List<Gruppe> grupper) throws IOException {
    return send(IDP_URL_TEST, grupper);
  }

  public HttpResponse sendToBroker(List<Gruppe> grupper) throws IOException {
    return send(BROKER_URL_TEST, grupper);
  }

  public HttpResponse sendToIdpLT(List<Gruppe> grupper) throws IOException {
    return send(IDP_URL_LT, grupper);
  }

  public HttpResponse sendToBrokerLT(List<Gruppe> grupper) throws IOException {
    return send(BROKER_URL_LT, grupper);
  }

  private HttpResponse send(String url, List<Gruppe> grupper) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    DefaultHttpClient httpClient = new DefaultHttpClient();

    HttpPost postrequest = new HttpPost(url);

    String json = objectMapper.writeValueAsString(grupper);
    StringEntity input = new StringEntity(json, "UTF-8");
    input.setContentType("application/json");
    input.setContentEncoding("UTF-8");
    postrequest.setEntity(input);

    HttpResponse response = httpClient.execute(postrequest);

    httpClient.getConnectionManager().shutdown();

    return response;
  }
}
