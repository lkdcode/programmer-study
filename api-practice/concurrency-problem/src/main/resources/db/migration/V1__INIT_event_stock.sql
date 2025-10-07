CREATE TABLE event_stock (
    id           BIGINT UNSIGNED   AUTO_INCREMENT PRIMARY KEY,
    event_name   VARCHAR(100)      NOT NULL,
    stock        BIGINT            NOT NULL
);

INSERT INTO event_stock (event_name, stock) VALUES ('선착순이벤트', 100);