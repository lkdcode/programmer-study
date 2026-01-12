from enum import Enum

from pydantic import BaseModel, Field


class ProcessStatus(str, Enum):
    PENDING = "PENDING"
    PROCESSING = "PROCESSING"
    COMPLETED = "COMPLETED"
    FAILED = "FAILED"
    CANCELLED = "CANCELLED"
    REJECTED = "REJECTED"


class CalligraphyWebhookRequest(BaseModel):
    calligraphy_id: str = Field(..., alias="calligraphyId")
    status: ProcessStatus
    progress: float = Field(..., ge=0.0, le=100.0, description="진행률 (0~100)")

    class Config:
        populate_by_name = True
