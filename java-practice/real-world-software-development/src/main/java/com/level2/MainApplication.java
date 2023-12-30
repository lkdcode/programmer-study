package com.level2;

import com.level2.domain.BankStatementCSVParser;
import com.level2.domain.BankTransactionAnalyzer;

import java.io.IOException;

public class MainApplication {
    public static void main(String[] args) throws IOException {
        BankTransactionAnalyzer bankTransactionAnalyzer = new BankTransactionAnalyzer();
        BankStatementCSVParser bankStatementCSVParser = new BankStatementCSVParser();
        bankTransactionAnalyzer.analyze("sample.csv", bankStatementCSVParser);
    }
}
