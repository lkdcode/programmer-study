CREATE TABLE event_history (
    id           BIGINT UNSIGNED   AUTO_INCREMENT PRIMARY KEY,
    user_id      BIGINT UNSIGNED   NOT NULL,
    event_id     BIGINT UNSIGNED   NOT NULL
);