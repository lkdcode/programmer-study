package org.example.google.adapter.input.rest.dto;

public record GoogleTokenDTO(
        String access_token,
        Long expires_in,
        String scope,
        String token_type,
        String id_token
) {
}
