CREATE TABLE debezium_schema_history (
    `id`                  VARCHAR(36)     NOT NULL,
    `history_data`        TEXT,
    `history_data_seq`    INTEGER,
    `record_insert_ts`    TIMESTAMP       NOT NULL,
    `record_insert_seq`   INTEGER         NOT NULL
);

CREATE TABLE debezium_offsets (
    `id`                  VARCHAR(36)     NOT NULL,
    `offset_key`          VARCHAR(1255),
    `offset_val`          VARCHAR(1255),
    `record_insert_ts`    TIMESTAMP       NOT NULL,
    `record_insert_seq`   INTEGER         NOT NULL
);