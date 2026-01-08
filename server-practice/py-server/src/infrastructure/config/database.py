from collections.abc import AsyncGenerator

from sqlalchemy.ext.asyncio import create_async_engine, async_sessionmaker
from sqlmodel.ext.asyncio.session import AsyncSession

from .settings import settings

# 비동기 엔진 생성
async_engine = create_async_engine(
    settings.database_url,
    echo=True,  # SQL 로그 출력
    pool_size=5,
    max_overflow=10,
    pool_pre_ping=True
)

# 비동기 세션 팩토리
async_session_factory = async_sessionmaker(
    bind=async_engine,
    class_=AsyncSession,
    expire_on_commit=False,
)


async def get_session() -> AsyncGenerator[AsyncSession, None]:
    """FastAPI Depends용 세션 제공"""
    async with async_session_factory() as session:
        yield session
