package org.game.domain.kakao.adapter.input.rest.dto;

public record KakaoRequestDTO(

        String tokenType,
        String accessToken,
        Long expiresIn,
        String refreshToken,
        Long refreshTokenExpiresIn,
        String scope
) {
}
