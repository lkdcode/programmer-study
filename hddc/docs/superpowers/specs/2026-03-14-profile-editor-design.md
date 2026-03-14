# 프로필 편집 페이지 설계

## 개요

로그인 후 사용자가 자신의 프로필 페이지(hotdeal.cool/username)를 꾸미는 편집 페이지.
리틀리/링크트리를 참고한 Linktree형 프로필 편집기.

## 블록 타입 (1차 MVP)

| 블록 | 설명 | 편집 UI |
|------|------|---------|
| 프로필 헤더 | 프로필 사진 + 닉네임 + 한줄 소개(bio) | placeholder 아바타 + 텍스트 input 2개 (이미지 업로드는 스코프 외, URL 직접 입력) |
| 링크 블록 | 제목 + URL | 링크 카드 리스트, 위/아래 버튼으로 순서 변경, + 추가 버튼 |
| 소셜 아이콘 | Instagram, YouTube, X, GitHub 등 | 플랫폼 선택 드롭다운 + URL input |
| 테마 설정 | 6개 컬러 프리셋 + 다크/라이트 모드 | 컬러 팔레트 선택 + 다크/라이트 토글 |

## 라우트 구조

```
/dashboard/edit    → 프로필 편집 페이지
/dashboard         → 나중에 분석/설정 등 확장 (이번 스코프 아님)
```

## 인증 가드

- `app/dashboard/layout.tsx`에서 인증 상태를 체크
- 1차 MVP: localStorage에 로그인 플래그(`hddc-auth`)가 없으면 `/auth/login`으로 redirect
- 나중에: 백엔드 세션/토큰 기반 인증으로 교체, Next.js middleware에서 처리

## 레이아웃

### 데스크톱 (lg 이상)

```
┌──────────────────────────────────────────────┐
│  TopBar: 로고 | (공간) | 저장 상태 | 프로필   │
├──────────────────────┬───────────────────────┤
│                      │  미리보기 [M] [W]     │
│  편집 폼 (스크롤)     │                       │
│  ┌────────────────┐  │  ┌─────────────────┐  │
│  │ 프로필 헤더     │  │  │  Phone/Browser  │  │
│  ├────────────────┤  │  │  Mockup         │  │
│  │ 링크 관리       │  │  │  (실시간 반영)   │  │
│  ├────────────────┤  │  │                 │  │
│  │ 소셜 아이콘     │  │  └─────────────────┘  │
│  ├────────────────┤  │                       │
│  │ 테마 설정       │  │                       │
│  └────────────────┘  │                       │
└──────────────────────┴───────────────────────┘
```

- 왼쪽: 편집 폼 (세로 스크롤, 섹션별 구분)
- 오른쪽: 미리보기 패널 (sticky, Mobile↔Web 토글)
- 미리보기는 편집 내용이 실시간으로 반영됨

### 모바일 (lg 미만)

- 편집 폼만 표시
- 상단에 "미리보기" 버튼 → 전체화면 오버레이로 미리보기 전환
- 미리보기에서도 Mobile↔Web 토글 가능
- "닫기" 버튼으로 편집 폼으로 복귀

## 미리보기 시스템

### 목업 컴포넌트 리팩터링

기존 `PhoneMockup`과 `BrowserMockup`은 콘텐츠가 하드코딩되어 있음.
`children` prop을 받도록 리팩터링하고, 기존 랜딩/auth 페이지에서는 기본 콘텐츠를 전달.

```
변경 전: <PhoneMockup />  (내부 하드코딩)
변경 후: <PhoneMockup>{children}</PhoneMockup>  (외부에서 콘텐츠 주입)
         <PhoneMockup><DefaultMockupContent /></PhoneMockup>  (기존 용도)
```

### 테마 격리

미리보기 패널은 **글로벌 테마와 독립**해야 함:
- 프로필의 테마/다크모드 설정은 미리보기 wrapper `<div>`에 `data-theme` + `dark` class를 적용
- 글로벌 `ColorThemeProvider`(대시보드 UI 테마)와 분리
- `theme-editor.tsx`는 글로벌 `setColorTheme()`을 호출하지 않고, `profileData.colorTheme`만 변경

### 미리보기 렌더링

- **Mobile 뷰**: PhoneMockup 안에 프로필 헤더 → 링크 리스트 → 소셜 아이콘
- **Web 뷰**: BrowserMockup 안에 동일 데이터를 웹 레이아웃으로 렌더링
- 토글로 Mobile↔Web 전환

### 공유 렌더링 컴포넌트

`profile-preview-content.tsx`는 나중에 공개 프로필 페이지(`[username]/page.tsx`)에서도 재사용할 수 있도록 설계. ProfileData를 props로 받아 렌더링하는 순수 presentational 컴포넌트.

## 데이터 모델

```typescript
interface ProfileData {
  // 프로필 헤더
  avatarUrl: string | null;
  nickname: string;      // max 20자 (기존 validateNickname 규칙)
  bio: string;           // max 80자

  // 링크 블록
  links: ProfileLink[];  // max 20개

  // 소셜 아이콘
  socials: SocialLink[]; // max 8개

  // 테마
  colorTheme: "teal" | "orange" | "blue" | "violet" | "yellow" | "red";
  darkMode: boolean;
}

interface ProfileLink {
  id: string;       // crypto.randomUUID()
  title: string;    // max 50자
  url: string;      // max 2048자, URL 형식 검증
  order: number;    // 순서 변경 시 배열 index 기반으로 재할당 (0, 1, 2, ...)
}

interface SocialLink {
  id: string;
  platform: SocialPlatform;
  url: string;      // max 2048자
}

type SocialPlatform =
  | "instagram"
  | "youtube"
  | "x"
  | "github"
  | "tiktok"
  | "linkedin"
  | "email"
  | "website";
```

### 초기 상태 (신규 사용자)

```typescript
const DEFAULT_PROFILE: ProfileData = {
  avatarUrl: null,
  nickname: "",     // 회원가입 시 입력한 닉네임으로 프리필
  bio: "",
  links: [],
  socials: [],
  colorTheme: "teal",
  darkMode: false,
};
```

미리보기 빈 상태: placeholder 아바타 + "닉네임을 입력하세요" + "링크를 추가해보세요" 안내 문구 표시.

## 저장 전략

### 자동 저장
- 편집 시 debounce(1초)로 localStorage에 저장
- 상태 표시: "저장 중..." → "저장됨" (TopBar에 표시)

### 수동 저장
- TopBar에 "저장" 버튼
- 1차: debounce를 무시하고 즉시 localStorage에 flush
- 나중에: 백엔드 API 호출로 교체 (수동 저장의 주된 용도)

### 스토리지
- 1차: `localStorage` (key: `hddc-profile-data`)
- JSON 파싱 실패 시 DEFAULT_PROFILE로 폴백
- 나중에: 백엔드 API 연동 시 localStorage를 캐시로 활용, API가 source of truth

## use-profile-data 훅 API

```typescript
interface UseProfileData {
  profileData: ProfileData;
  saveStatus: "idle" | "saving" | "saved";

  // 프로필 헤더
  updateProfile: (fields: Partial<Pick<ProfileData, "avatarUrl" | "nickname" | "bio">>) => void;

  // 링크
  addLink: () => void;                                    // 빈 링크 추가
  updateLink: (id: string, fields: Partial<Omit<ProfileLink, "id" | "order">>) => void;
  removeLink: (id: string) => void;
  moveLink: (id: string, direction: "up" | "down") => void;

  // 소셜
  addSocial: (platform: SocialPlatform) => void;
  updateSocial: (id: string, url: string) => void;
  removeSocial: (id: string) => void;

  // 테마
  setColorTheme: (theme: ProfileData["colorTheme"]) => void;
  setDarkMode: (dark: boolean) => void;

  // 저장
  saveNow: () => void;                                    // 수동 저장 (debounce 무시)
}
```

## 컴포넌트 구조

```
app/dashboard/layout.tsx          → 대시보드 공통 레이아웃 (TopBar + 인증 가드)
app/dashboard/edit/page.tsx       → 편집 페이지 (컨테이너)

components/dashboard/
  profile-editor.tsx              → 편집 폼 (왼쪽 패널) — 전체 편집 UI 관리
  profile-header-editor.tsx       → 프로필 헤더 편집 섹션
  link-list-editor.tsx            → 링크 블록 편집 섹션 (추가/삭제/순서변경)
  social-editor.tsx               → 소셜 아이콘 편집 섹션
  theme-editor.tsx                → 테마 설정 섹션
  profile-preview.tsx             → 미리보기 패널 (오른쪽) — Mobile/Web 토글
  profile-preview-content.tsx     → 미리보기 내부 렌더링 (공유 컴포넌트, [username]에서도 재사용)
  mobile-preview-overlay.tsx      → 모바일용 전체화면 미리보기 오버레이

components/device-mockup.tsx      → 리팩터링: children prop 추가

hooks/
  use-profile-data.ts             → 프로필 데이터 상태 관리 + 자동/수동 저장

lib/
  profile-types.ts                → ProfileData, ProfileLink, SocialLink 타입 정의
```

## 필요한 shadcn/ui 컴포넌트 추가

기존: button, checkbox, dialog, input, label
추가 필요: `select` (소셜 플랫폼 선택)

## 기존 코드 변경

| 대상 | 변경 내용 |
|------|-----------|
| `device-mockup.tsx` | `children` prop 추가, 기존 하드코딩 콘텐츠를 default children으로 분리 |
| `reserved-slugs.ts` | `dashboard` 이미 포함되어 있음 (변경 없음) |

## 스코프 외 (나중에)

- 이미지 업로드 서버 (1차에서는 avatar URL 직접 입력 또는 placeholder)
- 드래그 앤 드롭 라이브러리 (1차에서는 위/아래 버튼으로 순서 변경)
- 백엔드 API 연동
- `/dashboard` 메인 (분석 대시보드)
- 구분선/텍스트 블록, 이미지 블록, 영상 임베드, 지도 블록
- CLAUDE.md의 "10개 프리셋" 목표 (현재 6개, 추가 4개는 나중에)
