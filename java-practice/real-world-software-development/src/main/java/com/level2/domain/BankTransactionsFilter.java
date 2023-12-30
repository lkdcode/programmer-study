package com.level2.domain;

@FunctionalInterface
public interface BankTransactionsFilter {
    boolean test(BankTransaction bankTransaction);
}
