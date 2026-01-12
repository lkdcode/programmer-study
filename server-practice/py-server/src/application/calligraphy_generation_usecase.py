import asyncio
import logging

from src.api.dto.calligraphy_dto import ImageGenerationRequest
from src.infrastructure.dto.calligraph_webhook_request import CalligraphyWebhookRequest, ProcessStatus
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

            await self.webhook_adapter.send_result(
                CalligraphyWebhookRequest(
                    calligraphyId="testId",
                    status=ProcessStatus.FAILED,
                    progress=15
                )
            )

        except Exception as e:
            logger.warning(e)
            await self.webhook_adapter.send_result(
                CalligraphyWebhookRequest(
                    calligraphyId="testId",
                    status=ProcessStatus.FAILED,
                    progress=15
                )
            )
