package run.moku.modules.users.adapter.infrastructure.jpa.query

import org.springframework.data.repository.Repository
import run.moku.modules.users.adapter.infrastructure.jpa.entity.UserJpaEntity

interface UserQueryJpaRepository : Repository<UserJpaEntity, Long> {
    fun findById(id: Long): UserJpaEntity?
    fun findAll(): List<UserJpaEntity>
}