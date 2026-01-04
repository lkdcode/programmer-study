from fastapi import FastAPI
from py_adapter.post.input.rest.post_api import router as post_router, init_api
from py_adapter.post.output.persistence.post_persistence_adapter import PostPersistenceAdapter
from py_application.post.service.create_post_service import CreatePostService

def create_app() -> FastAPI:
    app = FastAPI(title="DDD Board API (Hexagonal)")

    # Dependency Injection
    persistence_adapter = PostPersistenceAdapter()
    create_post_service = CreatePostService(persistence_adapter)
    
    # Initialize API with usecase
    init_api(create_post_service)

    app.include_router(post_router)

    @app.get("/")
    def read_root():
        return {"message": "Welcome to the Refactored DDD & Hexagonal Architecture Board API"}

    return app

app = create_app()

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)

