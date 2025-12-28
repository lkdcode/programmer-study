package com.sb.application.like.service.command

import com.sb.application.like.dto.LikeCalligraphyCommand
import com.sb.application.like.dto.UnlikeCalligraphyCommand
import com.sb.application.like.ports.input.command.LikeCalligraphyCommandUsecase
import com.sb.application.like.ports.output.command.CalligraphyLikeCommandPort
import com.sb.domain.like.entity.CalligraphyLike
import com.sb.domain.like.policy.LikePolicy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CalligraphyLikeApplicationService(
    private val commandPort: CalligraphyLikeCommandPort,
    private val policy: LikePolicy
) : LikeCalligraphyCommandUsecase {

    override suspend fun like(command: LikeCalligraphyCommand): CalligraphyLike.CalligraphyLikeId {
        val entity = command.convert(policy)
        val saved = commandPort.save(entity)

        return saved.snapshot.id
    }

    override suspend fun unlike(command: UnlikeCalligraphyCommand) {
        policy.requireLiked(command.likeId, command.userId)
        commandPort.delete(command)
    }
}