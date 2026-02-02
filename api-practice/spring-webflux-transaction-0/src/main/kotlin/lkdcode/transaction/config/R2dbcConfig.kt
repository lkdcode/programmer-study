package lkdcode.transaction.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing

/**
 * R2DBC 기본 설정
 *
 * TransactionManager 및 TransactionalOperator Bean은
 * DualTransactionConfig.kt에서 통합 관리됩니다.
 *
 * @see DualTransactionConfig
 */
@Configuration
@EnableR2dbcAuditing
class R2dbcConfig