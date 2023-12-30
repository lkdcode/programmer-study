package com.level7.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

interface Extractor<R> {
    R run(PreparedStatement statement) throws SQLException;
}
