package org.jpa.chap01.api;

import lombok.RequiredArgsConstructor;
import org.jpa.chap01.dto.PostRequest;
import org.jpa.chap01.entity.PostEntity;
import org.jpa.chap01.repository.PostJpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostApi {
    private final PostJpaRepository postJpaRepository;

    @GetMapping
    public List<PostEntity> getPostList() {
        return postJpaRepository.findAll();
    }

    @PostMapping
    public PostEntity createPost(
            @RequestBody final PostRequest.Create request
    ) {
        return postJpaRepository.save(request.toEntity());
    }

    @DeleteMapping
    public String deletePost(
            @RequestBody final PostRequest.Delete request
    ) {
        postJpaRepository.deleteById(request.postId());
        return "deleted";
    }

    @PutMapping
    public PostEntity updatePost(
            @RequestBody final PostRequest.Update request
    ) {
        Optional<PostEntity> byId = postJpaRepository.findById(request.postId());

        if (byId.isPresent()) {
            PostEntity postEntity = byId.get();
            postEntity.updateTitle(request.title());
            postEntity.updateContent(request.content());
            return postEntity;
        }

        return null;
    }
}