package org.bjing.chat.adapter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GithubAccessTokenResponse {
    String access_token;
    String scope;
    String token_type;

    public String getAccessToken() {
        return this.access_token;
    }
}
