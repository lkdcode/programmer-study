package org.jpa.chap01.dto;

import org.jpa.chap01.entity.PostEntity;

import java.util.List;

public sealed interface PostRequest permits
        PostRequest.Create, PostRequest.Get,
        PostRequest.GetList, PostRequest.Delete,
        PostRequest.Update {
    record Create(
            String title,
            String content
    ) implements PostRequest {
        public PostEntity toEntity() {
            return PostEntity.builder()
                    .title(title)
                    .content(content)
                    .build();
        }
    }

    record Get(
            Long id,
            Long userId,
            String title,
            String content
    ) implements PostRequest {
    }

    record GetList(
            List<Get> list
    ) implements PostRequest {
    }

    record Update(
            Long postId,
            String title,
            String content
    ) implements PostRequest {
    }

    record Delete(
            Long postId
    ) implements PostRequest {
    }
}
