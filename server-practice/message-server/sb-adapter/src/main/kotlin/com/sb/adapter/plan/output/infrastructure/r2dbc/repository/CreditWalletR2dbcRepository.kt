package com.sb.adapter.plan.output.infrastructure.r2dbc.repository

import com.sb.adapter.plan.output.infrastructure.r2dbc.entity.CreditWalletR2dbcEntity
import com.sb.domain.credit.entity.CreditWallet
import com.sb.framework.api.ApiException
import com.sb.framework.api.ApiResponseCode
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono

interface CreditWalletR2dbcRepository : R2dbcRepository<CreditWalletR2dbcEntity, Long> {
    fun findByUserId(userId: Long): Mono<CreditWalletR2dbcEntity>
    fun existsByUserId(userId: Long): Mono<Boolean>
}

fun CreditWalletR2dbcRepository.loadById(walletId: CreditWallet.WalletId): Mono<CreditWalletR2dbcEntity> =
    findById(walletId.value).switchIfEmpty(Mono.error(ApiException(ApiResponseCode.WALLET_NOT_FOUND)))