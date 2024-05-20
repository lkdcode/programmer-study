package org.example.domain.kakao.adapter.input.rest.dto;

public record KakaoResponseDTO(
        String access_token,
        String token_type,
        String refresh_token,
        String id_token,
        String scope,
        Long expires_in,
        Long refresh_token_expires_in
) {
}
