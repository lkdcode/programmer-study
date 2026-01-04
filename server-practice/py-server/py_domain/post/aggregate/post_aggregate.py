from datetime import datetime
from py_domain.post.entity.post import Post, PostId
from py_domain.post.value.title import Title
from py_domain.post.value.content import Content

class PostAggregate:
    def __init__(self, post: Post):
        self._post = post

    @property
    def post(self) -> Post:
        return self._post

    def update(self, title: Title, content: Content) -> 'PostAggregate':
        self._post.update(title, content)
        return self

    @classmethod
    def create(cls, title: str, content: str) -> 'PostAggregate':
        # Simple ID generation for example
        post_id = PostId(int(datetime.now().timestamp() * 1000))
        post = Post(
            id=post_id,
            title=Title(title),
            content=Content(content),
            created_at=datetime.now(),
            updated_at=datetime.now()
        )
        return cls(post)

