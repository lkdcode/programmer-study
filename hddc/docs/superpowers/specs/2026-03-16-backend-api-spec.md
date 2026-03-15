# 백엔드 API 스펙 — 핫딜닷쿨

## Context

프론트엔드(hddc-client-web) 화면에서 필요한 모든 REST API를 정리한다. 현재 프론트엔드는 localStorage 기반 mock 데이터로 동작하며, 이 문서의 API를 백엔드(hddc-server-api)에서 구현하면 실제 서비스로 전환 가능하다.

**Base URL:** `/api/v1`
**인증:** Bearer JWT (Authorization 헤더)
**공통 응답 형식:**
```json
{
  "code": "SUCCESS",
  "data": { ... }
}
```
**에러 응답:**
```json
{
  "code": "ERROR_CODE",
  "message": "사람이 읽을 수 있는 에러 메시지"
}
```

---

## 1. 인증 (Auth)

> 화면: `/auth/login`, `/auth/signup`

회원가입은 3단계 플로우로 진행된다:
1. 이메일 인증코드 발송
2. 인증코드 확인 (VERIFICATION_COMPLETED 상태 전환)
3. 회원가입 (인증 완료 상태 필요)

### 1-1. 이메일 인증코드 발송

```
POST /api/auth/email-verifications
```

**Request Body:**
```json
{
  "email": "user@example.com"
}
```

| 필드 | 타입 | 검증 |
|------|------|------|
| email | string | 이메일 형식, 필수 |

**동작:**
- 이미 가입된 이메일이면 → `U001` 에러
- SecureRandom으로 6자리 숫자 코드 생성
- Redis에 `VERIFICATION:SIGN_UP:{email}` → 코드 저장 (TTL 5분)
- 이메일 발송 실패 시 Redis 키 삭제 후 에러

**Response (200):**
```json
{
  "code": "SUCCESS",
  "data": {
    "expiresIn": 300
  }
}
```

**에러:**
| code | 설명 |
|------|------|
| U001 | 이미 가입된 이메일 |

### 1-2. 이메일 인증코드 확인

```
POST /api/auth/email-verifications/verify
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "code": "123456"
}
```

**동작:**
- Redis에서 코드 조회 → 만료 시 `V003`
- 코드 불일치 시 시도 횟수 카운트 (`VERIFICATION:SIGN_UP_ATTEMPTS:{email}`)
- 최대 5회 실패 시 → `V005` 잠금
- 성공 시 → Redis 값을 `"VERIFICATION_COMPLETED"`로 교체 (TTL 15분)

**Response (200):**
```json
{
  "code": "SUCCESS"
}
```

**에러:**
| code | 설명 |
|------|------|
| V003 | 인증코드 만료 |
| V004 | 인증코드 불일치 |
| V005 | 인증 시도 횟수 초과 (5회) |

### 1-3. 회원가입

```
POST /api/auth/sign-up
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "Password1!",
  "nickname": "닉네임"
}
```

| 필드 | 타입 | 검증 |
|------|------|------|
| email | string | 이메일 형식, 필수 |
| password | string | 필수, 8~20자 |
| nickname | string | 필수, 2~20자 |

**동작 (순서대로):**
1. Redis에서 `VERIFICATION_COMPLETED` 확인 → 없으면 `V006`
2. 이메일 중복 체크 → 있으면 `U001`
3. 비밀번호 BCrypt 인코딩 후 `mst_user` 저장 (role = USER)
4. Redis 인증 키 삭제
5. 기본 프로필 자동 생성 (`mst_profile`) — slug는 닉네임 기반, 충돌 시 UUID suffix

**Response (201):**
```json
{
  "code": "SUCCESS",
  "data": {
    "accessToken": "eyJ...",
    "refreshToken": "eyJ...",
    "user": {
      "id": "uuid",
      "email": "user@example.com",
      "nickname": "닉네임",
      "plan": "free"
    }
  }
}
```

**에러:**
| code | 설명 |
|------|------|
| V006 | 이메일 인증 미완료 또는 만료 (15분 초과) |
| U001 | 이미 가입된 이메일 |

### 1-4. 로그인

```
POST /api/auth/login
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "Password1!"
}
```

**Response (200):**
```json
{
  "code": "SUCCESS",
  "data": {
    "accessToken": "eyJ...",
    "refreshToken": "eyJ...",
    "user": {
      "id": "uuid",
      "email": "user@example.com",
      "nickname": "닉네임",
      "plan": "free"
    }
  }
}
```

### 1-5. 토큰 갱신

```
POST /api/v1/auth/refresh
```

**Request Body:**
```json
{
  "refreshToken": "eyJ..."
}
```

**Response (200):**
```json
{
  "code": "SUCCESS",
  "data": {
    "accessToken": "eyJ...",
    "refreshToken": "eyJ..."
  }
}
```

### 1-6. 로그아웃

```
POST /api/v1/auth/logout
Authorization: Bearer {accessToken}
```

**Response (200):**
```json
{
  "code": "SUCCESS"
}
```

### 1-7. 비밀번호 재설정 — 인증코드 발송

> 화면: `/auth/forgot-password`

```
POST /api/auth/password-reset/email-verifications
```

**Request Body:**
```json
{
  "email": "user@example.com"
}
```

**동작:**
- 가입되지 않은 이메일이면 → `U002`
- SecureRandom 6자리 코드 생성 → Redis `VERIFICATION:PASSWORD_RESET:{email}` (TTL 5분)
- 발송 시 기존 시도 횟수 초기화

**Response (200):**
```json
{
  "code": "V001",
  "data": {
    "expiresIn": 300
  }
}
```

**에러:**
| code | 설명 |
|------|------|
| U002 | 가입되지 않은 이메일 |
| V007 | 메일 발송 실패 |

### 1-8. 비밀번호 재설정 — 인증코드 확인

```
POST /api/auth/password-reset/email-verifications/verify
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "code": "123456"
}
```

**동작:**
- Redis에서 코드 조회 → 만료 시 `V003`
- 코드 불일치 시 시도 횟수 카운트 (최대 5회)
- 성공 시 → Redis 값을 `"VERIFICATION_COMPLETED"` (TTL 15분)

**Response (200):**
```json
{
  "code": "V002"
}
```

**에러:**
| code | 설명 |
|------|------|
| V003 | 인증코드 만료 (5분 초과) |
| V004 | 인증코드 불일치 |
| V005 | 5회 초과 시도 → 차단 |

### 1-9. 비밀번호 재설정

```
PUT /api/auth/password-reset
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "NewPass1!",
  "passwordConfirm": "NewPass1!"
}
```

| 필드 | 타입 | 검증 |
|------|------|------|
| email | string | 필수, 이메일 형식 |
| password | string | 8~20자, 영문+숫자+특수문자(!@#$%^&*()_=+.) 포함, 공백 불가 |
| passwordConfirm | string | password와 일치 |

**동작:**
1. Redis에서 `VERIFICATION_COMPLETED` 확인 → 없으면 `V006`
2. 사용자 조회 → 없으면 `U002`
3. BCrypt 인코딩 후 비밀번호 업데이트
4. `loginAttemptCount = 0`, `isLocked = false` (계정 잠금 해제)
5. Redis 인증 키 삭제

**Response (200):**
```json
{
  "code": "S003"
}
```

**에러:**
| code | 설명 |
|------|------|
| V006 | 이메일 인증 미완료 또는 만료 (15분 초과) |
| U002 | 사용자 없음 |
| U003 | 비밀번호 형식 불일치 또는 확인 불일치 |

---

## 2. 프로필 (Profile)

> 화면: `/dashboard/edit` (편집), `/[username]` (공개 페이지)

### 2-1. 내 프로필 조회 (인증 필요)

```
GET /api/v1/profiles/me
Authorization: Bearer {accessToken}
```

**Response (200):**
```json
{
  "code": "SUCCESS",
  "data": {
    "id": "uuid",
    "slug": "mypage",
    "nickname": "닉네임",
    "bio": "자기소개",
    "avatarUrl": "https://cdn.../avatar.jpg",
    "backgroundUrl": "https://cdn.../bg.jpg",
    "backgroundColor": "#f0f0f0",
    "linkLayout": "list",
    "linkStyle": "fill",
    "fontFamily": "pretendard",
    "headerLayout": "center",
    "linkAnimation": "none",
    "colorTheme": "default",
    "customPrimaryColor": null,
    "customSecondaryColor": null,
    "darkMode": false,
    "plan": "free",
    "links": [],
    "socials": [],
    "createdAt": "2026-03-16T00:00:00Z",
    "updatedAt": "2026-03-16T00:00:00Z"
  }
}
```

### 2-2. 공개 프로필 조회 (인증 불필요)

```
GET /api/v1/profiles/{slug}
```

**Response (200):** 2-1과 동일 구조
**Response (404):** 프로필 없음

### 2-3. 프로필 수정 (부분 업데이트)

```
PATCH /api/v1/profiles/me
Authorization: Bearer {accessToken}
```

**Request Body (변경할 필드만 전송):**
```json
{
  "slug": "newpage",
  "nickname": "새 닉네임",
  "bio": "새 소개글",
  "backgroundColor": "#ffffff",
  "linkLayout": "grid-2",
  "linkStyle": "outline",
  "fontFamily": "noto-sans",
  "headerLayout": "left",
  "linkAnimation": "fade-in",
  "colorTheme": "teal",
  "customPrimaryColor": "#3b82f6",
  "customSecondaryColor": "#dbeafe",
  "darkMode": true
}
```

| 필드 | 타입 | 허용 값 |
|------|------|---------|
| slug | string | 3~30자, 영문 소문자/숫자/하이픈, 예약어 불가 |
| nickname | string | 2~20자 |
| bio | string | 최대 80자, 3줄 이내 |
| backgroundColor | string \| null | hex 컬러 또는 null |
| linkLayout | string | `list`, `grid-2`, `grid-3` |
| linkStyle | string | `fill`, `outline`, `shadow`, `rounded`, `pill` |
| fontFamily | string | `pretendard`, `noto-sans`, `nanum-gothic`, `nanum-myeongjo`, `gmarket-sans`, `suit` |
| headerLayout | string | `center`, `left`, `avatar-only`, `banner-only` |
| linkAnimation | string | `none`, `fade-in`, `slide-up`, `scale`, `stagger` |
| colorTheme | string | `default`, `white`, `teal`, `orange`, `blue`, `violet`, `yellow`, `red`, `custom` |
| customPrimaryColor | string \| null | hex 컬러 |
| customSecondaryColor | string \| null | hex 컬러 |
| darkMode | boolean | |

**Response (200):** 업데이트된 전체 ProfileData

### 2-4. 슬러그 중복 확인

```
GET /api/v1/profiles/check-slug?slug={slug}
```

**Response (200):**
```json
{
  "code": "SUCCESS",
  "data": {
    "available": true
  }
}
```

### 2-5. 프로필 초기화

```
POST /api/v1/profiles/me/reset
Authorization: Bearer {accessToken}
```

**Response (200):** 초기화된 ProfileData

---

## 3. 프로필 링크 (Links)

> 화면: `/dashboard/edit` — 링크 에디터 섹션

### 3-1. 링크 추가

```
POST /api/v1/profiles/me/links
Authorization: Bearer {accessToken}
```

**Request Body:**
```json
{
  "title": "나의 블로그",
  "url": "https://blog.example.com",
  "description": "기술 블로그입니다"
}
```

| 필드 | 타입 | 검증 |
|------|------|------|
| title | string | 필수, 최대 20자 |
| url | string | 필수, 유효한 URL |
| description | string | 선택, 최대 40자 |

**Response (201):**
```json
{
  "code": "SUCCESS",
  "data": {
    "id": "uuid",
    "title": "나의 블로그",
    "url": "https://blog.example.com",
    "imageUrl": "",
    "description": "기술 블로그입니다",
    "order": 0,
    "enabled": true
  }
}
```

**제한:** Free 플랜 최대 20개, Pro/Business 무제한

### 3-2. 링크 수정

```
PATCH /api/v1/profiles/me/links/{linkId}
Authorization: Bearer {accessToken}
```

**Request Body (변경할 필드만):**
```json
{
  "title": "수정된 제목",
  "url": "https://new-url.com",
  "description": "수정된 설명"
}
```

**Response (200):** 업데이트된 링크

### 3-3. 링크 삭제

```
DELETE /api/v1/profiles/me/links/{linkId}
Authorization: Bearer {accessToken}
```

**Response (204):** No Content

### 3-4. 링크 활성/비활성 토글

```
PATCH /api/v1/profiles/me/links/{linkId}/toggle
Authorization: Bearer {accessToken}
```

**Response (200):**
```json
{
  "code": "SUCCESS",
  "data": {
    "id": "uuid",
    "enabled": false
  }
}
```

### 3-5. 링크 순서 변경

```
PUT /api/v1/profiles/me/links/order
Authorization: Bearer {accessToken}
```

**Request Body:**
```json
{
  "orderedIds": ["uuid-3", "uuid-1", "uuid-2"]
}
```

**Response (200):** 정렬된 전체 링크 배열

---

## 4. 소셜 링크 (Socials)

> 화면: `/dashboard/edit` — 소셜 에디터 섹션

### 4-1. 소셜 링크 추가

```
POST /api/v1/profiles/me/socials
Authorization: Bearer {accessToken}
```

**Request Body:**
```json
{
  "platform": "instagram",
  "url": "myhandle"
}
```

| 필드 | 타입 | 허용 값 |
|------|------|---------|
| platform | string | `instagram`, `youtube`, `x`, `tiktok`, `threads`, `facebook`, `kakaotalk`, `naver-blog`, `email`, `website` |
| url | string | 핸들 또는 전체 URL |

**제한:** 최대 8개, 플랫폼 중복 불가

**Response (201):**
```json
{
  "code": "SUCCESS",
  "data": {
    "id": "uuid",
    "platform": "instagram",
    "url": "myhandle"
  }
}
```

### 4-2. 소셜 링크 수정

```
PATCH /api/v1/profiles/me/socials/{socialId}
Authorization: Bearer {accessToken}
```

**Request Body:**
```json
{
  "url": "newhandle"
}
```

**Response (200):** 업데이트된 소셜 링크

### 4-3. 소셜 링크 삭제

```
DELETE /api/v1/profiles/me/socials/{socialId}
Authorization: Bearer {accessToken}
```

**Response (204):** No Content

### 4-4. 소셜 링크 순서 변경

```
PUT /api/v1/profiles/me/socials/order
Authorization: Bearer {accessToken}
```

**Request Body:**
```json
{
  "orderedIds": ["uuid-2", "uuid-1", "uuid-3"]
}
```

**Response (200):** 정렬된 전체 소셜 링크 배열

---

## 5. 이미지 업로드 (Image Upload)

> 화면: `/dashboard/edit` — 아바타, 배경, 링크 이미지 크롭 모달

### 5-1. 아바타 업로드

```
POST /api/v1/profiles/me/avatar
Authorization: Bearer {accessToken}
Content-Type: multipart/form-data
```

**Form Field:** `file` — JPEG, 400×400px, 최대 2MB

**Response (200):**
```json
{
  "code": "SUCCESS",
  "data": {
    "avatarUrl": "https://cdn.hotdeal.cool/avatars/uuid.jpg"
  }
}
```

### 5-2. 아바타 삭제

```
DELETE /api/v1/profiles/me/avatar
Authorization: Bearer {accessToken}
```

**Response (204):** No Content

### 5-3. 배경 이미지 업로드

```
POST /api/v1/profiles/me/background
Authorization: Bearer {accessToken}
Content-Type: multipart/form-data
```

**Form Field:** `file` — JPEG, 1400×560px, 최대 5MB

**Response (200):**
```json
{
  "code": "SUCCESS",
  "data": {
    "backgroundUrl": "https://cdn.hotdeal.cool/backgrounds/uuid.jpg"
  }
}
```

### 5-4. 배경 이미지 삭제

```
DELETE /api/v1/profiles/me/background
Authorization: Bearer {accessToken}
```

**Response (204):** No Content

### 5-5. 링크 이미지 업로드

```
POST /api/v1/profiles/me/links/{linkId}/image
Authorization: Bearer {accessToken}
Content-Type: multipart/form-data
```

**Form Field:** `file` — JPEG, 400×400px, 최대 2MB

**Response (200):**
```json
{
  "code": "SUCCESS",
  "data": {
    "imageUrl": "https://cdn.hotdeal.cool/links/uuid.jpg"
  }
}
```

### 5-6. 링크 이미지 삭제

```
DELETE /api/v1/profiles/me/links/{linkId}/image
Authorization: Bearer {accessToken}
```

**Response (204):** No Content

---

## 6. 분석 대시보드 (Analytics)

> 화면: `/dashboard` — 통계 카드 + 주간 차트 + 상위 링크

### 6-1. 요약 통계

```
GET /api/v1/profiles/me/analytics/summary?period=7d
Authorization: Bearer {accessToken}
```

| 파라미터 | 타입 | 기본값 | 허용 값 |
|----------|------|--------|---------|
| period | string | 7d | `7d`, `30d`, `90d` |

**Response (200):**
```json
{
  "code": "SUCCESS",
  "data": {
    "totalViews": 1247,
    "totalClicks": 389,
    "clickRate": 31.2,
    "viewsChange": 12.5,
    "clicksChange": -3.2
  }
}
```

### 6-2. 일별 추이 데이터

```
GET /api/v1/profiles/me/analytics/daily?period=7d
Authorization: Bearer {accessToken}
```

**Response (200):**
```json
{
  "code": "SUCCESS",
  "data": [
    { "date": "2026-03-10", "day": "월", "views": 180, "clicks": 52 },
    { "date": "2026-03-11", "day": "화", "views": 210, "clicks": 68 },
    { "date": "2026-03-12", "day": "수", "views": 150, "clicks": 45 }
  ]
}
```

### 6-3. 상위 클릭 링크

```
GET /api/v1/profiles/me/analytics/top-links?limit=5
Authorization: Bearer {accessToken}
```

**Response (200):**
```json
{
  "code": "SUCCESS",
  "data": [
    { "linkId": "uuid", "title": "나의 블로그", "url": "https://blog.example.com", "clicks": 142 },
    { "linkId": "uuid", "title": "포트폴리오", "url": "https://portfolio.com", "clicks": 98 }
  ]
}
```

### 6-4. 클릭 트래킹 (공개, 인증 불필요)

```
POST /api/v1/click
```

**Request Body:**
```json
{
  "slug": "username",
  "linkId": "uuid"
}
```

**Response (200):**
```json
{
  "code": "SUCCESS"
}
```

> 서버에서 Request 헤더로 `User-Agent`, `Referer`, IP를 추출하여 저장.

---

## 7. 구독/플랜 (Subscription)

> 화면: `/pricing`, 스폰서 배너의 "광고 제거하기" 링크

### 7-1. 현재 구독 조회

```
GET /api/v1/profiles/me/subscription
Authorization: Bearer {accessToken}
```

**Response (200):**
```json
{
  "code": "SUCCESS",
  "data": {
    "plan": "free",
    "billingCycleStart": null,
    "billingCycleEnd": null,
    "nextBillingDate": null,
    "cancelledAt": null
  }
}
```

### 7-2. 플랜 변경 (업그레이드/다운그레이드)

```
POST /api/v1/profiles/me/subscription
Authorization: Bearer {accessToken}
```

**Request Body:**
```json
{
  "plan": "pro"
}
```

**Response (200):**
```json
{
  "code": "SUCCESS",
  "data": {
    "plan": "pro",
    "billingUrl": "https://pay.toss.im/..."
  }
}
```

> `billingUrl`이 있으면 프론트엔드에서 결제 페이지로 리다이렉트.

### 7-3. 구독 해지

```
DELETE /api/v1/profiles/me/subscription
Authorization: Bearer {accessToken}
```

**Response (200):**
```json
{
  "code": "SUCCESS",
  "data": {
    "plan": "free",
    "effectiveDate": "2026-04-16T00:00:00Z"
  }
}
```

> 현재 결제 주기 종료 후 free로 전환.

---

## API 요약 테이블

| # | Method | Path | 인증 | 설명 |
|---|--------|------|------|------|
| 1 | POST | `/auth/email-verifications` | X | 회원가입 인증코드 발송 |
| 2 | POST | `/auth/email-verifications/verify` | X | 회원가입 인증코드 확인 |
| 3 | POST | `/auth/sign-up` | X | 회원가입 (인증 완료 필요) |
| 4 | POST | `/auth/login` | X | 로그인 |
| 5 | POST | `/auth/refresh` | X | 토큰 갱신 |
| 6 | POST | `/auth/logout` | O | 로그아웃 |
| 7 | POST | `/auth/password-reset/email-verifications` | X | 비밀번호 재설정 인증코드 발송 |
| 8 | POST | `/auth/password-reset/email-verifications/verify` | X | 비밀번호 재설정 인증코드 확인 |
| 9 | PUT | `/auth/password-reset` | X | 비밀번호 재설정 |
| 10 | GET | `/profiles/me` | O | 내 프로필 조회 |
| 11 | GET | `/profiles/{slug}` | X | 공개 프로필 조회 |
| 12 | PATCH | `/profiles/me` | O | 프로필 수정 |
| 13 | GET | `/profiles/check-slug` | O | 슬러그 중복 확인 |
| 14 | POST | `/profiles/me/reset` | O | 프로필 초기화 |
| 15 | POST | `/profiles/me/links` | O | 링크 추가 |
| 16 | PATCH | `/profiles/me/links/{id}` | O | 링크 수정 |
| 17 | DELETE | `/profiles/me/links/{id}` | O | 링크 삭제 |
| 18 | PATCH | `/profiles/me/links/{id}/toggle` | O | 링크 토글 |
| 19 | PUT | `/profiles/me/links/order` | O | 링크 순서 변경 |
| 20 | POST | `/profiles/me/socials` | O | 소셜 추가 |
| 21 | PATCH | `/profiles/me/socials/{id}` | O | 소셜 수정 |
| 22 | DELETE | `/profiles/me/socials/{id}` | O | 소셜 삭제 |
| 23 | PUT | `/profiles/me/socials/order` | O | 소셜 순서 변경 |
| 24 | POST | `/profiles/me/avatar` | O | 아바타 업로드 |
| 25 | DELETE | `/profiles/me/avatar` | O | 아바타 삭제 |
| 26 | POST | `/profiles/me/background` | O | 배경 업로드 |
| 27 | DELETE | `/profiles/me/background` | O | 배경 삭제 |
| 28 | POST | `/profiles/me/links/{id}/image` | O | 링크 이미지 업로드 |
| 29 | DELETE | `/profiles/me/links/{id}/image` | O | 링크 이미지 삭제 |
| 30 | GET | `/profiles/me/analytics/summary` | O | 요약 통계 |
| 31 | GET | `/profiles/me/analytics/daily` | O | 일별 추이 |
| 32 | GET | `/profiles/me/analytics/top-links` | O | 상위 링크 |
| 33 | POST | `/click` | X | 클릭 트래킹 |
| 34 | GET | `/profiles/me/subscription` | O | 구독 조회 |
| 35 | POST | `/profiles/me/subscription` | O | 플랜 변경 |
| 36 | DELETE | `/profiles/me/subscription` | O | 구독 해지 |

**총 36개 엔드포인트** (인증 9 + 프로필 5 + 링크 5 + 소셜 4 + 이미지 6 + 분석 4 + 구독 3)

## 정책 요약

- **이메일 인증 필수** — 인증 안 하면 가입 불가
- **인증 코드 유효시간 5분**, 인증 완료 후 가입 가능 시간 **15분**
- **인증 코드 오입력 5회 초과 시 차단** (V005)
- **가입 즉시 기본 프로필 자동 생성** (빈 상태, 기본 테마, slug = 닉네임 기반)
- **비밀번호는 BCrypt 단방향 해시 저장**
