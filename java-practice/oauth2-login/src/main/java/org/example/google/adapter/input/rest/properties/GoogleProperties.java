package org.example.google.adapter.input.rest.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GoogleProperties {
    @Value("${google.client_id}")
    private String clientId;

    @Value("${google.client_secret}")
    private String clientSecret;
    @Value("${google.grant_type}")
    private String grantType;
    @Value("${google.scope}")
    private String scope;

    @Value("${google.uri.redirect_uri}")
    private String redirectUri;

    @Value("${google.uri.oauth_uri}")
    private String oauthUri;

    @Value("${google.uri.user_info_uri}")
    private String userInfoUri;

}
