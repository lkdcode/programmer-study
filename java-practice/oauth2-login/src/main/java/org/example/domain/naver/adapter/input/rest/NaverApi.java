package org.example.domain.naver.adapter.input.rest;

import lombok.RequiredArgsConstructor;
import org.example.domain.naver.adapter.input.rest.dto.NaverInfoResponseDTO;
import org.example.domain.naver.adapter.input.rest.dto.NaverResponseDTO;
import org.example.domain.naver.adapter.input.rest.propeties.NaverProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@RestController
@RequestMapping("/naver")
@RequiredArgsConstructor
public class NaverApi {
    private final NaverProperties naverProperties;

    @GetMapping("/callback")
    public void getCallback(
            @RequestParam(name = "code") final String code
    ) {
        getToken(code);
    }

    private void getToken(final String code) {
        final var restClient = RestClient.builder()
                .baseUrl(naverProperties.getOauthUri())
                .build();

        final var body = restClient.get()
                .uri(uri -> uri
                        .queryParam("grant_type", naverProperties.getGrantType())
                        .queryParam("client_id", naverProperties.getClientId())
                        .queryParam("client_secret", naverProperties.getClientSecret())
                        .queryParam("code", code)
                        .queryParam("state", naverProperties.getState())
                        .build())
                .retrieve()
                .body(NaverResponseDTO.class);

        getInfo(Objects.requireNonNull(body).access_token());
    }

    private void getInfo(final String token) {
        final var restClient = RestClient.builder()
                .baseUrl(naverProperties.getUserInfoUri())
                .build();

        var body = restClient.get()
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(NaverInfoResponseDTO.class);

        System.out.println(body);
    }
}