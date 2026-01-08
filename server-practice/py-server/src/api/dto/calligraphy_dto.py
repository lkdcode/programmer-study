from uuid import UUID

from pydantic import BaseModel

from src.domain.calligraphy_task_status import CalligraphyTaskStatus


class ImageGenerationRequest(BaseModel):
    file_name: str
    text: str
    prompt: str
    uuid: UUID


class ImageGenerationResult(BaseModel):
    uuid: UUID
    image_url: str
    status: CalligraphyTaskStatus


class ImageGenerationResponse(BaseModel):
    uuid: UUID
    status: CalligraphyTaskStatus
