package com.sb.adapter.user.output.infrastructure.r2dbc.repository

import com.sb.adapter.user.output.infrastructure.r2dbc.entity.UserR2dbcEntity
import com.sb.domain.user.entity.User
import com.sb.domain.user.value.Email
import com.sb.framework.api.ApiException
import com.sb.framework.api.ApiResponseCode
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono

interface UserR2dbcRepository : R2dbcRepository<UserR2dbcEntity, Long> {
    fun findByIdAndIsDeletedFalse(id: Long): Mono<UserR2dbcEntity>
    fun findByEmail(email: String): Mono<UserR2dbcEntity>
    fun findByEmailAndIsDeletedFalse(email: String): Mono<UserR2dbcEntity>

    fun findByProviderAndProviderUserId(provider: String, providerId: String): Mono<UserR2dbcEntity>

    @Query(
        """
        SELECT EXISTS (
            SELECT 1
              FROM mst_user
             WHERE email = :email
        )
    """
    )
    fun existsByEmail(email: String): Mono<Boolean>
}

fun UserR2dbcRepository.loadById(id: Long): Mono<UserR2dbcEntity> =
    findByIdAndIsDeletedFalse(id).switchIfEmpty(Mono.error(ApiException(ApiResponseCode.USER_NOT_FOUND)))

fun UserR2dbcRepository.loadById(id: User.UserId): Mono<UserR2dbcEntity> =
    findByIdAndIsDeletedFalse(id.value).switchIfEmpty(Mono.error(ApiException(ApiResponseCode.USER_NOT_FOUND)))

fun UserR2dbcRepository.loadByEmail(email: String): Mono<UserR2dbcEntity> =
    findByEmailAndIsDeletedFalse(email).switchIfEmpty(Mono.error(ApiException(ApiResponseCode.USER_NOT_FOUND)))

fun UserR2dbcRepository.loadByEmail(email: Email): Mono<UserR2dbcEntity> =
    findByEmailAndIsDeletedFalse(email.value).switchIfEmpty(Mono.error(ApiException(ApiResponseCode.USER_NOT_FOUND)))