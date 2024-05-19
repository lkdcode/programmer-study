package org.example.naver.adapter.input.rest.dto;

public record NaverInfoResponseDTO(
        String resultcode,
        String message,
        NaverInfoDetailsDTO response
) {
}
