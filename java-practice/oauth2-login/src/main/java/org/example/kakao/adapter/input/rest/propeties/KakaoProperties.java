package org.example.kakao.adapter.input.rest.propeties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KakaoProperties {
    @Value("${kakao.oauth_uri}")
    private String oauthUri;
    @Value("${kakao.token.grant_type}")
    private String grantType;
    @Value("${kakao.token.client_id}")
    private String clientId;
    @Value("${kakao.token.client_secret}")
    private String clientSecret;
    @Value("${kakao.uri.redirect_uri}")
    private String redirectUri;
}
