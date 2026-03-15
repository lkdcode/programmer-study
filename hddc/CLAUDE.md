# HDDC - Profile Link Service (Linktree-like)

## Project Plan
구현 계획서는 아래 파일을 참조:
`/Users/kiducklee/.claude/plans/distributed-nibbling-lark.md`

새 세션 시작 시 위 플랜 파일을 먼저 읽고 현재 진행 상황을 파악한 뒤 작업을 이어갈 것.

## Tech Stack
- **Frontend (`hddc-client-web/`):** Next.js 14+ (App Router, TypeScript, Tailwind CSS, shadcn/ui)
- **Backend (`hddc-server-api/`):** Kotlin + Spring Boot 3 (Hexagonal Architecture, JPA, PostgreSQL)

## Architecture
- Backend: Hexagonal Architecture (Port & Adapter)
  - `domain/` → 순수 비즈니스 로직 (프레임워크 의존 없음)
  - `application/` → UseCase 계층
  - `adapter/in/web/` → Controller + DTO (Driving Adapter)
  - `adapter/out/persistence/` → JPA Entity + Repository (Driven Adapter)
- Frontend: Next.js App Router with Server Components

## Key Feature
디자인 토큰 기반 테마 시스템 — 10개 빌트인 프리셋 + 사용자 커스터마이징
