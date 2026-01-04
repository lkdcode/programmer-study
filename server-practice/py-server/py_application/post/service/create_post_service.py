from py_application.post.ports.input.create_post_usecase import CreatePostUsecase, CreatePostDTO
from py_application.post.ports.output.post_command_port import PostCommandPort
from py_domain.post.aggregate.post_aggregate import PostAggregate
from py_domain.post.entity.post import Post

class CreatePostService(CreatePostUsecase):
    def __init__(self, post_command_port: PostCommandPort):
        self._post_command_port = post_command_port

    def create(self, dto: CreatePostDTO) -> Post:
        aggregate = PostAggregate.create(
            title=dto.title,
            content=dto.content
        )
        saved_aggregate = self._post_command_port.save(aggregate)
        return saved_aggregate.post

