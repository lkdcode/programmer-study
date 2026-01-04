from dataclasses import dataclass

@dataclass(frozen=True)
class Title:
    value: str

    def __post_init__(self):
        if not self.value or not self.value.strip():
            raise ValueError("제목은 필수입니다.")
        if len(self.value) > 150:
            raise ValueError("제목은 150자를 초과할 수 없습니다.")

