from fastapi import APIRouter, Depends
from pydantic import BaseModel
from py_application.post.ports.input.create_post_usecase import CreatePostUsecase, CreatePostDTO

router = APIRouter()

class CreatePostRequest(BaseModel):
    title: str
    content: str

    @property
    def to_dto(self) -> CreatePostDTO:
        return CreatePostDTO(
            title=self.title,
            content=self.content
        )

# In a real app, we'd use a DI container. 
# For now, we'll assume the usecase is injected or available.
# We'll set this up in py_boot.
_create_post_usecase: CreatePostUsecase = None

def init_api(usecase: CreatePostUsecase):
    global _create_post_usecase
    _create_post_usecase = usecase

@router.post("/api/posts")
def create_post(request: CreatePostRequest):
    _create_post_usecase.create(request.to_dto)
    return {"message": "Success!"}

