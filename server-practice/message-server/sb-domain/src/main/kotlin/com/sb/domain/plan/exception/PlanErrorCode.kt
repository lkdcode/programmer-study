package com.sb.domain.plan.exception

import com.sb.domain.exception.DomainErrorCode

enum class PlanErrorCode(
    override val code: String,
    override val message: String,
) : DomainErrorCode {
    PAYMENT_METHOD_REFERENCE_BLANK("PLN001", "결제수단 식별자는 비어있을 수 없습니다."),
    PAYMENT_METHOD_REFERENCE_TOO_LONG("PLN002", "결제수단 식별자는 최대 길이를 초과할 수 없습니다."),

    CREDIT_NEGATIVE("PLN003", "크레딧은 0 이상이어야 합니다."),
    CREDIT_INSUFFICIENT("PLN004", "크레딧이 부족합니다."),
    CREDIT_TX_REASON_REQUIRED("PLN005", "사유는 필수입니다."),

    MONEY_NEGATIVE("PLN006", "금액은 0 이상이어야 합니다."),
    MONEY_SCALE_INVALID("PLN007", "금액 단위가 올바르지 않습니다."),
    MONEY_CURRENCY_MISMATCH("PLN008", "통화가 일치하지 않습니다."),
    MONEY_NEGATIVE_AFTER_MINUS("PLN009", "금액은 0 미만이 될 수 없습니다."),

    PAYMENT_ORDER_INVALID_STATE("PLN010", "결제 상태가 올바르지 않습니다."),
    PAYMENT_ORDER_PROVIDER_PAYMENT_ID_REQUIRED("PLN011", "providerPaymentId는 필수입니다."),
    PAYMENT_ORDER_INVALID_USER_ID("PLN012", "userId가 올바르지 않습니다."),
    PAYMENT_ORDER_INVALID_CREDITS_TO_GRANT("PLN013", "부여할 크레딧은 1 이상이어야 합니다."),
    ;
}