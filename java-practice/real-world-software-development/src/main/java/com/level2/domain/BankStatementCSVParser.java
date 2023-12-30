package com.level2.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class BankStatementCSVParser implements BankStatementParser {
    private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public BankStatementCSVParser() {
    }

    public List<BankTransaction> parseLinesFrom(List<String> lines) {
        return lines.stream()
                .map(this::parseFrom)
                .collect(Collectors.toList());
    }

    public BankTransaction parseFrom(String line) {
        String[] columns = line.split(",");
        LocalDate date = LocalDate.parse(columns[0], DATE_PATTERN);
        double amount = Double.parseDouble(columns[1]);
        String description = columns[2];
        return new BankTransaction(date, amount, description);
    }
}
