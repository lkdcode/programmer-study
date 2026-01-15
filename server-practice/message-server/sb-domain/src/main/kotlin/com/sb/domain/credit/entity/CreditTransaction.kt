package com.sb.domain.credit.entity

import com.sb.domain.credit.value.Credit
import com.sb.domain.credit.value.TransactionReason
import com.sb.domain.credit.value.TransactionType
import kotlin.random.Random

data class CreditTransaction(
    val id: TransactionId,
    val userId: Long,
    val amount: Credit,

    val type: TransactionType,
    val reason: TransactionReason,
) {

    @JvmInline
    value class TransactionId private constructor(val value: String) {
        companion object {
            fun of(value: String): TransactionId = TransactionId(value)

            fun generate(): TransactionId {
                return TransactionId(generateUuidV7(Random.Default))
            }

            private fun generateUuidV7(random: Random): String {
                val timestamp = System.currentTimeMillis()
                val timeHigh = (timestamp shr 16).toInt()
                val timeMid = (timestamp and 0xFFFF).toInt()
                val versionAndRandA = 0x7000 or random.nextInt(0x1000)
                val variantAndRandB = 0x8000 or random.nextInt(0x4000)
                val randBLow = random.nextLong() and 0xFFFF_FFFF_FFFFL

                return "%08x-%04x-%04x-%04x-%012x".format(
                    timeHigh,
                    timeMid,
                    versionAndRandA,
                    variantAndRandB,
                    randBLow
                )
            }
        }
    }
}