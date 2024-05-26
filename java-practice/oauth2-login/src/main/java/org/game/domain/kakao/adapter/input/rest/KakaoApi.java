//package org.example.domain.kakao.adapter.input.rest;
//
//import lombok.RequiredArgsConstructor;
//import org.example.domain.kakao.adapter.input.rest.dto.KakaoResponseDTO;
//import org.example.domain.kakao.adapter.input.rest.dto.KakaoUserResponseDTO;
//import org.example.domain.kakao.adapter.input.rest.propeties.KakaoProperties;
//import org.springframework.http.MediaType;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestClient;
//
//import java.util.Objects;
//
//@RestController
//@RequestMapping("/kakao")
//@RequiredArgsConstructor
//public class KakaoApi {
//    private final KakaoProperties kakaoProperties;
//
//    @GetMapping("/callback")
//    public void getCallback(
//            @RequestParam(name = "code") final String code
//    ) {
//        final var uri = kakaoProperties.getOauthUri();
//        final var grantType = kakaoProperties.getGrantType();
//        final var clientId = kakaoProperties.getClientId();
//        final var redirectUri = kakaoProperties.getRedirectUri();
//        final var clientSecret = kakaoProperties.getClientSecret();
//
//        final var restClient = RestClient.builder()
//                .baseUrl(uri)
//                .build();
//
//        final var map = new LinkedMultiValueMap<String, String>();
//        map.add("grant_type", grantType);
//        map.add("client_id", clientId);
//        map.add("redirect_uri", redirectUri);
//        map.add("code", code);
//        map.add("client_secret", clientSecret);
//
//        final var body = restClient.post()
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .body(map)
//                .retrieve()
//                .body(KakaoResponseDTO.class);
//
//        final var token = Objects.requireNonNull(body).access_token();
//        getKakaoUserId(token);
//    }
//
//    private void getKakaoUserId(String accessToken) {
//        final var restClient = RestClient.builder()
//                .baseUrl(kakaoProperties.getUserUri())
//                .build();
//
//        final var body = restClient.post()
//                .header("Authorization", "Bearer " + accessToken)
//                .retrieve()
//                .body(KakaoUserResponseDTO.class);
//
//        System.out.println(body);
//    }
//}
