package org.bjing.chat.adapter;

import net.minidev.json.JSONObject;
import org.bjing.chat.adapter.dto.GithubAccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Collections;

@Service
public class GithubApiService {

    private final RestTemplate restTemplate;


    GithubApiService() {
        this.restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("https://api.github.com"));
    }


    public String getProfile(String code) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            JSONObject body = new JSONObject();
//            body.put("code", code);
//            body.put("client_secret", this.clientSecret);
//            body.put("client_id", this.clientId);
//            HttpEntity<String> entity = new HttpEntity<>(body.toJSONString(), headers);
//
//            GithubAccessTokenResponse response = this.restTemplate.postForObject("/login/oauth/access_token", entity, GithubAccessTokenResponse.class);
//            return response.getAccessToken();
//        } catch (NullPointerException e) {
//            return "";
//        }
        return "";
    }
}
