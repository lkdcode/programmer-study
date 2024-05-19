package org.example.google.adapter.input.rest;

import lombok.RequiredArgsConstructor;
import org.example.google.adapter.input.rest.dto.GoogleTokenDTO;
import org.example.google.adapter.input.rest.dto.GoogleUserInfoDTO;
import org.example.google.adapter.input.rest.properties.GoogleProperties;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@RestController
@RequestMapping("/google")
@RequiredArgsConstructor
public class GoogleApi {
    private final GoogleProperties googleProperties;

    @GetMapping("/callback")
    public void getCallback(
            @RequestParam(name = "code") String code
    ) {
        getToken(code);
    }

    private void getToken(final String code) {
        final var restClient = RestClient.builder()
                .baseUrl(googleProperties.getOauthUri())
                .build();

        final var map = new LinkedMultiValueMap<String, String>();
        map.add("grant_type", googleProperties.getGrantType());
        map.add("client_id", googleProperties.getClientId());
        map.add("redirect_uri", googleProperties.getRedirectUri());
        map.add("code", code);
        map.add("client_secret", googleProperties.getClientSecret());

        final var token = restClient.post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(map)
                .retrieve()
                .body(GoogleTokenDTO.class);

        getUserInfo(Objects.requireNonNull(token).access_token());
    }

    private void getUserInfo(final String accessToken) {
        final var restClient = RestClient.builder()
                .baseUrl(googleProperties.getUserInfoUri())
                .build();

        final var body = restClient.get()
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .body(GoogleUserInfoDTO.class);

        System.out.println(body);
    }
}