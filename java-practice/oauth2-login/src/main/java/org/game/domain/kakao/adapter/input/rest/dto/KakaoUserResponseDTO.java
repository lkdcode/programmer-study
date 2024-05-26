package org.game.domain.kakao.adapter.input.rest.dto;

public record KakaoUserResponseDTO(
        String id,
        String connected_at,
        KakaoAccountDTO kakao_account
) {
}
