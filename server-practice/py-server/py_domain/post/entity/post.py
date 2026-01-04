from dataclasses import dataclass
from typing import Optional
from datetime import datetime
from py_domain.post.value.title import Title
from py_domain.post.value.content import Content

@dataclass(frozen=True)
class PostId:
    value: int

@dataclass
class Post:
    id: PostId
    title: Title
    content: Content
    created_at: datetime
    updated_at: datetime

    def update(self, title: Title, content: Content) -> 'Post':
        self.title = title
        self.content = content
        self.updated_at = datetime.now()
        return self

