package com.sb.adapter.plan.output.command

import com.sb.adapter.plan.output.infrastructure.r2dbc.entity.CreditTransactionR2dbcEntity
import com.sb.adapter.plan.output.infrastructure.r2dbc.repository.CreditTransactionR2dbcRepository
import com.sb.application.credit.ports.output.command.CreditTransactionCommandPort
import com.sb.domain.credit.aggregate.CreditTransactionAggregate
import com.sb.domain.credit.entity.CreditTransaction
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import java.util.*

@Component
class CreditTransactionCommandAdapter(
    private val repository: CreditTransactionR2dbcRepository,
) : CreditTransactionCommandPort {

    override suspend fun append(tx: CreditTransaction): CreditTransaction {
        val entity = CreditTransactionR2dbcEntity(
            txId = UUID.fromString(tx.id.value),
            userId = tx.userId,
            type = tx.type.name,
            amount = tx.amount.value,
            reason = tx.reason.name,
        )

        repository
            .save(entity)
            .awaitSingle()

        return tx
    }

    override suspend fun append(tx: CreditTransactionAggregate): CreditTransactionAggregate {
        TODO("Not yet implemented")
    }
}