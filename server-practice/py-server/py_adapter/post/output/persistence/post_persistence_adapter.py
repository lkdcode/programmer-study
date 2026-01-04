from typing import List, Optional
from py_application.post.ports.output.post_command_port import PostCommandPort
from py_domain.post.aggregate.post_aggregate import PostAggregate
from py_domain.post.entity.post import PostId

class PostPersistenceAdapter(PostCommandPort):
    def __init__(self):
        self._db = {}

    def save(self, aggregate: PostAggregate) -> PostAggregate:
        self._db[aggregate.post.id.value] = aggregate
        return aggregate

    def find_all(self) -> List[PostAggregate]:
        return list(self._db.values())

    def find_by_id(self, post_id: PostId) -> Optional[PostAggregate]:
        return self._db.get(post_id.value)

    def delete(self, post_id: PostId) -> None:
        if post_id.value in self._db:
            del self._db[post_id.value]

