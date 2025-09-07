CREATE TABLE `price` (
	`id`              BIGINT UNSIGNED   NOT NULL  AUTO_INCREMENT PRIMARY KEY  COMMENT '식별 인덱스',
	`fruit_id`        BIGINT UNSIGNED   NOT NULL                              COMMENT '과일 식별 인덱스',
    `price`           DECIMAL(10,2)     NOT NULL                              COMMENT '가격',
    `currency`        CHAR(3)           NOT NULL DEFAULT 'KRW'                COMMENT 'ISO 4217 (KRW, USD)',
    `unit`            VARCHAR(20)       NOT NULL DEFAULT 'KG'                 COMMENT '단위 (KG, BOX, EA)',
    FOREIGN KEY (`fruit_id`)            REFERENCES fruit(`id`)                ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO price (fruit_id, price, currency, unit) VALUES
(1, 25000.00, 'KRW', 'BOX'),
(2, 22000.00, 'KRW', 'KG'),
(3, 12000.00, 'KRW', 'KG'),
(4,  6000.00, 'KRW', 'EA'),
(5, 18000.00, 'KRW', 'KG'),
(6, 15000.00, 'KRW', 'KG'),
(7,  8000.00, 'KRW', 'KG'),
(8, 12000.00, 'KRW', 'EA'),
(9,  4000.00, 'KRW', 'KG'),
(10, 7000.00, 'KRW', 'BOX');