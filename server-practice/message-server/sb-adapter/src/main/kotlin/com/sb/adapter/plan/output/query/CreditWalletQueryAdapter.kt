package com.sb.adapter.plan.output.query

import com.sb.adapter.plan.output.infrastructure.r2dbc.repository.CreditWalletR2dbcRepository
import com.sb.adapter.plan.output.infrastructure.r2dbc.repository.loadById
import com.sb.application.credit.ports.output.query.CreditWalletQueryPort
import com.sb.domain.credit.aggregate.CreditWalletAggregate
import com.sb.domain.credit.entity.CreditWallet
import com.sb.domain.credit.value.Credit
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component


@Component
class CreditWalletQueryAdapter(
    private val repository: CreditWalletR2dbcRepository,
) : CreditWalletQueryPort {

    override suspend fun loadByUserId(userId: Long): CreditWalletAggregate =
        repository
            .findByUserId(userId)
            .map { entity ->
                CreditWalletAggregate.restore(
                    CreditWallet(
                        id = CreditWallet.WalletId(requireNotNull(entity.id)),
                        userId = entity.userId,
                        balance = Credit.of(entity.balance),
                    )
                )
            }
            .awaitSingle()

    override suspend fun loadByWalletId(walletId: CreditWallet.WalletId): CreditWalletAggregate =
        repository
            .loadById(walletId)
            .map { entity ->
                CreditWalletAggregate.restore(
                    CreditWallet(
                        id = CreditWallet.WalletId(requireNotNull(entity.id)),
                        userId = entity.userId,
                        balance = Credit.of(entity.balance),
                    )
                )
            }
            .awaitSingle()
}