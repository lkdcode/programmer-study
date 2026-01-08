from fastapi import APIRouter, BackgroundTasks

from src.api.dto.calligraphy_dto import ImageGenerationResponse, ImageGenerationRequest
from src.application.calligraphy_generation_usecase import ImageGenerationUseCase
from src.domain.calligraphy_task_status import CalligraphyTaskStatus

router = APIRouter()
usecase = ImageGenerationUseCase()


@router.post("/api/images/generate", response_model=ImageGenerationResponse)
async def generate_image(request: ImageGenerationRequest, background_tasks: BackgroundTasks):
    background_tasks.add_task(usecase.execute, request)

    return ImageGenerationResponse(
        uuid=request.uuid,
        status=CalligraphyTaskStatus.PENDING
    )
