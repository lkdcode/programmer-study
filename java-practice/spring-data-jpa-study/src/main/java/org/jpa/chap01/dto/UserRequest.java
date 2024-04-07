package org.jpa.chap01.dto;

import lombok.Builder;
import org.jpa.chap01.entity.UserEntity;

import java.util.List;

public sealed interface UserRequest permits
        UserRequest.CreateDTO, UserRequest.UpdateDTO,
        UserRequest.DeleteDTO, UserRequest.GetDTO,
        UserRequest.GetListDTO {
    @Builder
    record CreateDTO(
            Long age,
            String name
    ) implements UserRequest {
        public UserEntity toEntity() {
            return UserEntity.builder()
                    .age(age)
                    .name(name)
                    .build();
        }
    }

    @Builder
    record UpdateDTO(
            Long userId,
            Long age,
            String name
    ) implements UserRequest {
    }

    @Builder
    record DeleteDTO(
            Long userId
    ) implements UserRequest {
    }

    @Builder
    record GetDTO(
            Long userId,
            Long age,
            String name
    ) implements UserRequest {
    }

    @Builder
    record GetListDTO(
            List<GetDTO> list
    ) implements UserRequest {
    }
}
