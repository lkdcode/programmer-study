package com.level2.domain;

import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;

public class BankStatementProcessor {
    private final List<BankTransaction> bankTransactions;

    public BankStatementProcessor(List<BankTransaction> bankTransactions) {
        this.bankTransactions = bankTransactions;
    }

    public double summarizeTransactions(BankTransactionSummarizer bankTransactionSummarizer) {
        double result = 0;

        for (BankTransaction bankTransaction : bankTransactions) {
            result = bankTransactionSummarizer.summarize(result, bankTransaction);
        }

        return result;
    }

    public double calculateTotalInMonth(Month month) {
        return summarizeTransactions((acc, bankTransaction) ->
                bankTransaction.getDate().getMonth() == month
                        ? acc + bankTransaction.getAmount()
                        : acc);
    }

    public List<BankTransaction> findTransactions(final BankTransactionsFilter bankTransactionsFilter) {
        return bankTransactions.stream()
                .filter(bankTransactionsFilter::test)
                .collect(Collectors.toList());
    }

    public List<BankTransaction> findTransactionsGreaterThanEqual(final int amount) {
        return findTransactions(bankTransaction -> bankTransaction.getAmount() >= amount);
    }

    public double calculateTotalAmount() {
        return summarizeTransactions((acc, bankTransaction) -> bankTransaction.getAmount() + acc);
    }

    public double calculateTotalForCategory(String salary) {
        return summarizeTransactions((acc, bankTransaction) -> {
            if (bankTransaction.getDescription().equals(salary)) {
                acc += bankTransaction.getAmount();
            }
            return acc;
        });
    }
}
