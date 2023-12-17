package com.level2.domain;

import java.time.Month;

public class BankTransactionIsInFebruaryAndExpensive implements BankTransactionsFilter {
    @Override
    public boolean test(BankTransaction bankTransaction) {
        return bankTransaction.getDate().getMonth() == Month.FEBRUARY
                && bankTransaction.getAmount() >= 1_000;
    }
}
