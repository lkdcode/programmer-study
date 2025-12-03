package cool.hdd.application.command.service

import cool.hdd.application.command.dto.CreatePostDTO
import cool.hdd.application.command.ports.input.CreatePostUsecase
import cool.hdd.application.command.ports.out.CommandPostPort
import cool.hdd.domain.post.aggregate.PostAggregate
import cool.hdd.domain.post.service.PostCreationService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class CreatePostService(
    private val commandPostPort: CommandPostPort,
) : CreatePostUsecase {

    override fun execute(dto: CreatePostDTO): PostAggregate =
        PostCreationService
            .execute(dto.convertPost()) {
                validateStatus()
            }
            .saved()

}