import logging

import httpx

from src.infrastructure.dto.calligraph_webhook_request import CalligraphyWebhookRequest

logger = logging.getLogger(__name__)

SPRING_SERVER_URL = "http://localhost:8080/api/calligraphies/webhook"


class WebhookAdapter:
    def __init__(self):
        self.target_url = SPRING_SERVER_URL

    async def send_result(self, request: CalligraphyWebhookRequest):
        async with httpx.AsyncClient() as client:
            try:
                payload = request.model_dump(by_alias=True)
                response = await client.post(self.target_url, json=payload)
                response.raise_for_status()
                logger.info(f"[성공] {request.calligraphy_id} 전송 완료")
            except Exception as e:
                # 여기서 에러를 로깅만 하고 넘길지, 다시 던질(raise)지는 정책에 따라 결정
                logger.error(f"전송 실패 ({request.calligraphy_id}): {e}")