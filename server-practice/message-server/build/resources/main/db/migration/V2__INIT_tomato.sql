CREATE TABLE tomato (
  id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name            VARCHAR(100)      NOT NULL,
  sweetness_brix  NUMERIC(4,1)      NOT NULL,
  brand           VARCHAR(120)      NOT NULL,
  grade           VARCHAR(50)       NOT NULL,
  harvested_at    DATE              NOT NULL
);

COMMENT ON TABLE  tomato                IS '토마토';
COMMENT ON COLUMN tomato.id             IS '식별 인덱스';
COMMENT ON COLUMN tomato.name           IS '이름';
COMMENT ON COLUMN tomato.sweetness_brix IS '당도';
COMMENT ON COLUMN tomato.brand          IS '브랜드/상표';
COMMENT ON COLUMN tomato.grade          IS '등급(특/상/보통 등)';
COMMENT ON COLUMN tomato.harvested_at   IS '수확일';