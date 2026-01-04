from abc import ABC, abstractmethod
from py_domain.post.aggregate.post_aggregate import PostAggregate

class PostCommandPort(ABC):
    @abstractmethod
    def save(self, aggregate: PostAggregate) -> PostAggregate:
        pass

