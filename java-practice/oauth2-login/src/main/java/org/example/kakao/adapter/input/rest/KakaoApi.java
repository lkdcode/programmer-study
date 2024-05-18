package org.example.kakao.adapter.input.rest;

import lombok.RequiredArgsConstructor;
import org.example.kakao.adapter.input.rest.dto.KakaoResponseDTO;
import org.example.kakao.adapter.input.rest.propeties.KakaoProperties;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoApi {

    private final KakaoProperties kakaoProperties;

    @GetMapping("/callback")
    public void getCallback(
            @RequestParam(name = "code") final String code
    ) {
        final String uri = kakaoProperties.getOauthUri();
        final String grantType = kakaoProperties.getGrantType();
        final String clientId = kakaoProperties.getClientId();
        final String redirectUri = kakaoProperties.getRedirectUri();
        final String clientSecret = kakaoProperties.getClientSecret();

        final RestClient restClient = RestClient.builder()
                .baseUrl(uri)
                .build();

        final LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", grantType);
        map.add("client_id", clientId);
        map.add("redirect_uri", redirectUri);
        map.add("code", code);
        map.add("client_secret", clientSecret);

        var body = restClient.post()
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(map)
                .retrieve()
                .body(KakaoResponseDTO.class);

        System.out.println(body);
    }
}