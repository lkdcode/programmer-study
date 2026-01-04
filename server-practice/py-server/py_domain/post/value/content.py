from dataclasses import dataclass

@dataclass(frozen=True)
class Content:
    value: str

    def __post_init__(self):
        if not self.value or not self.value.strip():
            raise ValueError("내용은 필수입니다.")

