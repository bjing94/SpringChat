package org.bjing.chat.adapter;

import net.minidev.json.JSONObject;
import org.bjing.chat.adapter.dto.GithubAccessTokenResponse;
import org.bjing.chat.adapter.dto.GithubUserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.Collections;
import java.util.Optional;

@Service
public class GithubApiService {

    private final RestTemplate restTemplate;


    GithubApiService() {
        this.restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("https://api.github.com"));
    }


    public Optional<GithubUserResponse> getProfile(String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(accessToken);
            System.out.println(headers);
            HttpEntity<String> request = new HttpEntity<String>(headers);
            ResponseEntity<GithubUserResponse> data = this.restTemplate.exchange("/user", HttpMethod.GET, request, GithubUserResponse.class);
            return Optional.ofNullable(data.getBody());
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }
}
