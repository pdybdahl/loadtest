package com.netcompany.skolelogin.broker.loadtest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcompany.skolelogin.broker.loadtest.model.Aktoer;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.List;

public class AktørClient {
  private static final String BROKER_URL_TEST = "https://nginx-broker.skolelogin.127.0.0.1.nip.io/auth/realms/broker/aktoer-service/aktoer";
  private static final String IDP_URL_TEST = "https://nginx-unilogin.skolelogin.127.0.0.1.nip.io/auth/realms/idp/aktoerrestservice/aktoer";

  private static final String BROKER_URL_LT = "https://lt-broker.unilogin.dk/auth/realms/broker/aktoer-service/aktoer";
  private static final String IDP_URL_LT = "https://lt-idp.unilogin.dk/auth/realms/idp/aktoerrestservice/aktoer";

  public HttpResponse sendToIdp(List<Aktoer> aktører) throws IOException {
    return send(IDP_URL_TEST, aktører);
  }

  public HttpResponse sendToBroker(List<Aktoer> aktører) throws IOException {
    return send(BROKER_URL_TEST, aktører);
  }

  public HttpResponse sendToIdpLT(List<Aktoer> aktører) throws IOException {
    return send(IDP_URL_LT, aktører);
  }

  public HttpResponse sendToBrokerLT(List<Aktoer> aktører) throws IOException {
    return send(BROKER_URL_LT, aktører);
  }

  private HttpResponse send(String url, List<Aktoer> aktører) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    DefaultHttpClient httpClient = new DefaultHttpClient();

    HttpPost postrequest = new HttpPost(url);

    String json = objectMapper.writeValueAsString(aktører);
    StringEntity input = new StringEntity(json, "UTF-8");
    input.setContentType("application/json");
    input.setContentEncoding("UTF-8");
    postrequest.setEntity(input);

    HttpResponse response = httpClient.execute(postrequest);

    httpClient.getConnectionManager().shutdown();

    return response;
  }
}
