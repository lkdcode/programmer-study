package org.game.domain.naver.adapter.input.rest.dto;

public record NaverResponseDTO(
        String access_token,
        String refresh_token,
        String token_type,
        String expires_in
) {
}
