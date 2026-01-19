CREATE TABLE IF NOT EXISTS mst_calligraphy (
    id         UUID          PRIMARY KEY,
    seed       VARCHAR(10)   NOT NULL,
    text       TEXT          NOT NULL,
    prompt     TEXT              NULL,
    style      VARCHAR(50)   NOT NULL,
    user_id    BIGINT        NOT NULL,
    path       TEXT          NOT NULL,

    created_at TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    created_by BIGINT        NOT NULL DEFAULT 0,
    updated_by BIGINT        NOT NULL DEFAULT 0,
    is_deleted BOOLEAN       NOT NULL DEFAULT FALSE,
    deleted_at TIMESTAMPTZ   NULL,

    CONSTRAINT fk_mst_user
    FOREIGN KEY (user_id) REFERENCES mst_user(id)
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
);

COMMENT ON COLUMN mst_calligraphy.id          IS 'UUID PK';
COMMENT ON COLUMN mst_calligraphy.seed        IS 'seed';
COMMENT ON COLUMN mst_calligraphy.path        IS 'R2 storage path';
COMMENT ON COLUMN mst_calligraphy.text        IS '문구';
COMMENT ON COLUMN mst_calligraphy.prompt      IS '명령어';
COMMENT ON COLUMN mst_calligraphy.style       IS '문구 스타일';
COMMENT ON COLUMN mst_calligraphy.user_id     IS 'mst_user.id';

COMMENT ON COLUMN mst_calligraphy.created_at  IS '데이터가 생성된 시간';
COMMENT ON COLUMN mst_calligraphy.updated_at  IS '데이터가 수정된 시간';
COMMENT ON COLUMN mst_calligraphy.created_by  IS '데이터를 생성한 유저 아이디';
COMMENT ON COLUMN mst_calligraphy.updated_by  IS '데이터를 수정한 유저 아이디';
COMMENT ON COLUMN mst_calligraphy.is_deleted  IS '소프트 삭제 여부';
COMMENT ON COLUMN mst_calligraphy.deleted_at  IS '소프트 삭제된 시간';

CREATE INDEX IF NOT EXISTS idx_mst_calligraphy_user_id_created_at
    ON mst_calligraphy (user_id, created_at DESC);