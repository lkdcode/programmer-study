package org.example.kakao.adapter.input.rest.dto;

public record KakaoUserResponseDTO(
        String id,
        String connected_at,
        KakaoAccountDTO kakao_account
) {
}