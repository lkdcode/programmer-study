CREATE TABLE `fruit` (
	`id`              BIGINT UNSIGNED   NOT NULL  AUTO_INCREMENT PRIMARY KEY  COMMENT '식별 인덱스',
    `variety`         VARCHAR(100)      NOT NULL                              COMMENT '품종/품목',
    `sweetness_brix`  DECIMAL(4,1)      NOT NULL                              COMMENT '당도',
    `brand`           VARCHAR(120)      NOT NULL                              COMMENT '브랜드/상표',
    `grade`           VARCHAR(50)       NOT NULL                              COMMENT '등급(특/상/보통 등)',
    `harvested_at`    DATE              NOT NULL                              COMMENT '수확일'
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO fruit (variety, sweetness_brix, brand, grade, harvested_at) VALUES
('사과', 13.5, '자바 농장', '특', '2025-09-01'),
('샤인머스캣', 18.7, 'lkdcode 농장', '상', '2025-08-28'),
('오렌지', 11.2, 'lkdcode 농장', '상', '2025-08-20'),
('딸기', 9.5, 'lkdcode 농장', '상', '2025-03-15'),
('배', 12.1, '자바 농장', '특', '2025-09-02'),
('복숭아', 12.8, 'lkdcode 농장', '보통', '2025-08-18'),
('자두', 12.0, 'lkdcode 농장', '보통', '2025-08-10'),
('멜론', 14.8, '자바 농장', '특', '2025-07-30'),
('바나나', 19.2, 'lkdcode 농장', '상', '2025-08-25'),
('블루베리', 12.3, 'lkdcode 농장', '상', '2025-07-15');