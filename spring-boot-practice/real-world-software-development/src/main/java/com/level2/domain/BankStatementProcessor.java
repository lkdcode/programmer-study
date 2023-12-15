package com.level2.domain;

import java.time.Month;
import java.util.List;

public class BankStatementProcessor {
    private final List<BankTransaction> bankTransactions;

    public BankStatementProcessor(List<BankTransaction> bankTransactions) {
        this.bankTransactions = bankTransactions;
    }

    public double calculateTotalAmount() {
        return this.bankTransactions.stream().mapToDouble(BankTransaction::getAmount).sum();
    }

    public double calculateTotalInMonth(Month month) {
        return this.bankTransactions.stream().filter((e) -> {
            return e.getDate().getMonth().equals(month);
        }).mapToDouble(BankTransaction::getAmount).sum();
    }

    public double calculateTotalForCategory(String category) {
        return this.bankTransactions.stream().filter((e) -> {
            return e.getDescription().equals(category);
        }).mapToDouble(BankTransaction::getAmount).sum();
    }
}
