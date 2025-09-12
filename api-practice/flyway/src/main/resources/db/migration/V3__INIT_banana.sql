CREATE TABLE `banana` (
	`id`              BIGINT UNSIGNED   NOT NULL  AUTO_INCREMENT PRIMARY KEY  COMMENT '식별 인덱스',
    `name`            VARCHAR(100)      NOT NULL                              COMMENT '이름',
    `sweetness_brix`  DECIMAL(4,1)      NOT NULL                              COMMENT '당도',
    `brand`           VARCHAR(120)      NOT NULL                              COMMENT '브랜드/상표',
    `grade`           VARCHAR(50)       NOT NULL                              COMMENT '등급(특/상/보통 등)',
    `harvested_at`    DATE              NOT NULL                              COMMENT '수확일'
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;