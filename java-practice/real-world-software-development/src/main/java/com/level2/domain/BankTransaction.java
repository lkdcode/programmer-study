package com.level2.domain;

import java.time.LocalDate;
import java.util.Objects;

public class BankTransaction {
    private final LocalDate date;
    private final double amount;
    private final String description;

    public BankTransaction(LocalDate date, double amount, String description) {
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getDescription() {
        return this.description;
    }

    public String toString() {
        return "BankTransaction{date=" + this.date + ", amount=" + this.amount + ", description='" + this.description + "'}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            BankTransaction that = (BankTransaction) o;
            return Double.compare(that.amount, this.amount) == 0 && Objects.equals(this.date, that.date) && Objects.equals(this.description, that.description);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.date, this.amount, this.description});
    }
}
