package com.sb.adapter.plan.output.command

import com.sb.adapter.plan.output.infrastructure.r2dbc.entity.CreditWalletR2dbcEntity
import com.sb.adapter.plan.output.infrastructure.r2dbc.repository.CreditWalletR2dbcRepository
import com.sb.application.credit.ports.output.command.CreditWalletCommandPort
import com.sb.domain.credit.aggregate.CreditWalletAggregate
import com.sb.domain.credit.entity.CreditWallet
import com.sb.domain.credit.model.NewCreditWallet
import com.sb.domain.credit.value.Credit
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component

@Component
class CreditWalletCommandAdapter(
    private val repository: CreditWalletR2dbcRepository,
) : CreditWalletCommandPort {

    override suspend fun update(aggregate: CreditWalletAggregate): CreditWalletAggregate {
        val w = aggregate.snapshot
        val entity = CreditWalletR2dbcEntity(
            id = w.id.value,
            userId = w.userId,
            balance = w.balance.value,
        )
        repository.save(entity).awaitSingle()
        return aggregate
    }

    override suspend fun create(newWallet: NewCreditWallet): CreditWalletAggregate {
        val entity = CreditWalletR2dbcEntity(
            userId = newWallet.userId,
            balance = newWallet.balance.value,
        )

        val saved = repository.save(entity).awaitSingle()

        return CreditWalletAggregate.Companion.restore(
            CreditWallet(
                id = CreditWallet.WalletId(saved.id!!),
                userId = saved.userId,
                balance = Credit.of(saved.balance),
            )
        )
    }

    override suspend fun save(aggregate: CreditWalletAggregate): CreditWalletAggregate {
        val wallet = aggregate.snapshot
        val entity = CreditWalletR2dbcEntity(
            id = wallet.id.value,
            userId = wallet.userId,
            balance = wallet.balance.value,
        )

        repository.save(entity).awaitSingle()

        return aggregate
    }
}