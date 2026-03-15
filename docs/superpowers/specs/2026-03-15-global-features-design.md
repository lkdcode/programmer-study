# HDDC Global Features Design

## Overview

HDDC 링크인바이오 웹앱에 글로벌 UI 시스템을 추가한다. 글로벌 헤더(SiteHeader 확장), 아이콘 사이드바, FAB(Floating Action Button) 3개 컴포넌트를 구현하고, Next.js Route Groups로 페이지별 레이아웃을 분리한다.

## Scope

| 페이지 | 헤더 | 사이드바 | FAB |
|--------|------|---------|-----|
| `/` (홈) | O | X | O |
| `/auth/*` | O | X | O |
| `/dashboard/*` | O | O | O |
| `/[username]` | X | X | X |

**제약사항:** `/dashboard/edit/page.tsx`는 다른 세션에서 작업 중이므로 수정하지 않는다.

## Architecture: Route Groups

Next.js App Router의 Route Groups를 활용해 레이아웃을 분리한다. URL은 변경되지 않는다.

```
src/app/
├─ layout.tsx              ← 루트 (테마 프로바이더만)
├─ (public)/
│  ├─ layout.tsx           ← SiteHeader + FAB
│  ├─ page.tsx             ← 홈
│  └─ auth/
│     ├─ layout.tsx        ← Auth 전용 레이아웃
│     ├─ login/page.tsx
│     └─ signup/page.tsx
├─ (app)/
│  ├─ layout.tsx           ← SiteHeader + IconSidebar + FAB
│  └─ dashboard/
│     ├─ layout.tsx        ← 인증 체크만 (SiteHeader 제거)
│     └─ edit/page.tsx
└─ [username]/page.tsx     ← 글로벌 UI 없음
```

## Migration Steps

기존 파일을 Route Groups로 이동하는 순서:

1. `src/app/(public)/` 디렉토리 생성
2. `src/app/page.tsx` → `src/app/(public)/page.tsx` 이동
   - 홈 페이지에서 직접 렌더링하던 `<SiteHeader>`를 제거 (레이아웃에서 렌더링하므로)
3. `src/app/auth/` → `src/app/(public)/auth/` 이동
4. `src/app/(public)/layout.tsx` 생성 — SiteHeader + FAB
5. `src/app/(app)/` 디렉토리 생성
6. `src/app/dashboard/` → `src/app/(app)/dashboard/` 이동
7. `src/app/(app)/layout.tsx` 생성 — SiteHeader + IconSidebar + FAB
8. 기존 `src/app/dashboard/layout.tsx`에서 SiteHeader 렌더링 제거 — 인증 체크만 남김

## Components

### 1. SiteHeader (확장)

기존 `src/components/site-header.tsx`를 확장한다.

- **변경 내용:** 우측에 유저 아바타/프로필 드롭다운 메뉴 추가
- **기존 유지:** 로고, 테마 토글, 컬러 테마 피커
- **메뉴 항목:** 추후 결정 (현재는 구조만 마련)
- **주의:** 홈 페이지(`page.tsx`)에서 직접 SiteHeader를 렌더링하던 코드를 제거해야 함. SiteHeader는 `(public)/layout.tsx`와 `(app)/layout.tsx`에서만 렌더링한다.
- **Auth 페이지 영향:** 기존 auth 레이아웃은 SiteHeader 없이 전체 화면 카드 레이아웃이었음. `(public)/layout.tsx`가 SiteHeader를 추가하므로 auth 레이아웃 아래에 헤더가 붙는 구조로 변경됨.

### 2. IconSidebar (신규)

Vercel/Linear 스타일의 collapsed 아이콘 사이드바.

- **위치:** `src/components/icon-sidebar.tsx`
- **directive:** `"use client"` (호버/툴팁 인터랙션 필요)
- **너비:** 56px 고정
- **아이콘 크기:** 36x36px, border-radius 8px
- **호버:** 우측에 라벨 툴팁 표시 (`role="tooltip"`, `aria-describedby`)
- **활성 상태:** primary 배경색
- **하단 고정:** 설정 아이콘
- **메뉴 항목:** 배열 config로 관리 (`{ icon, label, href }[]`), 추후 확장 가능
- **적용 범위:** `(app)` 레이아웃에서만 렌더링
- **반응형:** `lg:` (1024px) 이상에서만 표시, 모바일에서는 숨김. 모바일 네비게이션은 추후 별도 구현.

### 3. FAB - Floating Action Button (신규)

우측 하단 고정 Help/Support 버튼.

- **위치:** `src/components/fab.tsx`
- **directive:** `"use client"` (열림/닫힘 상태 관리)
- **크기:** 52x52px, 완전 원형
- **색상:** primary 테마 색상
- **아이콘:** 닫힌 상태 `Question` (Phosphor), 열린 상태 `X` (Phosphor)
- **z-index:** 60 (SiteHeader z-50보다 위)
- **위치:** `fixed`, bottom-right (bottom: 1.5rem, right: 1.5rem)
- **그림자:** `0 4px 12px rgba(0,0,0,0.2)`
- **접근성:** `aria-expanded`, `aria-haspopup="menu"`, Escape 키로 닫기

**메뉴 항목 (클릭 시 팝업):**

| 항목 | Phosphor 아이콘 | 설명 |
|------|----------------|------|
| 문의하기 | `ChatCircle` | 이메일/폼 연결 |
| FAQ / 가이드 | `BookOpen` | 도움말 페이지 |
| 피드백 보내기 | `Lightbulb` | 피드백 폼 |
| 공지사항 | `Megaphone` | 공지 목록 (NEW 뱃지) |

**팝업 동작:**
- 클릭 시 위로 펼쳐지는 메뉴
- 바깥 클릭 시 닫힘 (Escape 키 지원)
- 애니메이션: CSS transition, `opacity` + `translateY`, duration 150ms, ease-out

**적용 범위:** `/[username]` 제외 전 페이지

## Tech Stack

- **UI:** shadcn/ui 컴포넌트 + Tailwind CSS 4
- **아이콘:** @phosphor-icons/react (duotone) — 이모지 사용하지 않음
- **애니메이션:** CSS transitions (duration 150ms, ease-out)
- **상태:** React useState (FAB 열림/닫힘, Sidebar 툴팁)

## Dev Environment

- **포트:** 3200 (`next dev -p 3200`)
- **수정 금지:** `/dashboard/edit/page.tsx` (다른 세션 작업 중)
