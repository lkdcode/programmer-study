package run.moku.modules.users.adapter.infrastructure.jpa.command

import org.springframework.data.repository.Repository
import run.moku.modules.users.adapter.infrastructure.jpa.entity.UserJpaEntity

interface UserCommandJpaRepository : Repository<UserJpaEntity, Long> {
    fun save(entity: UserJpaEntity)
}