package org.game.core.security.service;

import org.game.core.security.dto.GoogleResponse;
import org.game.core.security.dto.KakaoResponse;
import org.game.core.security.dto.NaverResponse;
import org.game.core.security.dto.OAuth2Response;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class MyOAuth2UserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final var user = super.loadUser(userRequest);

        final var registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(user.getAttributes());
        }

        if (registrationId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(user.getAttributes());
        }

        if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(user.getAttributes());
        }

        return new MyOauth2User(oAuth2Response, "ROLE_USER");
    }
}