from abc import ABC, abstractmethod
from dataclasses import dataclass
from py_domain.post.entity.post import Post

@dataclass
class CreatePostDTO:
    title: str
    content: str

class CreatePostUsecase(ABC):
    @abstractmethod
    def create(self, dto: CreatePostDTO) -> Post:
        pass

