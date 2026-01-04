from abc import ABC, abstractmethod
from typing import List, Optional
from py_domain.post.aggregate.post_aggregate import PostAggregate
from py_domain.post.entity.post import PostId

class PostRepository(ABC):
    @abstractmethod
    def save(self, aggregate: PostAggregate) -> PostAggregate:
        pass

    @abstractmethod
    def find_all(self) -> List[PostAggregate]:
        pass

    @abstractmethod
    def find_by_id(self, post_id: PostId) -> Optional[PostAggregate]:
        pass

    @abstractmethod
    def delete(self, post_id: PostId) -> None:
        pass

