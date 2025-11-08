CREATE TABLE banana (
  id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name            VARCHAR(100)      NOT NULL,
  sweetness_brix  NUMERIC(4,1)      NOT NULL,
  brand           VARCHAR(120)      NOT NULL,
  grade           VARCHAR(50)       NOT NULL,
  harvested_at    DATE              NOT NULL
);

COMMENT ON TABLE  banana                IS '바나나';
COMMENT ON COLUMN banana.id             IS '식별 인덱스';
COMMENT ON COLUMN banana.name           IS '이름';
COMMENT ON COLUMN banana.sweetness_brix IS '당도';
COMMENT ON COLUMN banana.brand          IS '브랜드/상표';
COMMENT ON COLUMN banana.grade          IS '등급(특/상/보통 등)';
COMMENT ON COLUMN banana.harvested_at   IS '수확일';