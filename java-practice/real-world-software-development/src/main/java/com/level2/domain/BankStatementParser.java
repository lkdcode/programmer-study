package com.level2.domain;

import java.util.List;

public interface BankStatementParser {
    BankTransaction parseFrom(String var1);

    List<BankTransaction> parseLinesFrom(List<String> var1);
}
