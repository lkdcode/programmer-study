import uvicorn
from fastapi import FastAPI

from src.api.routes import router

app = FastAPI(title="AI API")

app.include_router(router)


@app.get("/")
async def health_check():
    return {"status": "ok", "service": "AI Image Generation"}

if __name__ == "__main__":
    uvicorn.run("src.main:app", host="0.0.0.0", port=8000, reload=True)