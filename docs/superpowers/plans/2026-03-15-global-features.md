# Global Features Implementation Plan

> **For agentic workers:** REQUIRED: Use superpowers:subagent-driven-development (if subagents available) or superpowers:executing-plans to implement this plan. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** HDDC 웹앱에 글로벌 헤더(확장), 아이콘 사이드바, FAB를 추가하고, Route Groups로 레이아웃을 분리한다.

**Architecture:** Next.js App Router의 Route Groups `(public)`과 `(app)`으로 레이아웃을 분리. `(public)`은 헤더+FAB, `(app)`은 헤더+사이드바+FAB를 제공. `[username]`은 글로벌 UI 없음.

**Tech Stack:** Next.js 16, React 19, Tailwind CSS 4, shadcn/ui, @phosphor-icons/react (duotone)

**Dev Port:** 3200 (`next dev -p 3200`)

**제약사항:** `/dashboard/edit/page.tsx` 수정 금지 (다른 세션 작업 중)

**Working Directory:** 모든 bash 커맨드는 `hddc/hddc-client-web/` 기준. 첫 번째 커맨드에서 `cd` 후 유지.

---

## Chunk 1: Route Groups Migration

### Task 1: Route Group 디렉토리 구조 생성 및 파일 이동

기존 파일들을 `(public)`, `(app)` Route Groups로 이동한다.

**Files:**
- Create: `src/app/(public)/` directory
- Move: `src/app/page.tsx` → `src/app/(public)/page.tsx`
- Move: `src/app/auth/` → `src/app/(public)/auth/`
- Create: `src/app/(app)/` directory
- Move: `src/app/dashboard/` → `src/app/(app)/dashboard/`

- [ ] **Step 1: (public) 그룹 생성 및 홈 페이지 이동**

```bash
cd hddc/hddc-client-web
mkdir -p src/app/\(public\)
mv src/app/page.tsx src/app/\(public\)/page.tsx
```

- [ ] **Step 2: auth 디렉토리 이동**

```bash
mv src/app/auth src/app/\(public\)/auth
```

- [ ] **Step 3: (app) 그룹 생성 및 dashboard 이동**

```bash
mkdir -p src/app/\(app\)
mv src/app/dashboard src/app/\(app\)/dashboard
```

- [ ] **Step 4: 개발 서버 시작하여 라우트 확인**

```bash
cd hddc/hddc-client-web && npx next dev -p 3200
```

브라우저에서 확인:
- `http://localhost:3200/` → 홈 페이지 정상 표시
- `http://localhost:3200/auth/login` → 로그인 페이지 정상 표시
- `http://localhost:3200/dashboard/edit` → 편집 페이지 정상 표시

- [ ] **Step 5: Commit**

```bash
git add -A
git commit -m "refactor: introduce route groups (public) and (app) for layout separation"
```

---

## Chunk 2: FAB Component

### Task 2: FAB 컴포넌트 구현

우측 하단 Floating Action Button — Help/Support 메뉴 4개 항목.

**Files:**
- Create: `src/components/fab.tsx`

- [ ] **Step 1: FAB 컴포넌트 생성**

`src/components/fab.tsx`:

```tsx
"use client";

import { useState, useEffect, useRef } from "react";
import {
  Question,
  X,
  ChatCircle,
  BookOpen,
  Lightbulb,
  Megaphone,
} from "@phosphor-icons/react";
import { cn } from "@/lib/utils";

const MENU_ITEMS = [
  { icon: ChatCircle, label: "문의하기", href: "#contact" },
  { icon: BookOpen, label: "FAQ / 가이드", href: "#faq" },
  { icon: Lightbulb, label: "피드백 보내기", href: "#feedback" },
  { icon: Megaphone, label: "공지사항", href: "#notice", badge: "NEW" },
];

export function Fab() {
  const [open, setOpen] = useState(false);
  const containerRef = useRef<HTMLDivElement>(null);

  // Close on outside click
  useEffect(() => {
    if (!open) return;
    function handleClick(e: MouseEvent) {
      if (containerRef.current && !containerRef.current.contains(e.target as Node)) {
        setOpen(false);
      }
    }
    document.addEventListener("mousedown", handleClick);
    return () => document.removeEventListener("mousedown", handleClick);
  }, [open]);

  // Close on Escape
  useEffect(() => {
    if (!open) return;
    function handleKey(e: KeyboardEvent) {
      if (e.key === "Escape") setOpen(false);
    }
    document.addEventListener("keydown", handleKey);
    return () => document.removeEventListener("keydown", handleKey);
  }, [open]);

  return (
    <div ref={containerRef} className="fixed bottom-6 right-6 z-[60]">
      {/* Menu popup */}
      <div
        className={cn(
          "absolute bottom-16 right-0 mb-2 min-w-[200px] rounded-xl border border-border bg-card p-1.5 shadow-lg transition-all duration-150 ease-out",
          open
            ? "translate-y-0 opacity-100"
            : "pointer-events-none translate-y-2 opacity-0",
        )}
        role="menu"
      >
        {MENU_ITEMS.map(({ icon: Icon, label, href, badge }) => (
          <a
            key={label}
            href={href}
            role="menuitem"
            className="flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm text-foreground transition-colors hover:bg-muted"
          >
            <Icon className="size-5 text-muted-foreground" weight="duotone" />
            <span>{label}</span>
            {badge && (
              <span className="ml-auto rounded-full bg-primary px-1.5 py-0.5 text-[10px] font-semibold text-primary-foreground">
                {badge}
              </span>
            )}
          </a>
        ))}
      </div>

      {/* FAB button */}
      <button
        onClick={() => setOpen(!open)}
        aria-expanded={open}
        aria-haspopup="menu"
        aria-label="도움말 메뉴"
        className="flex size-[52px] cursor-pointer items-center justify-center rounded-full bg-primary text-primary-foreground shadow-lg transition-transform duration-150 hover:scale-105 active:scale-95"
      >
        {open ? (
          <X className="size-6" weight="bold" />
        ) : (
          <Question className="size-6" weight="bold" />
        )}
      </button>
    </div>
  );
}
```

- [ ] **Step 2: 개발 서버에서 FAB 단독 확인**

아직 레이아웃에 넣지 않고, 홈 페이지에 임시로 import하여 렌더링 확인.

`src/app/(public)/page.tsx`의 return문 마지막에 임시 추가:
```tsx
import { Fab } from "@/components/fab";
// ... 기존 코드 ...
<Fab />
```

브라우저에서 `http://localhost:3200/` 확인:
- 우측 하단에 `?` 아이콘 FAB 표시
- 클릭 시 4개 메뉴 팝업
- 바깥 클릭/Escape로 닫힘
- 애니메이션 동작 확인

- [ ] **Step 3: 확인 후 임시 import 제거**

홈 페이지에서 Fab import와 `<Fab />`을 제거한다.

- [ ] **Step 4: Commit**

```bash
git add src/components/fab.tsx
git commit -m "feat: add FAB (Floating Action Button) component with help menu"
```

---

## Chunk 3: IconSidebar Component

### Task 3: IconSidebar 컴포넌트 구현

Dashboard 계열에서만 표시되는 collapsed 아이콘 사이드바.

**Files:**
- Create: `src/components/icon-sidebar.tsx`

- [ ] **Step 1: IconSidebar 컴포넌트 생성**

`src/components/icon-sidebar.tsx`:

```tsx
"use client";

import { useState } from "react";
import Link from "next/link";
import { usePathname } from "next/navigation";
import {
  House,
  PencilSimple,
  ChartBar,
  GearSix,
} from "@phosphor-icons/react";
import { cn } from "@/lib/utils";

interface SidebarItem {
  icon: React.ComponentType<{ className?: string; weight?: string }>;
  label: string;
  href: string;
}

const SIDEBAR_ITEMS: SidebarItem[] = [
  { icon: House, label: "대시보드", href: "/dashboard" },
  { icon: PencilSimple, label: "프로필 편집", href: "/dashboard/edit" },
  { icon: ChartBar, label: "분석", href: "/dashboard/analytics" },
];

const BOTTOM_ITEMS: SidebarItem[] = [
  { icon: GearSix, label: "설정", href: "/dashboard/settings" },
];

function SidebarIcon({ item }: { item: SidebarItem }) {
  const pathname = usePathname();
  const isActive = pathname === item.href || pathname.startsWith(item.href + "/");
  const [showTooltip, setShowTooltip] = useState(false);
  const Icon = item.icon;

  return (
    <div className="relative">
      <Link
        href={item.href}
        onMouseEnter={() => setShowTooltip(true)}
        onMouseLeave={() => setShowTooltip(false)}
        className={cn(
          "flex size-9 items-center justify-center rounded-lg transition-colors",
          isActive
            ? "bg-primary text-primary-foreground"
            : "text-muted-foreground hover:bg-muted hover:text-foreground",
        )}
        aria-label={item.label}
      >
        <Icon className="size-5" weight={isActive ? "fill" : "duotone"} />
      </Link>

      {/* Tooltip */}
      {showTooltip && (
        <div
          role="tooltip"
          className="absolute left-full top-1/2 z-50 ml-3 -translate-y-1/2 whitespace-nowrap rounded-md border border-border bg-popover px-2.5 py-1 text-xs font-medium text-popover-foreground shadow-md"
        >
          {item.label}
        </div>
      )}
    </div>
  );
}

export function IconSidebar() {
  return (
    <aside className="hidden lg:flex w-14 flex-col items-center border-r border-border bg-background py-4 gap-1.5">
      <div className="flex flex-1 flex-col items-center gap-1.5">
        {SIDEBAR_ITEMS.map((item) => (
          <SidebarIcon key={item.href} item={item} />
        ))}
      </div>
      <div className="flex flex-col items-center gap-1.5">
        {BOTTOM_ITEMS.map((item) => (
          <SidebarIcon key={item.href} item={item} />
        ))}
      </div>
    </aside>
  );
}
```

- [ ] **Step 2: Commit**

```bash
git add src/components/icon-sidebar.tsx
git commit -m "feat: add IconSidebar component with tooltip and active state"
```

---

## Chunk 4: Layout Wiring

### Task 4: (public) 레이아웃 생성

SiteHeader + FAB를 제공하는 (public) 그룹 레이아웃.

**Files:**
- Create: `src/app/(public)/layout.tsx`
- Modify: `src/app/(public)/page.tsx` — SiteHeader 직접 렌더링 제거

- [ ] **Step 1: (public)/layout.tsx 생성**

`src/app/(public)/layout.tsx`:

```tsx
import { SiteHeader } from "@/components/site-header";
import { ColorThemePicker } from "@/components/color-theme-picker";
import { ThemeToggle } from "@/components/theme-toggle";
import { Fab } from "@/components/fab";

export default function PublicLayout({ children }: { children: React.ReactNode }) {
  return (
    <>
      <SiteHeader>
        <ColorThemePicker />
        <ThemeToggle />
      </SiteHeader>
      {children}
      <Fab />
    </>
  );
}
```

- [ ] **Step 2: 홈 페이지에서 SiteHeader 제거**

`src/app/(public)/page.tsx`에서:
- `import { SiteHeader }` 제거
- `import { ColorThemePicker }` 제거
- `import { ThemeToggle }` 제거 (사용처 없어지면)
- JSX에서 `<SiteHeader>...</SiteHeader>` 블록 제거

변경 후 홈 페이지는 `<main>` 부터 시작해야 함. 기존의 `<div className="relative flex min-h-svh flex-col">` 래퍼는 유지.

주의: `ThemeToggle`은 홈 페이지 본문에서 사용하지 않으므로 import 제거. `ColorThemePicker`도 마찬가지.

- [ ] **Step 3: Auth 레이아웃 높이 조정**

`src/app/(public)/auth/layout.tsx`에서 외부 div의 `min-h-svh`를 제거한다. `(public)/layout.tsx`의 SiteHeader가 위에 추가되므로 `min-h-svh`는 카드를 뷰포트 아래로 밀어낸다.

변경: `min-h-svh` → `min-h-[calc(100svh-3.5rem)]` (3.5rem = h-14 헤더 높이)

```tsx
// Before
<div className="flex min-h-svh items-center justify-center px-4 py-12">

// After
<div className="flex min-h-[calc(100svh-3.5rem)] items-center justify-center px-4 py-12">
```

- [ ] **Step 4: 브라우저 확인**

`http://localhost:3200/` 확인:
- 헤더가 레이아웃에서 한 번만 렌더링
- FAB가 우측 하단에 표시
- Auth 페이지(`/auth/login`)에서도 헤더 + FAB 표시
- Auth 카드가 헤더 아래에서 세로 중앙 정렬

- [ ] **Step 5: Commit**

```bash
git add src/app/\(public\)/layout.tsx src/app/\(public\)/page.tsx src/app/\(public\)/auth/layout.tsx
git commit -m "feat: add (public) layout with SiteHeader and FAB"
```

### Task 5: (app) 레이아웃 생성

SiteHeader + IconSidebar + FAB를 제공하는 (app) 그룹 레이아웃.

**Files:**
- Create: `src/app/(app)/layout.tsx`
- Modify: `src/app/(app)/dashboard/layout.tsx` — SiteHeader 렌더링 제거, 인증 체크만 유지

- [ ] **Step 1: (app)/layout.tsx 생성**

`src/app/(app)/layout.tsx`:

```tsx
import { SiteHeader } from "@/components/site-header";
import { ThemeToggle } from "@/components/theme-toggle";
import { IconSidebar } from "@/components/icon-sidebar";
import { Fab } from "@/components/fab";

export default function AppLayout({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex min-h-svh flex-col">
      <SiteHeader maxWidth="max-w-full">
        <ThemeToggle />
      </SiteHeader>
      <div className="flex flex-1">
        <IconSidebar />
        <main className="flex flex-1 flex-col">{children}</main>
      </div>
      <Fab />
    </div>
  );
}
```

- [ ] **Step 2: dashboard/layout.tsx에서 SiteHeader 제거**

`src/app/(app)/dashboard/layout.tsx`를 수정하여 SiteHeader, ThemeToggle 렌더링을 제거하고 인증 체크 + 로그아웃 로직만 남긴다.

변경 후:

```tsx
"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";

export default function DashboardLayout({ children }: { children: React.ReactNode }) {
  const router = useRouter();
  const [authed, setAuthed] = useState(false);

  useEffect(() => {
    const flag = localStorage.getItem("hddc-auth");
    if (!flag) {
      router.replace("/auth/login");
    } else {
      setAuthed(true);
    }
  }, [router]);

  if (!authed) {
    return (
      <div className="flex flex-1 items-center justify-center">
        <p className="text-sm text-muted-foreground">로딩 중...</p>
      </div>
    );
  }

  return <>{children}</>;
}
```

주의:
- 로그아웃 버튼은 SiteHeader로 이동해야 함. 이 Task에서는 우선 제거하고, Task 6에서 SiteHeader 확장 시 추가한다.
- 로딩 상태에서 `min-h-svh` → `flex-1`로 변경. `(app)/layout.tsx`가 이미 `min-h-svh`를 제공하므로 중첩 방지.

- [ ] **Step 3: 브라우저 확인**

`http://localhost:3200/dashboard/edit` 확인:
- 상단에 SiteHeader (풀 너비)
- 좌측에 아이콘 사이드바 (lg 이상에서만)
- 우측 하단에 FAB
- 편집 페이지 콘텐츠 정상 표시

- [ ] **Step 4: Commit**

```bash
git add src/app/\(app\)/layout.tsx src/app/\(app\)/dashboard/layout.tsx
git commit -m "feat: add (app) layout with SiteHeader, IconSidebar, and FAB"
```

---

## Chunk 5: SiteHeader Extension

### Task 6: SiteHeader에 유저 메뉴 추가

로그아웃 버튼과 유저 아바타를 SiteHeader에 추가한다.

**Files:**
- Modify: `src/components/site-header.tsx` — 유저 아바타/메뉴 슬롯 추가

- [ ] **Step 1: SiteHeader에 유저 메뉴 슬롯 추가**

기존 SiteHeader의 props에 `userMenu`를 추가한다. 기존 `children`은 그대로 유지 (ThemeToggle, ColorThemePicker 등).

`src/components/site-header.tsx`:

```tsx
import Link from "next/link";
import { cn } from "@/lib/utils";

interface SiteHeaderProps {
  nav?: React.ReactNode;
  children?: React.ReactNode;
  userMenu?: React.ReactNode;
  maxWidth?: string;
}

export function SiteHeader({ nav, children, userMenu, maxWidth = "max-w-5xl" }: SiteHeaderProps) {
  return (
    <header className="sticky top-0 z-50 border-b border-border bg-background/80 backdrop-blur-md">
      <nav className={cn("mx-auto flex h-14 items-center justify-between px-4 sm:px-6", maxWidth)}>
        <div className="flex items-center gap-6">
          <Link href="/" className="text-lg font-bold tracking-tight">
            핫딜닷쿨
          </Link>
          {nav}
        </div>
        {(children || userMenu) && (
          <div className="flex items-center gap-1">
            {children}
            {userMenu}
          </div>
        )}
      </nav>
    </header>
  );
}
```

- [ ] **Step 2: (app)/layout.tsx에 로그아웃 버튼 추가**

`src/app/(app)/layout.tsx`를 `"use client"`로 변경하고 로그아웃 버튼을 `userMenu` 슬롯에 전달한다.

```tsx
"use client";

import { useRouter } from "next/navigation";
import { SiteHeader } from "@/components/site-header";
import { ThemeToggle } from "@/components/theme-toggle";
import { IconSidebar } from "@/components/icon-sidebar";
import { Fab } from "@/components/fab";
import { Button } from "@/components/ui/button";
import { SignOut } from "@phosphor-icons/react";

export default function AppLayout({ children }: { children: React.ReactNode }) {
  const router = useRouter();

  function handleLogout() {
    localStorage.removeItem("hddc-auth");
    router.push("/auth/login");
  }

  return (
    <div className="flex min-h-svh flex-col">
      <SiteHeader
        maxWidth="max-w-full"
        userMenu={
          <Button variant="ghost" size="icon" onClick={handleLogout} aria-label="로그아웃">
            <SignOut className="size-4" />
          </Button>
        }
      >
        <ThemeToggle />
      </SiteHeader>
      <div className="flex flex-1">
        <IconSidebar />
        <main className="flex flex-1 flex-col">{children}</main>
      </div>
      <Fab />
    </div>
  );
}
```

- [ ] **Step 3: 브라우저 확인**

- `http://localhost:3200/` — 헤더에 로고 + ColorThemePicker + ThemeToggle (유저메뉴 없음)
- `http://localhost:3200/dashboard/edit` — 헤더에 로고 + ThemeToggle + 로그아웃 버튼

- [ ] **Step 4: Commit**

```bash
git add src/components/site-header.tsx src/app/\(app\)/layout.tsx
git commit -m "feat: extend SiteHeader with userMenu slot and add logout to app layout"
```

---

## Chunk 6: Final Verification

### Task 7: 전체 동작 확인 및 정리

**Files:**
- All modified files

- [ ] **Step 1: 전체 라우트 동작 확인**

| URL | 기대 결과 |
|-----|----------|
| `http://localhost:3200/` | 헤더(로고+테마) + 홈 콘텐츠 + FAB |
| `http://localhost:3200/auth/login` | 헤더(로고+테마) + Auth 카드 + FAB |
| `http://localhost:3200/auth/signup` | 헤더(로고+테마) + Auth 카드 + FAB |
| `http://localhost:3200/dashboard/edit` | 헤더(로고+테마+로그아웃) + 사이드바 + 편집기 + FAB |

- [ ] **Step 2: 반응형 확인**

- 모바일(< 1024px): 사이드바 숨김, 헤더+FAB만 표시
- 데스크톱(≥ 1024px): 사이드바 표시

- [ ] **Step 3: FAB 인터랙션 확인**

- 클릭으로 열기/닫기
- 바깥 클릭으로 닫기
- Escape 키로 닫기
- 애니메이션 동작

- [ ] **Step 4: TypeScript 에러 확인**

```bash
cd hddc/hddc-client-web && npx tsc --noEmit
```

Expected: 에러 없음

- [ ] **Step 5: Commit (필요 시)**

모든 확인 완료 후 누락된 변경이 있으면 커밋.
