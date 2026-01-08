import io

from PIL import Image


class AIService:
    async def generate_image(self, prompt: str) -> bytes:
        # 1. 이미지 생성
        img = Image.new('RGB', (512, 512), color=(73, 109, 137))

        # 2. 메모리 버퍼 생성 (가짜 파일 역할)
        buffer = io.BytesIO()

        # 3. 버퍼에 저장 (형식 지정 필수: PNG, JPEG 등)
        img.save(buffer, format="PNG")

        # 4. 버퍼의 내용을 바이트(bytes)로 꺼내서 리턴
        return buffer.getvalue()
