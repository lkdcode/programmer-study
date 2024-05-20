package org.example.domain.naver.adapter.input.rest.dto;

public record NaverInfoResponseDTO(
        String resultcode,
        String message,
        NaverInfoDetailsDTO response
) {
}
