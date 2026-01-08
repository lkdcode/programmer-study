import asyncio
import logging
import os

from src.api.dto.calligraphy_dto import ImageGenerationRequest
from src.infrastructure.s3_adapter import S3Adapter
from src.infrastructure.webhook_adapter import WebhookAdapter
from src.services.ai_service import AIService

logger = logging.getLogger(__name__)


class ImageGenerationUseCase:
    def __init__(self):
        self.ai_service = AIService()
        self.s3_adapter = S3Adapter()
        self.webhook_adapter = WebhookAdapter()

    async def execute(self, request: ImageGenerationRequest):
        try:
            logger.info(f"Processing image generation for {request.uuid}")

            await asyncio.sleep(1)
            image_bytes = await self.ai_service.generate_image(request.prompt)
            image_url = await self.s3_adapter.upload(image_bytes, request.file_name)

            await self.webhook_adapter.send_result({
                "uuid": str(request.uuid),
                "image_url": image_url,
                "status": "SUCCESS"
            })

        except Exception as e:
            await self.webhook_adapter.send_result({
                "uuid": str(request.uuid),
                "status": "FAILED",
                "error": str(e)
            })
