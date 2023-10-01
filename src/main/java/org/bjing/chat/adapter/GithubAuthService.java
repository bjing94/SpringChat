package org.bjing.chat.adapter;

import net.minidev.json.JSONObject;
import org.bjing.chat.adapter.dto.GithubAccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

@Service
public class GithubAuthService {

    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String clientId;

    GithubAuthService() {
        this.restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("https://github.com"));
    }


    public String getAccessToken(String code) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject body = new JSONObject();
            body.put("code", code);
            body.put("client_secret", this.clientSecret);
            body.put("client_id", this.clientId);
            HttpEntity<String> entity = new HttpEntity<>(body.toJSONString(), headers);

            GithubAccessTokenResponse response = this.restTemplate.postForObject("/login/oauth/access_token", entity, GithubAccessTokenResponse.class);
            return response.getAccessToken();
        } catch (NullPointerException e) {
            return "";
        }
    }
}
