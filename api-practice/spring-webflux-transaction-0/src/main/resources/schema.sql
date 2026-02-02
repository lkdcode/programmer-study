-- Company 테이블
CREATE TABLE IF NOT EXISTS company (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    brn VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Company Plan 테이블
CREATE TABLE IF NOT EXISTS company_plan (
    id BIGSERIAL PRIMARY KEY,
    company_id BIGINT NOT NULL,
    plan_type VARCHAR(20) NOT NULL DEFAULT 'FREE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES company(id)
);

-- User 테이블
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    company_id BIGINT NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES company(id)
);

-- Terms 동의 테이블
CREATE TABLE IF NOT EXISTS user_terms (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    terms_code VARCHAR(50) NOT NULL,
    agreed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Message 테이블 (이기종 트랜잭션 예제용)
CREATE TABLE IF NOT EXISTS message_metadata (
    id BIGSERIAL PRIMARY KEY,
    message_id VARCHAR(36) NOT NULL UNIQUE,
    tag_id VARCHAR(36),
    tag_description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Outbox 테이블 (Outbox 패턴용)
CREATE TABLE IF NOT EXISTS outbox_event (
    id BIGSERIAL PRIMARY KEY,
    aggregate_type VARCHAR(100) NOT NULL,
    aggregate_id VARCHAR(36) NOT NULL,
    payload TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
