package org.game.domain.naver.adapter.input.rest.dto;

public record NaverInfoDetailsDTO(
        String id,
        String gender,
        String mobile,
        String mobile_e164,
        String name,
        String birthday,
        String birthyear
) {
}
