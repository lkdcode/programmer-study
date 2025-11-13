package dev.lkdcode.application.service.command

import dev.lkdcode.application.ports.input.command.CreatePostDTO
import dev.lkdcode.application.ports.input.command.CreatePostUsecase
import dev.lkdcode.application.ports.output.command.PostCommandPort
import dev.lkdcode.application.ports.output.query.UserQueryPort
import dev.lkdcode.domain.aggregate.PostAggregate
import dev.lkdcode.domain.entity.Post
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class CreatePostInput(
    private val userQueryPor: UserQueryPort,
    private val postCommandPort: PostCommandPort
) : CreatePostUsecase {

    override fun create(dto: CreatePostDTO): Post {
        val author = userQueryPor.load(dto.authorId)
        val aggregate = PostAggregate.create(
            dto.titleValue,
            dto.contentValue,
            author
        )

        return postCommandPort
            .save(aggregate)
            .getPost
    }
}