package dev.lkdcode.domain.repository

import dev.lkdcode.domain.value.Post
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.time.Duration

@Repository
class PostRepository {
    fun findByPostId(postId: Long): Mono<Post> {
        println("PostRepository.findByPostId POST_ID: $postId")

        return Mono
            .just(Post(postId, "Post Title $postId"))
            .delayElement(Duration.ofSeconds(3))
    }

    fun save(postId: Long): Mono<Post> {
        println("PostRepository.save POST_ID: $postId")

        return Mono.just(Post(postId, "Post Title $postId"))
    }

    fun updateByPostId(postId: Long): Mono<Post> {
        println("PostRepository.updateByPostId POST_ID: $postId")

        return Mono.just(Post(postId, "Updated Post Title $postId"))
    }

    fun deleteByPostId(postId: Long): Mono<Post> {
        println("PostRepository.deleteByPostId POST_ID: $postId")

        return Mono.just(Post(postId, "Post Title $postId"))
    }
}