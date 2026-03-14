# Profile Editor Implementation Plan

> **For agentic workers:** REQUIRED: Use superpowers:subagent-driven-development (if subagents available) or superpowers:executing-plans to implement this plan. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build a profile editor page at `/dashboard/edit` where users customize their Linktree-style profile (header, links, social icons, theme) with a live side-by-side preview.

**Architecture:** Side-by-side editor (left: form sections, right: live mobile/web preview with toggle). Profile data stored in localStorage via a custom hook with auto-save (debounce 1s) + manual save. Preview uses scoped `data-theme` for theme isolation from the dashboard UI.

**Tech Stack:** Next.js 14+ App Router, TypeScript, Tailwind CSS, shadcn/ui, Phosphor Icons

**Spec:** `docs/superpowers/specs/2026-03-14-profile-editor-design.md`

**Note:** This project has no test infrastructure (no Jest/Vitest/Playwright). Steps use `next build` for type-checking and manual browser verification instead of automated tests.

---

## File Map

| Action | Path | Responsibility |
|--------|------|----------------|
| Create | `src/lib/profile-types.ts` | ProfileData, ProfileLink, SocialLink types + DEFAULT_PROFILE constant |
| Create | `src/hooks/use-profile-data.ts` | Profile state management + localStorage auto-save/manual-save |
| Modify | `src/components/device-mockup.tsx` | Add `children` prop; extract hardcoded content to default |
| Modify | `src/app/page.tsx` | No code change needed (uses PhoneMockup without children → shows default) |
| Modify | `src/app/auth/layout.tsx` | No code change needed (same reason) |
| Create | `src/components/dashboard/profile-preview-content.tsx` | Shared profile renderer (avatar + links + socials). Reusable by `[username]/page.tsx` later |
| Create | `src/components/dashboard/profile-header-editor.tsx` | Avatar URL input + nickname + bio editing |
| Create | `src/components/dashboard/link-list-editor.tsx` | Add/remove/reorder links |
| Create | `src/components/dashboard/social-editor.tsx` | Add/remove social platforms |
| Create | `src/components/dashboard/theme-editor.tsx` | Color preset picker + dark/light toggle (scoped, NOT global) |
| Create | `src/components/dashboard/profile-editor.tsx` | Assembles all editor sections into left panel |
| Create | `src/components/dashboard/profile-preview.tsx` | Right panel: mobile/web toggle + mockup frame + preview content |
| Create | `src/components/dashboard/mobile-preview-overlay.tsx` | Fullscreen preview overlay for mobile screens |
| Create | `src/app/dashboard/layout.tsx` | Dashboard shell: TopBar + auth guard (localStorage check) |
| Create | `src/app/dashboard/edit/page.tsx` | Composes profile-editor + profile-preview side-by-side |
| Install | `shadcn select` component | Needed for social platform dropdown |

---

## Chunk 1: Foundation (Types + Hook + Mockup Refactor)

### Task 1: Profile Types

**Files:**
- Create: `src/lib/profile-types.ts`

- [ ] **Step 1: Create type definitions file**

```typescript
// src/lib/profile-types.ts

export const SOCIAL_PLATFORMS = [
  "instagram",
  "youtube",
  "x",
  "github",
  "tiktok",
  "linkedin",
  "email",
  "website",
] as const;

export type SocialPlatform = (typeof SOCIAL_PLATFORMS)[number];

export interface ProfileLink {
  id: string;
  title: string;
  url: string;
  order: number;
}

export interface SocialLink {
  id: string;
  platform: SocialPlatform;
  url: string;
}

export interface ProfileData {
  avatarUrl: string | null;
  nickname: string;
  bio: string;
  links: ProfileLink[];
  socials: SocialLink[];
  colorTheme: "teal" | "orange" | "blue" | "violet" | "yellow" | "red";
  darkMode: boolean;
}

export const DEFAULT_PROFILE: ProfileData = {
  avatarUrl: null,
  nickname: "",
  bio: "",
  links: [],
  socials: [],
  colorTheme: "teal",
  darkMode: false,
};

export const SOCIAL_PLATFORM_LABELS: Record<SocialPlatform, string> = {
  instagram: "Instagram",
  youtube: "YouTube",
  x: "X (Twitter)",
  github: "GitHub",
  tiktok: "TikTok",
  linkedin: "LinkedIn",
  email: "이메일",
  website: "웹사이트",
};
```

- [ ] **Step 2: Verify types compile**

Run: `cd hddc-client-web && npx next build 2>&1 | tail -5`
Expected: `✓ Compiled successfully`

- [ ] **Step 3: Commit**

```bash
git add src/lib/profile-types.ts
git commit -m "feat: add profile data types and constants"
```

---

### Task 2: use-profile-data Hook

**Files:**
- Create: `src/hooks/use-profile-data.ts`

- [ ] **Step 1: Create the hook**

```typescript
// src/hooks/use-profile-data.ts
"use client";

import { useState, useEffect, useCallback, useRef } from "react";
import {
  type ProfileData,
  type ProfileLink,
  type SocialLink,
  type SocialPlatform,
  DEFAULT_PROFILE,
} from "@/lib/profile-types";

const STORAGE_KEY = "hddc-profile-data";
const DEBOUNCE_MS = 1000;

type SaveStatus = "idle" | "saving" | "saved";

function loadFromStorage(): ProfileData {
  try {
    const raw = localStorage.getItem(STORAGE_KEY);
    if (!raw) return DEFAULT_PROFILE;
    return { ...DEFAULT_PROFILE, ...JSON.parse(raw) };
  } catch {
    return DEFAULT_PROFILE;
  }
}

function saveToStorage(data: ProfileData) {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(data));
}

export function useProfileData() {
  const [profileData, setProfileData] = useState<ProfileData>(DEFAULT_PROFILE);
  const [saveStatus, setSaveStatus] = useState<SaveStatus>("idle");
  const timerRef = useRef<ReturnType<typeof setTimeout>>();
  const initialized = useRef(false);

  // Load from localStorage on mount
  useEffect(() => {
    setProfileData(loadFromStorage());
    initialized.current = true;
  }, []);

  // Auto-save with debounce
  useEffect(() => {
    if (!initialized.current) return;
    setSaveStatus("saving");
    clearTimeout(timerRef.current);
    timerRef.current = setTimeout(() => {
      saveToStorage(profileData);
      setSaveStatus("saved");
    }, DEBOUNCE_MS);
    return () => clearTimeout(timerRef.current);
  }, [profileData]);

  // ─── Profile header ───
  const updateProfile = useCallback(
    (fields: Partial<Pick<ProfileData, "avatarUrl" | "nickname" | "bio">>) => {
      setProfileData((prev) => ({ ...prev, ...fields }));
    },
    [],
  );

  // ─── Links ───
  const addLink = useCallback(() => {
    setProfileData((prev) => {
      if (prev.links.length >= 20) return prev;
      const newLink: ProfileLink = {
        id: crypto.randomUUID(),
        title: "",
        url: "",
        order: prev.links.length,
      };
      return { ...prev, links: [...prev.links, newLink] };
    });
  }, []);

  const updateLink = useCallback(
    (id: string, fields: Partial<Pick<ProfileLink, "title" | "url">>) => {
      setProfileData((prev) => ({
        ...prev,
        links: prev.links.map((l) =>
          l.id === id ? { ...l, ...fields } : l,
        ),
      }));
    },
    [],
  );

  const removeLink = useCallback((id: string) => {
    setProfileData((prev) => ({
      ...prev,
      links: prev.links
        .filter((l) => l.id !== id)
        .map((l, i) => ({ ...l, order: i })),
    }));
  }, []);

  const moveLink = useCallback(
    (id: string, direction: "up" | "down") => {
      setProfileData((prev) => {
        const idx = prev.links.findIndex((l) => l.id === id);
        if (idx === -1) return prev;
        const newIdx = direction === "up" ? idx - 1 : idx + 1;
        if (newIdx < 0 || newIdx >= prev.links.length) return prev;
        const newLinks = [...prev.links];
        [newLinks[idx], newLinks[newIdx]] = [newLinks[newIdx], newLinks[idx]];
        return {
          ...prev,
          links: newLinks.map((l, i) => ({ ...l, order: i })),
        };
      });
    },
    [],
  );

  // ─── Socials ───
  const addSocial = useCallback((platform: SocialPlatform) => {
    setProfileData((prev) => {
      if (prev.socials.length >= 8) return prev;
      if (prev.socials.some((s) => s.platform === platform)) return prev;
      const newSocial: SocialLink = {
        id: crypto.randomUUID(),
        platform,
        url: "",
      };
      return { ...prev, socials: [...prev.socials, newSocial] };
    });
  }, []);

  const updateSocial = useCallback((id: string, url: string) => {
    setProfileData((prev) => ({
      ...prev,
      socials: prev.socials.map((s) => (s.id === id ? { ...s, url } : s)),
    }));
  }, []);

  const removeSocial = useCallback((id: string) => {
    setProfileData((prev) => ({
      ...prev,
      socials: prev.socials.filter((s) => s.id !== id),
    }));
  }, []);

  // ─── Theme ───
  const setColorTheme = useCallback(
    (theme: ProfileData["colorTheme"]) => {
      setProfileData((prev) => ({ ...prev, colorTheme: theme }));
    },
    [],
  );

  const setDarkMode = useCallback((dark: boolean) => {
    setProfileData((prev) => ({ ...prev, darkMode: dark }));
  }, []);

  // ─── Manual save ───
  const saveNow = useCallback(() => {
    clearTimeout(timerRef.current);
    setSaveStatus("saving");
    saveToStorage(profileData);
    setSaveStatus("saved");
  }, [profileData]);

  return {
    profileData,
    saveStatus,
    updateProfile,
    addLink,
    updateLink,
    removeLink,
    moveLink,
    addSocial,
    updateSocial,
    removeSocial,
    setColorTheme,
    setDarkMode,
    saveNow,
  };
}
```

- [ ] **Step 2: Verify build passes**

Run: `cd hddc-client-web && npx next build 2>&1 | tail -5`
Expected: `✓ Compiled successfully`

- [ ] **Step 3: Commit**

```bash
git add src/hooks/use-profile-data.ts
git commit -m "feat: add use-profile-data hook with auto-save"
```

---

### Task 3: Refactor Device Mockups to Accept Children

**Files:**
- Modify: `src/components/device-mockup.tsx`

The current `PhoneMockup` and `BrowserMockup` have hardcoded content. Add optional `children` prop — when provided, render children instead of default content. This preserves all existing usages (landing page, auth layout) which pass no children.

- [ ] **Step 1: Refactor PhoneMockup**

The component signature changes from `{ onClick?, className? }` to `{ onClick?, className?, children? }`.
When `children` is provided, render it inside the phone frame instead of the hardcoded avatar + links.

```typescript
// New PhoneMockup signature:
export function PhoneMockup({
  onClick,
  className,
  children,
}: {
  onClick?: () => void;
  className?: string;
  children?: React.ReactNode;
}) {
  return (
    <div onClick={onClick} className={cn("relative", className)}>
      <div className="rounded-[1.75rem] border-2 border-border bg-card p-2 shadow-lg">
        <div className="mx-auto mb-2 h-4 w-16 rounded-full bg-border/60" />
        <div className="rounded-[1.25rem] bg-background p-3">
          {children ?? (
            <>
              {/* existing hardcoded default content */}
              <div className="flex flex-col items-center gap-2 pb-3">
                <div className="flex size-10 items-center justify-center rounded-full bg-primary/10 text-sm font-bold text-primary">핫</div>
                <div className="text-center">
                  <p className="text-xs font-semibold">핫딜닷쿨</p>
                  <p className="text-[10px] text-muted-foreground">크리에이터</p>
                </div>
              </div>
              <div className="flex flex-col gap-1.5">
                {["추천 상품", "리뷰 블로그", "제휴 파트너스"].map((label) => (
                  <div key={label} className="flex h-7 items-center justify-center rounded-lg bg-muted/60 text-[10px] font-medium">{label}</div>
                ))}
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  );
}
```

- [ ] **Step 2: Refactor BrowserMockup**

Same pattern — add `children` prop, keep hardcoded content as fallback.

```typescript
// New BrowserMockup signature:
export function BrowserMockup({
  onClick,
  className,
  children,
}: {
  onClick?: () => void;
  className?: string;
  children?: React.ReactNode;
}) {
  return (
    <div onClick={onClick} className={cn("relative", className)}>
      <div className="rounded-xl border-2 border-border bg-card shadow-lg">
        {/* Browser chrome bar (always shown) */}
        <div className="flex items-center gap-1.5 border-b border-border px-3 py-2">
          <div className="flex gap-1">
            <span className="size-2 rounded-full bg-red-400/70" />
            <span className="size-2 rounded-full bg-yellow-400/70" />
            <span className="size-2 rounded-full bg-green-400/70" />
          </div>
          <div className="ml-2 flex-1 rounded-md bg-muted/60 px-2 py-0.5 text-[9px] text-muted-foreground">
            hotdeal.cool/yourname
          </div>
        </div>
        {/* Content area */}
        <div className="p-3">
          {children ?? (
            <>
              {/* existing hardcoded default content */}
              {/* ... (keep the exact existing grid layout) ... */}
            </>
          )}
        </div>
      </div>
    </div>
  );
}
```

- [ ] **Step 3: Verify existing pages still work**

Run: `cd hddc-client-web && npx next build 2>&1 | tail -5`
Expected: `✓ Compiled successfully` — landing page and auth layout use `<PhoneMockup />` without children, so they render the hardcoded default.

- [ ] **Step 4: Verify visually**

Run: `cd hddc-client-web && npx next dev`
Check: Landing page (`/`) and auth pages (`/auth/login`, `/auth/signup`) look exactly the same as before.

- [ ] **Step 5: Commit**

```bash
git add src/components/device-mockup.tsx
git commit -m "refactor: device mockups accept children prop for dynamic content"
```

---

## Chunk 2: Preview Rendering + Editor Sections

### Task 4: Install shadcn Select Component

- [ ] **Step 1: Install select component**

Run: `cd hddc-client-web && npx shadcn@latest add select`

- [ ] **Step 2: Commit**

```bash
git add src/components/ui/select.tsx
git commit -m "chore: add shadcn select component"
```

---

### Task 5: Profile Preview Content (Shared Renderer)

**Files:**
- Create: `src/components/dashboard/profile-preview-content.tsx`

This component renders the actual profile view (avatar, links, socials) given ProfileData.
It is used inside PhoneMockup/BrowserMockup for the editor preview, and later by `[username]/page.tsx` for the public page.

- [ ] **Step 1: Create profile preview content component**

The component takes `profileData` and `variant` ("mobile" | "web") as props.

Mobile variant: centered single-column layout.
Web variant: two-column grid (profile sidebar + links list).

```typescript
// src/components/dashboard/profile-preview-content.tsx
"use client";

import { type ProfileData, SOCIAL_PLATFORM_LABELS } from "@/lib/profile-types";
import {
  InstagramLogo,
  YoutubeLogo,
  XLogo,
  GithubLogo,
  TiktokLogo,
  LinkedinLogo,
  EnvelopeSimple,
  Globe,
  UserCircle,
} from "@phosphor-icons/react";

const SOCIAL_ICONS: Record<string, typeof InstagramLogo> = {
  instagram: InstagramLogo,
  youtube: YoutubeLogo,
  x: XLogo,
  github: GithubLogo,
  tiktok: TiktokLogo,
  linkedin: LinkedinLogo,
  email: EnvelopeSimple,
  website: Globe,
};

interface Props {
  profileData: ProfileData;
  variant: "mobile" | "web";
}

export function ProfilePreviewContent({ profileData, variant }: Props) {
  const { avatarUrl, nickname, bio, links, socials } = profileData;
  const hasContent = nickname || bio || links.length > 0 || socials.length > 0;

  if (!hasContent) {
    return (
      <div className="flex flex-col items-center justify-center gap-2 py-8 text-center">
        <UserCircle className="size-10 text-muted-foreground/40" weight="duotone" />
        <p className="text-[10px] text-muted-foreground">
          프로필을 꾸며보세요
        </p>
      </div>
    );
  }

  if (variant === "mobile") {
    return (
      <div className="flex flex-col items-center gap-2">
        {/* Avatar + Name + Bio */}
        <div className="flex flex-col items-center gap-1 pb-2">
          {avatarUrl ? (
            <img src={avatarUrl} alt="" className="size-10 rounded-full object-cover" />
          ) : (
            <div className="flex size-10 items-center justify-center rounded-full bg-primary/10 text-sm font-bold text-primary">
              {nickname ? nickname[0] : "?"}
            </div>
          )}
          <p className="text-xs font-semibold">{nickname || "이름 없음"}</p>
          {bio && <p className="text-[10px] text-muted-foreground text-center">{bio}</p>}
        </div>

        {/* Links */}
        {links.length > 0 && (
          <div className="flex w-full flex-col gap-1.5">
            {links.map((link) => (
              <div key={link.id} className="flex h-7 items-center justify-center rounded-lg bg-muted/60 text-[10px] font-medium">
                {link.title || "제목 없음"}
              </div>
            ))}
          </div>
        )}

        {/* Socials */}
        {socials.length > 0 && (
          <div className="flex gap-2 pt-1">
            {socials.map((social) => {
              const Icon = SOCIAL_ICONS[social.platform] ?? Globe;
              return (
                <div key={social.id} className="flex size-6 items-center justify-center rounded-full bg-muted/60">
                  <Icon className="size-3 text-muted-foreground" />
                </div>
              );
            })}
          </div>
        )}
      </div>
    );
  }

  // Web variant: sidebar + main content
  return (
    <div className="grid grid-cols-[auto_1fr] gap-3">
      {/* Sidebar */}
      <div className="flex flex-col items-center gap-1.5 border-r border-border pr-3">
        {avatarUrl ? (
          <img src={avatarUrl} alt="" className="size-8 rounded-full object-cover" />
        ) : (
          <div className="flex size-8 items-center justify-center rounded-full bg-primary/10 text-xs font-bold text-primary">
            {nickname ? nickname[0] : "?"}
          </div>
        )}
        <p className="text-[9px] font-semibold">{nickname || "이름 없음"}</p>
        {bio && <p className="text-[8px] text-muted-foreground text-center">{bio}</p>}
        {socials.length > 0 && (
          <div className="flex gap-1.5 pt-1">
            {socials.map((social) => {
              const Icon = SOCIAL_ICONS[social.platform] ?? Globe;
              return (
                <div key={social.id} className="flex size-5 items-center justify-center rounded-full bg-muted/60">
                  <Icon className="size-2.5 text-muted-foreground" />
                </div>
              );
            })}
          </div>
        )}
      </div>

      {/* Main content */}
      <div className="flex flex-col gap-1.5">
        {links.map((link) => (
          <div key={link.id} className="flex h-6 items-center rounded-md bg-muted/60 px-2 text-[9px] font-medium">
            <Globe className="mr-1 size-3 text-muted-foreground" />
            {link.title || "제목 없음"}
          </div>
        ))}
        {links.length === 0 && (
          <p className="py-4 text-center text-[9px] text-muted-foreground">링크를 추가해보세요</p>
        )}
      </div>
    </div>
  );
}
```

- [ ] **Step 2: Verify build**

Run: `cd hddc-client-web && npx next build 2>&1 | tail -5`

- [ ] **Step 3: Commit**

```bash
git add src/components/dashboard/profile-preview-content.tsx
git commit -m "feat: add shared profile preview content renderer"
```

---

### Task 6: Profile Header Editor

**Files:**
- Create: `src/components/dashboard/profile-header-editor.tsx`

- [ ] **Step 1: Create the component**

Edits `avatarUrl`, `nickname`, `bio`. Uses `Input` from shadcn/ui.

```typescript
// src/components/dashboard/profile-header-editor.tsx
"use client";

import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { UserCircle } from "@phosphor-icons/react";
import type { ProfileData } from "@/lib/profile-types";

interface Props {
  profileData: ProfileData;
  updateProfile: (fields: Partial<Pick<ProfileData, "avatarUrl" | "nickname" | "bio">>) => void;
}

export function ProfileHeaderEditor({ profileData, updateProfile }: Props) {
  return (
    <section className="flex flex-col gap-4">
      <h3 className="text-sm font-semibold">프로필</h3>

      {/* Avatar preview + URL input */}
      <div className="flex items-center gap-4">
        {profileData.avatarUrl ? (
          <img src={profileData.avatarUrl} alt="" className="size-14 rounded-full object-cover border border-border" />
        ) : (
          <div className="flex size-14 items-center justify-center rounded-full bg-muted">
            <UserCircle className="size-8 text-muted-foreground" weight="duotone" />
          </div>
        )}
        <div className="flex-1">
          <Label htmlFor="avatar-url" className="text-xs text-muted-foreground">프로필 이미지 URL</Label>
          <Input
            id="avatar-url"
            type="url"
            placeholder="https://example.com/photo.jpg"
            value={profileData.avatarUrl ?? ""}
            onChange={(e) => updateProfile({ avatarUrl: e.target.value || null })}
            className="mt-1 h-8 text-sm"
          />
        </div>
      </div>

      {/* Nickname */}
      <div>
        <Label htmlFor="nickname" className="text-xs text-muted-foreground">닉네임</Label>
        <Input
          id="nickname"
          placeholder="나만의 이름"
          value={profileData.nickname}
          onChange={(e) => updateProfile({ nickname: e.target.value.slice(0, 20) })}
          className="mt-1 h-8 text-sm"
          maxLength={20}
        />
        <p className="mt-1 text-right text-[10px] text-muted-foreground">{profileData.nickname.length}/20</p>
      </div>

      {/* Bio */}
      <div>
        <Label htmlFor="bio" className="text-xs text-muted-foreground">한줄 소개</Label>
        <Input
          id="bio"
          placeholder="자기소개를 입력하세요"
          value={profileData.bio}
          onChange={(e) => updateProfile({ bio: e.target.value.slice(0, 80) })}
          className="mt-1 h-8 text-sm"
          maxLength={80}
        />
        <p className="mt-1 text-right text-[10px] text-muted-foreground">{profileData.bio.length}/80</p>
      </div>
    </section>
  );
}
```

- [ ] **Step 2: Commit**

```bash
git add src/components/dashboard/profile-header-editor.tsx
git commit -m "feat: add profile header editor section"
```

---

### Task 7: Link List Editor

**Files:**
- Create: `src/components/dashboard/link-list-editor.tsx`

- [ ] **Step 1: Create the component**

Add/remove/reorder links. Up/down arrow buttons for ordering. Max 20 links.

```typescript
// src/components/dashboard/link-list-editor.tsx
"use client";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { ArrowUp, ArrowDown, Trash, Plus } from "@phosphor-icons/react";
import type { ProfileLink } from "@/lib/profile-types";

interface Props {
  links: ProfileLink[];
  addLink: () => void;
  updateLink: (id: string, fields: Partial<Pick<ProfileLink, "title" | "url">>) => void;
  removeLink: (id: string) => void;
  moveLink: (id: string, direction: "up" | "down") => void;
}

export function LinkListEditor({ links, addLink, updateLink, removeLink, moveLink }: Props) {
  return (
    <section className="flex flex-col gap-3">
      <div className="flex items-center justify-between">
        <h3 className="text-sm font-semibold">링크</h3>
        <span className="text-[10px] text-muted-foreground">{links.length}/20</span>
      </div>

      {links.map((link, idx) => (
        <div key={link.id} className="flex gap-2 rounded-lg border border-border bg-card p-3">
          {/* Order buttons */}
          <div className="flex flex-col gap-0.5">
            <Button
              variant="ghost"
              size="icon-xs"
              onClick={() => moveLink(link.id, "up")}
              disabled={idx === 0}
              aria-label="위로 이동"
            >
              <ArrowUp className="size-3" />
            </Button>
            <Button
              variant="ghost"
              size="icon-xs"
              onClick={() => moveLink(link.id, "down")}
              disabled={idx === links.length - 1}
              aria-label="아래로 이동"
            >
              <ArrowDown className="size-3" />
            </Button>
          </div>

          {/* Inputs */}
          <div className="flex flex-1 flex-col gap-1.5">
            <Input
              placeholder="링크 제목"
              value={link.title}
              onChange={(e) => updateLink(link.id, { title: e.target.value.slice(0, 50) })}
              className="h-7 text-sm"
              maxLength={50}
            />
            <Input
              placeholder="https://..."
              type="url"
              value={link.url}
              onChange={(e) => updateLink(link.id, { url: e.target.value })}
              className="h-7 text-sm"
            />
          </div>

          {/* Delete */}
          <Button
            variant="ghost"
            size="icon-xs"
            onClick={() => removeLink(link.id)}
            className="text-muted-foreground hover:text-destructive"
            aria-label="링크 삭제"
          >
            <Trash className="size-3.5" />
          </Button>
        </div>
      ))}

      <Button
        variant="outline"
        className="h-9 w-full text-sm"
        onClick={addLink}
        disabled={links.length >= 20}
      >
        <Plus className="mr-1 size-4" />
        링크 추가
      </Button>
    </section>
  );
}
```

- [ ] **Step 2: Commit**

```bash
git add src/components/dashboard/link-list-editor.tsx
git commit -m "feat: add link list editor with reordering"
```

---

### Task 8: Social Editor

**Files:**
- Create: `src/components/dashboard/social-editor.tsx`

- [ ] **Step 1: Create the component**

Platform dropdown to add socials. List of added socials with URL input + remove button. Max 8.

```typescript
// src/components/dashboard/social-editor.tsx
"use client";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Trash } from "@phosphor-icons/react";
import {
  type SocialPlatform,
  type SocialLink,
  SOCIAL_PLATFORMS,
  SOCIAL_PLATFORM_LABELS,
} from "@/lib/profile-types";

interface Props {
  socials: SocialLink[];
  addSocial: (platform: SocialPlatform) => void;
  updateSocial: (id: string, url: string) => void;
  removeSocial: (id: string) => void;
}

export function SocialEditor({ socials, addSocial, updateSocial, removeSocial }: Props) {
  const usedPlatforms = new Set(socials.map((s) => s.platform));
  const availablePlatforms = SOCIAL_PLATFORMS.filter((p) => !usedPlatforms.has(p));

  return (
    <section className="flex flex-col gap-3">
      <div className="flex items-center justify-between">
        <h3 className="text-sm font-semibold">소셜 아이콘</h3>
        <span className="text-[10px] text-muted-foreground">{socials.length}/8</span>
      </div>

      {socials.map((social) => (
        <div key={social.id} className="flex items-center gap-2">
          <span className="w-20 shrink-0 text-xs font-medium">
            {SOCIAL_PLATFORM_LABELS[social.platform]}
          </span>
          <Input
            placeholder="URL 입력"
            value={social.url}
            onChange={(e) => updateSocial(social.id, e.target.value)}
            className="h-7 flex-1 text-sm"
          />
          <Button
            variant="ghost"
            size="icon-xs"
            onClick={() => removeSocial(social.id)}
            className="text-muted-foreground hover:text-destructive"
            aria-label="삭제"
          >
            <Trash className="size-3.5" />
          </Button>
        </div>
      ))}

      {availablePlatforms.length > 0 && socials.length < 8 && (
        <Select onValueChange={(val) => addSocial(val as SocialPlatform)}>
          <SelectTrigger className="h-9 text-sm">
            <SelectValue placeholder="소셜 추가..." />
          </SelectTrigger>
          <SelectContent>
            {availablePlatforms.map((platform) => (
              <SelectItem key={platform} value={platform}>
                {SOCIAL_PLATFORM_LABELS[platform]}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      )}
    </section>
  );
}
```

- [ ] **Step 2: Commit**

```bash
git add src/components/dashboard/social-editor.tsx
git commit -m "feat: add social icons editor"
```

---

### Task 9: Theme Editor

**Files:**
- Create: `src/components/dashboard/theme-editor.tsx`

Key: This does NOT use the global `setColorTheme` from `useColorTheme()`. It only updates `profileData.colorTheme` and `profileData.darkMode`, which the preview panel reads via a scoped `data-theme` wrapper.

- [ ] **Step 1: Create the component**

```typescript
// src/components/dashboard/theme-editor.tsx
"use client";

import { COLOR_THEMES, type ColorTheme } from "@/hooks/use-color-theme";
import { Sun, Moon } from "@phosphor-icons/react";

const THEME_COLORS: Record<ColorTheme, string> = {
  teal: "oklch(0.6 0.118 184.704)",
  orange: "oklch(0.646 0.222 41.116)",
  blue: "oklch(0.588 0.158 241.966)",
  violet: "oklch(0.541 0.281 293.009)",
  yellow: "oklch(0.852 0.199 91.936)",
  red: "oklch(0.577 0.245 27.325)",
};

const THEME_LABELS: Record<ColorTheme, string> = {
  teal: "틸",
  orange: "오렌지",
  blue: "블루",
  violet: "바이올렛",
  yellow: "옐로",
  red: "레드",
};

interface Props {
  colorTheme: ColorTheme;
  darkMode: boolean;
  setColorTheme: (theme: ColorTheme) => void;
  setDarkMode: (dark: boolean) => void;
}

export function ThemeEditor({ colorTheme, darkMode, setColorTheme, setDarkMode }: Props) {
  return (
    <section className="flex flex-col gap-4">
      <h3 className="text-sm font-semibold">테마</h3>

      {/* Color presets */}
      <div>
        <p className="mb-2 text-xs text-muted-foreground">컬러 프리셋</p>
        <div className="flex gap-2">
          {COLOR_THEMES.map((theme) => (
            <button
              key={theme}
              onClick={() => setColorTheme(theme)}
              className={`cursor-pointer flex flex-col items-center gap-1 rounded-lg p-2 transition-all ${
                colorTheme === theme
                  ? "bg-muted ring-2 ring-foreground ring-offset-2 ring-offset-background"
                  : "hover:bg-muted/50"
              }`}
              aria-label={THEME_LABELS[theme]}
            >
              <div
                className="size-7 rounded-full"
                style={{ backgroundColor: THEME_COLORS[theme] }}
              />
              <span className="text-[10px] text-muted-foreground">{THEME_LABELS[theme]}</span>
            </button>
          ))}
        </div>
      </div>

      {/* Dark mode toggle */}
      <div>
        <p className="mb-2 text-xs text-muted-foreground">모드</p>
        <div className="inline-flex items-center gap-1 rounded-full border border-border bg-muted/50 p-1">
          <button
            onClick={() => setDarkMode(false)}
            className={`cursor-pointer inline-flex items-center gap-1.5 rounded-full px-4 py-1.5 text-sm font-medium transition-all ${
              !darkMode
                ? "bg-background text-foreground shadow-sm"
                : "text-muted-foreground hover:text-foreground"
            }`}
          >
            <Sun className="size-4" />
            Light
          </button>
          <button
            onClick={() => setDarkMode(true)}
            className={`cursor-pointer inline-flex items-center gap-1.5 rounded-full px-4 py-1.5 text-sm font-medium transition-all ${
              darkMode
                ? "bg-background text-foreground shadow-sm"
                : "text-muted-foreground hover:text-foreground"
            }`}
          >
            <Moon className="size-4" />
            Dark
          </button>
        </div>
      </div>
    </section>
  );
}
```

- [ ] **Step 2: Commit**

```bash
git add src/components/dashboard/theme-editor.tsx
git commit -m "feat: add theme editor with scoped color presets"
```

---

## Chunk 3: Assembly (Editor + Preview + Layout + Page)

### Task 10: Profile Editor (Left Panel Assembly)

**Files:**
- Create: `src/components/dashboard/profile-editor.tsx`

- [ ] **Step 1: Create the component**

Assembles all editor sections into a scrollable left panel.

```typescript
// src/components/dashboard/profile-editor.tsx
"use client";

import { ProfileHeaderEditor } from "./profile-header-editor";
import { LinkListEditor } from "./link-list-editor";
import { SocialEditor } from "./social-editor";
import { ThemeEditor } from "./theme-editor";
import type { useProfileData } from "@/hooks/use-profile-data";

type ProfileActions = ReturnType<typeof useProfileData>;

export function ProfileEditor(props: ProfileActions) {
  return (
    <div className="flex flex-col gap-8 overflow-y-auto p-6">
      <ProfileHeaderEditor
        profileData={props.profileData}
        updateProfile={props.updateProfile}
      />

      <hr className="border-border" />

      <LinkListEditor
        links={props.profileData.links}
        addLink={props.addLink}
        updateLink={props.updateLink}
        removeLink={props.removeLink}
        moveLink={props.moveLink}
      />

      <hr className="border-border" />

      <SocialEditor
        socials={props.profileData.socials}
        addSocial={props.addSocial}
        updateSocial={props.updateSocial}
        removeSocial={props.removeSocial}
      />

      <hr className="border-border" />

      <ThemeEditor
        colorTheme={props.profileData.colorTheme}
        darkMode={props.profileData.darkMode}
        setColorTheme={props.setColorTheme}
        setDarkMode={props.setDarkMode}
      />
    </div>
  );
}
```

- [ ] **Step 2: Commit**

```bash
git add src/components/dashboard/profile-editor.tsx
git commit -m "feat: assemble profile editor left panel"
```

---

### Task 11: Profile Preview (Right Panel)

**Files:**
- Create: `src/components/dashboard/profile-preview.tsx`

- [ ] **Step 1: Create the component**

Mobile/Web toggle + device mockup frame containing ProfilePreviewContent.
Theme is scoped to the preview wrapper via `data-theme` + conditional `dark` class.

```typescript
// src/components/dashboard/profile-preview.tsx
"use client";

import { useState } from "react";
import { DeviceMobile, Desktop } from "@phosphor-icons/react";
import { PhoneMockup, BrowserMockup } from "@/components/device-mockup";
import { ProfilePreviewContent } from "./profile-preview-content";
import type { ProfileData } from "@/lib/profile-types";

interface Props {
  profileData: ProfileData;
}

export function ProfilePreview({ profileData }: Props) {
  const [view, setView] = useState<"mobile" | "web">("mobile");

  const themeAttr = profileData.colorTheme === "teal" ? undefined : profileData.colorTheme;
  const darkClass = profileData.darkMode ? "dark" : "";

  return (
    <div className="flex flex-col items-center gap-4 p-6">
      {/* Toggle */}
      <div className="flex items-center gap-3">
        <span className="text-xs font-semibold text-muted-foreground">미리보기</span>
        <div className="inline-flex items-center gap-1 rounded-full border border-border bg-muted/50 p-1">
          <button
            onClick={() => setView("mobile")}
            className={`cursor-pointer inline-flex items-center gap-1 rounded-full px-3 py-1 text-xs font-medium transition-all ${
              view === "mobile"
                ? "bg-primary text-primary-foreground shadow-sm"
                : "text-muted-foreground hover:text-foreground"
            }`}
          >
            <DeviceMobile className="size-3.5" />
            Mobile
          </button>
          <button
            onClick={() => setView("web")}
            className={`cursor-pointer inline-flex items-center gap-1 rounded-full px-3 py-1 text-xs font-medium transition-all ${
              view === "web"
                ? "bg-primary text-primary-foreground shadow-sm"
                : "text-muted-foreground hover:text-foreground"
            }`}
          >
            <Desktop className="size-3.5" />
            Web
          </button>
        </div>
      </div>

      {/* Mockup with scoped theme */}
      <div data-theme={themeAttr} className={darkClass}>
        {view === "mobile" ? (
          <PhoneMockup className="w-[200px]">
            <ProfilePreviewContent profileData={profileData} variant="mobile" />
          </PhoneMockup>
        ) : (
          <BrowserMockup className="w-[320px]">
            <ProfilePreviewContent profileData={profileData} variant="web" />
          </BrowserMockup>
        )}
      </div>
    </div>
  );
}
```

- [ ] **Step 2: Commit**

```bash
git add src/components/dashboard/profile-preview.tsx
git commit -m "feat: add profile preview panel with theme isolation"
```

---

### Task 12: Mobile Preview Overlay

**Files:**
- Create: `src/components/dashboard/mobile-preview-overlay.tsx`

- [ ] **Step 1: Create the component**

Fullscreen overlay that shows the same preview for mobile screens. Triggered by a button.

```typescript
// src/components/dashboard/mobile-preview-overlay.tsx
"use client";

import { useState } from "react";
import { X, Eye } from "@phosphor-icons/react";
import { Button } from "@/components/ui/button";
import { ProfilePreview } from "./profile-preview";
import type { ProfileData } from "@/lib/profile-types";

interface Props {
  profileData: ProfileData;
}

export function MobilePreviewButton({ profileData }: Props) {
  const [open, setOpen] = useState(false);

  return (
    <>
      <Button
        variant="outline"
        size="sm"
        className="lg:hidden"
        onClick={() => setOpen(true)}
      >
        <Eye className="mr-1 size-4" />
        미리보기
      </Button>

      {open && (
        <div className="fixed inset-0 z-50 flex flex-col bg-background">
          <div className="flex items-center justify-between border-b border-border px-4 py-3">
            <span className="text-sm font-semibold">미리보기</span>
            <Button variant="ghost" size="icon-sm" onClick={() => setOpen(false)}>
              <X className="size-4" />
            </Button>
          </div>
          <div className="flex flex-1 items-center justify-center overflow-auto">
            <ProfilePreview profileData={profileData} />
          </div>
        </div>
      )}
    </>
  );
}
```

- [ ] **Step 2: Commit**

```bash
git add src/components/dashboard/mobile-preview-overlay.tsx
git commit -m "feat: add mobile fullscreen preview overlay"
```

---

### Task 13: Dashboard Layout

**Files:**
- Create: `src/app/dashboard/layout.tsx`

- [ ] **Step 1: Create the layout**

TopBar with logo, save status indicator, and auth guard.
Auth guard: check localStorage for `hddc-auth` flag, redirect to `/auth/login` if missing.

```typescript
// src/app/dashboard/layout.tsx
"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";

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
      <div className="flex min-h-svh items-center justify-center">
        <p className="text-sm text-muted-foreground">로딩 중...</p>
      </div>
    );
  }

  return (
    <div className="flex min-h-svh flex-col">
      <header className="sticky top-0 z-40 border-b border-border bg-background/80 backdrop-blur-md">
        <nav className="mx-auto flex h-14 items-center justify-between px-4 max-w-6xl">
          <Link href="/" className="text-lg font-bold tracking-tight">
            핫딜닷쿨
          </Link>
        </nav>
      </header>
      <main className="flex flex-1 flex-col">{children}</main>
    </div>
  );
}
```

- [ ] **Step 2: Commit**

```bash
git add src/app/dashboard/layout.tsx
git commit -m "feat: add dashboard layout with auth guard"
```

---

### Task 14: Profile Edit Page

**Files:**
- Create: `src/app/dashboard/edit/page.tsx`

- [ ] **Step 1: Create the page**

Composes ProfileEditor (left) + ProfilePreview (right) side-by-side on desktop.
On mobile, shows editor + MobilePreviewButton. TopBar shows save status.

```typescript
// src/app/dashboard/edit/page.tsx
"use client";

import { useProfileData } from "@/hooks/use-profile-data";
import { ProfileEditor } from "@/components/dashboard/profile-editor";
import { ProfilePreview } from "@/components/dashboard/profile-preview";
import { MobilePreviewButton } from "@/components/dashboard/mobile-preview-overlay";
import { Button } from "@/components/ui/button";
import { FloppyDisk, Check, CircleNotch } from "@phosphor-icons/react";

export default function ProfileEditPage() {
  const profile = useProfileData();

  return (
    <div className="flex flex-1 flex-col">
      {/* Sub-header: save status + mobile preview btn */}
      <div className="flex items-center justify-between border-b border-border px-4 py-2">
        <div className="flex items-center gap-2">
          <Button
            variant="outline"
            size="sm"
            onClick={profile.saveNow}
            className="h-8 text-xs"
          >
            <FloppyDisk className="mr-1 size-3.5" />
            저장
          </Button>
          <span className="text-xs text-muted-foreground">
            {profile.saveStatus === "saving" && (
              <span className="inline-flex items-center gap-1">
                <CircleNotch className="size-3 animate-spin" />
                저장 중...
              </span>
            )}
            {profile.saveStatus === "saved" && (
              <span className="inline-flex items-center gap-1 text-primary">
                <Check className="size-3" />
                저장됨
              </span>
            )}
          </span>
        </div>
        <MobilePreviewButton profileData={profile.profileData} />
      </div>

      {/* Main content: editor + preview */}
      <div className="flex flex-1">
        {/* Left: Editor */}
        <div className="flex-1 overflow-y-auto">
          <ProfileEditor {...profile} />
        </div>

        {/* Right: Preview (desktop only) */}
        <div className="hidden border-l border-border lg:flex lg:w-[380px] lg:flex-col lg:items-center lg:justify-start lg:overflow-y-auto sticky top-14 h-[calc(100svh-3.5rem-2.75rem)]">
          <ProfilePreview profileData={profile.profileData} />
        </div>
      </div>
    </div>
  );
}
```

- [ ] **Step 2: Build and verify**

Run: `cd hddc-client-web && npx next build 2>&1 | tail -10`
Expected: Build succeeds with routes including `/dashboard/edit`.

- [ ] **Step 3: Commit**

```bash
git add src/app/dashboard/layout.tsx src/app/dashboard/edit/page.tsx
git commit -m "feat: add profile edit page with side-by-side preview"
```

---

### Task 15: Visual Verification & Polish

- [ ] **Step 1: Start dev server and test**

Run: `cd hddc-client-web && npx next dev`

Test flow:
1. Set auth flag: open browser console → `localStorage.setItem("hddc-auth", "true")`
2. Navigate to `http://localhost:3000/dashboard/edit`
3. Verify: editor form on left, preview on right (desktop)
4. Add a nickname → preview updates in real-time
5. Add links → preview shows them
6. Add social icons → preview shows icons
7. Change theme → preview re-themes (dashboard stays unchanged)
8. Toggle Mobile/Web in preview
9. Resize to mobile width → preview panel hides, "미리보기" button appears
10. Click "미리보기" → fullscreen overlay
11. Click "저장" → status shows "저장됨"
12. Refresh page → data persists from localStorage

- [ ] **Step 2: Fix any visual issues found during testing**

- [ ] **Step 3: Final build check**

Run: `cd hddc-client-web && npx next build 2>&1 | tail -5`

- [ ] **Step 4: Commit any fixes**

```bash
git add -A
git commit -m "fix: polish profile editor UI after visual review"
```

---

## Summary

| Chunk | Tasks | What it delivers |
|-------|-------|-----------------|
| 1: Foundation | Tasks 1-3 | Types, data hook, mockup refactor |
| 2: Editor Sections | Tasks 4-9 | Select component, preview renderer, 4 editor sections |
| 3: Assembly | Tasks 10-15 | Full page assembly, layout, auth guard, visual QA |
