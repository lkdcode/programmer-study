import os


class S3Adapter:
    def __init__(self):
        self.bucket_name = os.getenv("S3_BUCKET_NAME", "ai-image-bucket")

    async def upload(self, image_bytes: bytes, file_name: str) -> str:
        return f"https://{self.bucket_name}.s3.amazonaws.com/{file_name}"