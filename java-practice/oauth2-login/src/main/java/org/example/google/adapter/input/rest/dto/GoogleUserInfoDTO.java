package org.example.google.adapter.input.rest.dto;

public record GoogleUserInfoDTO(
        String id,
        String email,
        Boolean verified_email,
        String name,
        String given_name,
        String picture,
        String locale
) {
}
