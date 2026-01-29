package dev.lkdcode.domain.service

import dev.lkdcode.domain.repository.PostRepository
import dev.lkdcode.domain.value.Post
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PostService(
    private val postRepository: PostRepository,
) {

    @Cacheable(
        cacheNames = ["post"],
        key = "#postId",
        condition = "#postId > 0",
        unless = "#result == null"
    )
    fun fetch(postId: Long): Mono<Post> {
        println("PostService.fetch POST_ID: $postId")

        return postRepository.findByPostId(postId)
    }

    @CachePut(
        cacheNames = ["post"],
        key = "#postId"
    )
    fun create(postId: Long): Mono<Post> {
        println("PostService.create POST_ID: $postId")

        return postRepository.save(postId)
    }

    @CachePut(
        cacheNames = ["post"],
        key = "#postId"
    )
    fun update(postId: Long): Mono<Post> {
        println("PostService.update POST_ID: $postId")

        return postRepository.updateByPostId(postId)
    }

    @CacheEvict(
        cacheNames = ["post"],
        key = "#postId"
    )
    fun delete(postId: Long): Mono<Post> {
        println("PostService.delete POST_ID: $postId")

        return postRepository.deleteByPostId(postId)
    }
}