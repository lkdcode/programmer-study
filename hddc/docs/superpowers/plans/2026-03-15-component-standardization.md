# Component Standardization — Phase A Implementation Plan

> **For agentic workers:** REQUIRED: Use superpowers:subagent-driven-development (if subagents available) or superpowers:executing-plans to implement this plan. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** raw `<button>` 및 수동 드롭다운을 표준 컴포넌트로 통합하여 UI 일관성 확보

**Architecture:** 기존 shadcn/ui + CVA 패턴을 따라 ToggleGroup과 Button을 확장하고, ColorPickerPopover / ConfirmDialog 신규 컴포넌트를 생성. 에디터 파일들은 import만 교체하는 방식으로 최소 침습 수정.

**Tech Stack:** Next.js 16, React 19, TypeScript, Tailwind CSS 4, shadcn/ui (Radix UI), CVA, react-colorful

**Spec:** `docs/superpowers/specs/2026-03-15-component-standardization-design.md`

---

## Chunk 1: Core Component Extensions

### Task 1: ToggleGroup variant 확장

**Files:**
- Modify: `src/components/ui/toggle-group.tsx`

- [ ] **Step 1: ToggleGroup에 variant prop + renderItem prop 추가**

```tsx
// src/components/ui/toggle-group.tsx — 전체 교체

import { cva, type VariantProps } from "class-variance-authority"
import type { Icon } from "@phosphor-icons/react"

import { cn } from "@/lib/utils"

const toggleGroupVariants = cva(
  "inline-flex items-center",
  {
    variants: {
      variant: {
        pill: "gap-1 rounded-full border border-border bg-muted/50 p-1",
        square: "gap-1",
      },
      size: {
        default: "",
        sm: "",
      },
    },
    defaultVariants: {
      variant: "pill",
      size: "default",
    },
  }
)

const toggleGroupItemVariants = cva(
  "cursor-pointer inline-flex items-center font-medium transition-all",
  {
    variants: {
      variant: {
        pill: "rounded-full",
        square: "rounded-md",
      },
      size: {
        default: "",
        sm: "",
      },
      active: {
        true: "",
        false: "",
      },
    },
    compoundVariants: [
      // pill + default
      { variant: "pill", size: "default", className: "gap-1.5 px-4 py-1.5 text-sm" },
      { variant: "pill", size: "sm", className: "gap-1 px-3 py-1 text-xs" },
      // square + default
      { variant: "square", size: "default", className: "gap-1 px-2 py-1 text-[11px]" },
      { variant: "square", size: "sm", className: "gap-0.5 px-1.5 py-0.5 text-[10px]" },
      // active states
      { variant: "pill", active: true, className: "bg-primary text-primary-foreground shadow-sm" },
      { variant: "pill", active: false, className: "text-muted-foreground hover:text-foreground" },
      { variant: "square", active: true, className: "bg-primary text-primary-foreground" },
      { variant: "square", active: false, className: "bg-muted/60 text-muted-foreground hover:bg-muted" },
    ],
    defaultVariants: {
      variant: "pill",
      size: "default",
      active: false,
    },
  }
)

interface ToggleGroupOption<T extends string> {
  value: T
  label: string
  icon?: Icon
}

interface ToggleGroupProps<T extends string>
  extends VariantProps<typeof toggleGroupVariants> {
  value: T
  onValueChange: (value: T) => void
  options: ToggleGroupOption<T>[]
  className?: string
  renderItem?: (option: ToggleGroupOption<T>, isActive: boolean) => React.ReactNode
}

function ToggleGroup<T extends string>({
  value,
  onValueChange,
  options,
  variant = "pill",
  size,
  className,
  renderItem,
}: ToggleGroupProps<T>) {
  return (
    <div
      data-slot="toggle-group"
      className={cn(toggleGroupVariants({ variant, size, className }))}
    >
      {options.map((option) => {
        const IconComp = option.icon
        const isActive = value === option.value
        return (
          <button
            key={option.value}
            type="button"
            data-state={isActive ? "on" : "off"}
            onClick={() => onValueChange(option.value)}
            className={cn(
              renderItem
                ? ""
                : toggleGroupItemVariants({ variant, size, active: isActive }),
            )}
          >
            {renderItem ? (
              renderItem(option, isActive)
            ) : (
              <>
                {IconComp && <IconComp className="size-4" />}
                {option.label}
              </>
            )}
          </button>
        )
      })}
    </div>
  )
}

export { ToggleGroup, toggleGroupVariants, toggleGroupItemVariants }
export type { ToggleGroupProps, ToggleGroupOption }
```

- [ ] **Step 2: 빌드 확인**

Run: `cd /Users/kiducklee/IdeaProjects/programmer-study/hddc/hddc-client-web && pnpm build 2>&1 | tail -5`
Expected: Build succeeds (기존 사용처는 variant default=pill이므로 하위 호환)

- [ ] **Step 3: Commit**

```bash
git add src/components/ui/toggle-group.tsx
git commit -m "feat: extend ToggleGroup with variant (pill/square) and renderItem props"
```

---

### Task 2: Button active prop 추가

**Files:**
- Modify: `src/components/ui/button.tsx`

- [ ] **Step 1: Button에 active prop 추가**

`button.tsx`의 Button function에 `active` prop을 추가하고, `data-active` attribute로 전달:

```tsx
// Button function signature 변경 (기존 props에 active 추가)
function Button({
  className,
  variant = "default",
  size = "default",
  asChild = false,
  active,
  ...props
}: React.ComponentProps<"button"> &
  VariantProps<typeof buttonVariants> & {
    asChild?: boolean
    active?: boolean
  }) {
  const Comp = asChild ? Slot.Root : "button"

  return (
    <Comp
      data-slot="button"
      data-variant={variant}
      data-size={size}
      data-active={active ? "true" : undefined}
      className={cn(
        buttonVariants({ variant, size, className }),
        active && "ring-2 ring-foreground ring-offset-2 ring-offset-background",
      )}
      {...props}
    />
  )
}
```

- [ ] **Step 2: 빌드 확인**

Run: `cd /Users/kiducklee/IdeaProjects/programmer-study/hddc/hddc-client-web && pnpm build 2>&1 | tail -5`
Expected: Build succeeds

- [ ] **Step 3: Commit**

```bash
git add src/components/ui/button.tsx
git commit -m "feat: add active prop to Button for selected state ring"
```

---

### Task 3: ColorPickerPopover 신규 생성

**Files:**
- Create: `src/components/ui/color-picker-popover.tsx`

- [ ] **Step 1: 컴포넌트 작성**

```tsx
// src/components/ui/color-picker-popover.tsx
"use client";

import { useState, useEffect } from "react";
import { HexColorPicker, HexColorInput } from "react-colorful";
import { Palette } from "@phosphor-icons/react";
import { Button } from "@/components/ui/button";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { cn } from "@/lib/utils";

interface ColorPickerPopoverProps {
  color: string;
  onChange: (color: string) => void;
  triggerLabel?: string;
  triggerIcon?: React.ReactNode;
  width?: string;
  align?: "start" | "center" | "end";
  triggerClassName?: string;
}

export function ColorPickerPopover({
  color,
  onChange,
  triggerLabel = "직접 선택",
  triggerIcon,
  width = "240px",
  align = "start",
  triggerClassName,
}: ColorPickerPopoverProps) {
  const [open, setOpen] = useState(false);
  const [tempColor, setTempColor] = useState(color);

  useEffect(() => {
    if (open) setTempColor(color);
  }, [open, color]);

  return (
    <Popover open={open} onOpenChange={setOpen}>
      <PopoverTrigger asChild>
        <Button
          variant="outline"
          className={cn(
            "mt-2 w-full justify-center gap-1.5 text-xs font-medium",
            triggerClassName,
          )}
        >
          {triggerIcon ?? <Palette className="size-4" />}
          {triggerLabel}
        </Button>
      </PopoverTrigger>
      <PopoverContent className="p-3" align={align} style={{ width }}>
        <div className="flex flex-col items-center gap-3">
          <HexColorPicker
            color={tempColor}
            onChange={setTempColor}
            style={{ width: "100%", height: "160px" }}
          />
          <div className="flex w-full items-center gap-2">
            <div
              className="size-8 shrink-0 rounded-lg border border-border"
              style={{ backgroundColor: tempColor }}
            />
            <div className="flex items-center gap-1 rounded-md border border-input px-2 py-1 text-sm">
              <span className="text-muted-foreground">#</span>
              <HexColorInput
                color={tempColor}
                onChange={setTempColor}
                className="w-[5.5rem] bg-transparent uppercase outline-none"
                prefixed={false}
              />
            </div>
          </div>
          <Button
            size="sm"
            className="w-full"
            onClick={() => {
              onChange(tempColor);
              setOpen(false);
            }}
          >
            적용
          </Button>
        </div>
      </PopoverContent>
    </Popover>
  );
}
```

- [ ] **Step 2: 빌드 확인**

Run: `cd /Users/kiducklee/IdeaProjects/programmer-study/hddc/hddc-client-web && pnpm build 2>&1 | tail -5`
Expected: Build succeeds

- [ ] **Step 3: Commit**

```bash
git add src/components/ui/color-picker-popover.tsx
git commit -m "feat: add ColorPickerPopover component"
```

---

### Task 4: ConfirmDialog 신규 생성

**Files:**
- Create: `src/components/ui/confirm-dialog.tsx`

- [ ] **Step 1: shadcn AlertDialog 설치 확인**

Run: `ls /Users/kiducklee/IdeaProjects/programmer-study/hddc/hddc-client-web/src/components/ui/alert-dialog.tsx 2>/dev/null || echo "NOT_FOUND"`

만약 NOT_FOUND이면:
Run: `cd /Users/kiducklee/IdeaProjects/programmer-study/hddc/hddc-client-web && npx shadcn@latest add alert-dialog -y`

- [ ] **Step 2: ConfirmDialog 컴포넌트 작성**

```tsx
// src/components/ui/confirm-dialog.tsx
"use client";

import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import { buttonVariants } from "@/components/ui/button";
import { cn } from "@/lib/utils";

interface ConfirmDialogProps {
  open: boolean;
  onOpenChange: (open: boolean) => void;
  title: string;
  description: string;
  onConfirm: () => void;
  confirmLabel?: string;
  cancelLabel?: string;
  variant?: "default" | "destructive";
}

export function ConfirmDialog({
  open,
  onOpenChange,
  title,
  description,
  onConfirm,
  confirmLabel = "확인",
  cancelLabel = "취소",
  variant = "destructive",
}: ConfirmDialogProps) {
  return (
    <AlertDialog open={open} onOpenChange={onOpenChange}>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle>{title}</AlertDialogTitle>
          <AlertDialogDescription>{description}</AlertDialogDescription>
        </AlertDialogHeader>
        <AlertDialogFooter>
          <AlertDialogCancel>{cancelLabel}</AlertDialogCancel>
          <AlertDialogAction
            className={cn(buttonVariants({ variant }))}
            onClick={onConfirm}
          >
            {confirmLabel}
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}
```

- [ ] **Step 3: 빌드 확인**

Run: `cd /Users/kiducklee/IdeaProjects/programmer-study/hddc/hddc-client-web && pnpm build 2>&1 | tail -5`
Expected: Build succeeds

- [ ] **Step 4: Commit**

```bash
git add src/components/ui/alert-dialog.tsx src/components/ui/confirm-dialog.tsx
git commit -m "feat: add ConfirmDialog component based on AlertDialog"
```

---

## Chunk 2: Editor File Migrations

### Task 5: link-list-editor.tsx — ToggleGroup 적용

**Files:**
- Modify: `src/components/dashboard/link-list-editor.tsx`

- [ ] **Step 1: import 추가 및 셀렉터 교체**

상단 import에 추가:
```tsx
import { ToggleGroup, type ToggleGroupOption } from "@/components/ui/toggle-group";
```

기존 `List, GridFour, SquaresFour` import에서 사용하되, 옵션 배열을 `ToggleGroupOption` 형태로 변환.

LAYOUTS 배열을 `ToggleGroupOption<LinkLayout>[]`로 타입 변경:
```tsx
const LAYOUTS: ToggleGroupOption<LinkLayout>[] = [
  { value: "list", label: "리스트", icon: List },
  { value: "grid-2", label: "2열", icon: GridFour },
  { value: "grid-3", label: "3열", icon: SquaresFour },
];
```

LINK_STYLES를 `ToggleGroupOption<LinkStyle>[]`로:
```tsx
const LINK_STYLES: ToggleGroupOption<LinkStyle>[] = [
  { value: "fill", label: "채움" },
  { value: "outline", label: "아웃라인" },
  { value: "shadow", label: "그림자" },
  { value: "rounded", label: "라운드" },
  { value: "pill", label: "캡슐" },
];
```

LINK_ANIMATIONS를 `ToggleGroupOption<LinkAnimation>[]`로:
```tsx
const LINK_ANIMATIONS: ToggleGroupOption<LinkAnimation>[] = [
  { value: "none", label: "없음" },
  { value: "fade-in", label: "페이드" },
  { value: "slide-up", label: "슬라이드" },
  { value: "scale", label: "스케일" },
  { value: "stagger", label: "순차" },
];
```

- [ ] **Step 2: Layout selector JSX 교체 (line 128-146)**

```tsx
{/* Layout selector */}
<div className="flex items-center gap-1">
  <span className="mr-1 text-[11px] text-muted-foreground">배치</span>
  <ToggleGroup
    variant="square"
    size="default"
    value={linkLayout}
    onValueChange={setLinkLayout}
    options={LAYOUTS}
  />
</div>
```

- [ ] **Step 3: Style selector JSX 교체 (line 148-165)**

```tsx
{/* Style selector */}
<div className="flex items-center gap-1">
  <span className="mr-1 text-[11px] text-muted-foreground">스타일</span>
  <ToggleGroup
    variant="square"
    size="default"
    value={linkStyle}
    onValueChange={setLinkStyle}
    options={LINK_STYLES}
  />
</div>
```

- [ ] **Step 4: Animation selector JSX 교체 (line 167-184)**

```tsx
{/* Animation selector */}
<div className="flex items-center gap-1">
  <span className="mr-1 text-[11px] text-muted-foreground">애니메이션</span>
  <ToggleGroup
    variant="square"
    size="default"
    value={linkAnimation}
    onValueChange={setLinkAnimation}
    options={LINK_ANIMATIONS}
  />
</div>
```

- [ ] **Step 5: 빌드 확인**

Run: `cd /Users/kiducklee/IdeaProjects/programmer-study/hddc/hddc-client-web && pnpm build 2>&1 | tail -5`

- [ ] **Step 6: Commit**

```bash
git add src/components/dashboard/link-list-editor.tsx
git commit -m "refactor: replace raw buttons with ToggleGroup in link-list-editor"
```

---

### Task 6: theme-editor.tsx — ToggleGroup + ColorPickerPopover + RemoveButton + Button

**Files:**
- Modify: `src/components/dashboard/theme-editor.tsx`

이 파일은 가장 많은 raw button(11개)을 포함하므로 변경이 큽니다.

- [ ] **Step 1: import 추가**

```tsx
import { Button } from "@/components/ui/button";
import { RemoveButton } from "@/components/ui/remove-button";
import { ColorPickerPopover } from "@/components/ui/color-picker-popover";
import { ToggleGroup, type ToggleGroupOption } from "@/components/ui/toggle-group";
```

- [ ] **Step 2: 배경색 "초기화" 버튼 → RemoveButton (line 279-285)**

교체 전:
```tsx
<button onClick={() => setBackgroundColor(null)} className="inline-flex cursor-pointer items-center gap-0.5 text-[11px] text-destructive hover:opacity-70">
  <X className="size-3" weight="bold" />
  초기화
</button>
```

교체 후:
```tsx
<RemoveButton label="초기화" onClick={() => setBackgroundColor(null)} />
```

- [ ] **Step 3: 배경색 커스텀 피커 → ColorPickerPopover (line 306-350)**

배경색 Popover 블록 전체를 교체:

```tsx
<ColorPickerPopover
  color={backgroundColor || "#ffffff"}
  onChange={(color) => setBackgroundColor(color)}
/>
```

이로 인해 `bgPickerOpen`, `tempBgColor` state 제거 가능.

- [ ] **Step 4: 프리셋 커스텀 피커 내부 "적용" 버튼 → Button (line 259-268)**

```tsx
<Button
  size="sm"
  className="w-full"
  onClick={() => {
    setCustomColors(tempPrimary, tempSecondary);
    setColorTheme("custom");
    setPresetPickerOpen(false);
  }}
>
  적용
</Button>
```

프리셋 커스텀 피커의 "직접 선택" 트리거 (line 170-189)도 Button으로 교체:
```tsx
<PopoverTrigger asChild>
  <Button
    variant="outline"
    active={colorTheme === "custom"}
    className="mt-2 w-full justify-center gap-1.5 text-xs font-medium"
  >
    {colorTheme === "custom" && customPrimaryColor ? (
      <div
        className="size-4 rounded-full border border-border"
        style={{
          background: `linear-gradient(135deg, ${customPrimaryColor} 50%, ${customSecondaryColor || autoSecondary(customPrimaryColor)} 50%)`,
        }}
      />
    ) : (
      <Palette className="size-4" />
    )}
    직접 선택
  </Button>
</PopoverTrigger>
```

- [ ] **Step 5: light/dark 모드 토글 → ToggleGroup (line 353-362)**

**주의:** 현재 light/dark 토글은 active 상태에 `bg-background text-foreground`를 사용하지만, ToggleGroup pill의 active는 `bg-primary text-primary-foreground`입니다. 이 차이를 유지하려면 ToggleGroup의 pill variant에 `neutral` active 스타일을 추가하거나, 인라인으로 오버라이드해야 합니다. 여기서는 renderItem을 사용하여 기존 스타일을 정확히 재현합니다:

```tsx
<div>
  <p className="mb-2 text-xs text-muted-foreground">모드</p>
  <ToggleGroup
    variant="pill"
    value={darkMode ? "dark" : "light"}
    onValueChange={(v: "light" | "dark") => setDarkMode(v === "dark")}
    options={[
      { value: "light" as const, label: "Light", icon: Sun },
      { value: "dark" as const, label: "Dark", icon: Moon },
    ]}
    renderItem={(option, isActive) => {
      const IconComp = option.icon!;
      return (
        <div className={cn(
          "inline-flex items-center gap-1.5 rounded-full px-4 py-1.5 text-sm font-medium transition-all",
          isActive ? "bg-background text-foreground shadow-sm" : "text-muted-foreground hover:text-foreground",
        )}>
          <IconComp className="size-4" />
          {option.label}
        </div>
      );
    }}
  />
</div>
```

- [ ] **Step 6: font 셀렉터 → ToggleGroup with renderItem (line 366-389)**

font 옵션 배열을 정의하고 ToggleGroup으로 교체:

```tsx
const FONT_OPTIONS: ToggleGroupOption<FontFamily>[] = (Object.keys(FONT_FAMILY_LABELS) as FontFamily[]).map((font) => ({
  value: font,
  label: FONT_FAMILY_LABELS[font],
}));
```

JSX:
```tsx
<div>
  <p className="mb-2 text-xs text-muted-foreground">폰트</p>
  <ToggleGroup
    variant="square"
    value={fontFamily}
    onValueChange={setFontFamily}
    options={FONT_OPTIONS}
    className="grid grid-cols-2 gap-1.5"
    renderItem={(option, isActive) => (
      <div className={cn("flex w-full flex-col rounded-lg px-3 py-2 text-left", isActive && "bg-muted ring-2 ring-foreground ring-offset-2 ring-offset-background")}>
        <span className="text-xs font-medium">{option.label}</span>
        <span className="mt-0.5 block text-[10px] text-muted-foreground" style={{ fontFamily: FONT_FAMILY_CSS[option.value] }}>
          가나다 ABC 123
        </span>
      </div>
    )}
  />
</div>
```

**주의:** font 셀렉터는 ToggleGroup의 기본 active 스타일 대신 renderItem 내부에서 직접 스타일을 제어합니다. ToggleGroup item의 기본 배경/패딩을 제거하기 위해 item button에 추가 className을 전달하는 방식이 필요할 수 있습니다. 이 경우 renderItem 사용 시 item의 기본 스타일을 `bg-transparent p-0`으로 오버라이드합니다 — ToggleGroup에서 `renderItem` 사용 시 item variant 스타일을 제거하는 로직 추가가 필요.

ToggleGroup에서 `renderItem` prop이 존재할 때 item className에서 active/size 관련 스타일을 제거:
```tsx
className={cn(
  renderItem
    ? "bg-transparent p-0"
    : toggleGroupItemVariants({ variant, size, active: isActive })
)}
```

- [ ] **Step 7: 테마 프리셋 그리드 버튼 → Button (line 137-156)**

```tsx
{PRESET_THEMES.map((theme) => (
  <Button
    key={theme}
    variant="ghost"
    active={colorTheme === theme}
    onClick={() => setColorTheme(theme)}
    aria-label={THEME_LABELS[theme]}
    className="!h-auto flex flex-col items-center gap-1 rounded-lg p-2"
  >
    <div
      className={cn("size-7 rounded-full", (theme === "default" || theme === "white") && "border border-border")}
      style={{
        background: `linear-gradient(135deg, ${THEME_COLORS[theme].primary} 50%, ${THEME_COLORS[theme].secondary} 50%)`,
      }}
    />
    <span className="text-[10px] text-muted-foreground">{THEME_LABELS[theme]}</span>
  </Button>
))}
```

- [ ] **Step 8: 불필요한 state/import 정리**

제거 가능한 state:
- `bgPickerOpen` — ColorPickerPopover 내부에서 관리
- `tempBgColor` — ColorPickerPopover 내부에서 관리

제거 가능한 import:
- `X` from `@phosphor-icons/react` (RemoveButton이 내부적으로 사용)

- [ ] **Step 9: 빌드 확인**

Run: `cd /Users/kiducklee/IdeaProjects/programmer-study/hddc/hddc-client-web && pnpm build 2>&1 | tail -5`

- [ ] **Step 10: Commit**

```bash
git add src/components/dashboard/theme-editor.tsx
git commit -m "refactor: standardize theme-editor with ToggleGroup, ColorPickerPopover, RemoveButton, Button"
```

---

### Task 7: profile-header-editor.tsx — RemoveButton 적용

**Files:**
- Modify: `src/components/dashboard/profile-header-editor.tsx`

- [ ] **Step 1: import 추가**

```tsx
import { RemoveButton } from "@/components/ui/remove-button";
```

`X` import 제거 (더 이상 직접 사용 안 함).

- [ ] **Step 2: "배경 제거" 버튼 교체 (line 158-164)**

```tsx
<RemoveButton
  label="배경 제거"
  onClick={(e) => { e.stopPropagation(); updateProfile({ backgroundUrl: null }); }}
/>
```

- [ ] **Step 3: "사진 제거" 버튼 교체 (line 195-201)**

```tsx
<RemoveButton
  label="사진 제거"
  onClick={(e) => { e.stopPropagation(); updateProfile({ avatarUrl: null }); }}
/>
```

- [ ] **Step 4: 빌드 확인 + Commit**

```bash
pnpm build 2>&1 | tail -5
git add src/components/dashboard/profile-header-editor.tsx
git commit -m "refactor: use RemoveButton in profile-header-editor"
```

---

### Task 8: profile-preview.tsx — ToggleGroup 적용

**Files:**
- Modify: `src/components/dashboard/profile-preview.tsx`

- [ ] **Step 1: import 추가**

```tsx
import { ToggleGroup } from "@/components/ui/toggle-group";
import { DeviceMobile, Desktop } from "@phosphor-icons/react";
```

- [ ] **Step 2: mobile/web 토글 교체 (line 50-73)**

```tsx
<ToggleGroup
  variant="pill"
  size="sm"
  value={view}
  onValueChange={setView}
  options={[
    { value: "mobile" as const, label: "Mobile", icon: DeviceMobile },
    { value: "web" as const, label: "Web", icon: Desktop },
  ]}
/>
```

기존 `<div className="inline-flex items-center gap-1 rounded-full...">` 블록 전체 제거.

- [ ] **Step 3: 빌드 확인 + Commit**

```bash
pnpm build 2>&1 | tail -5
git add src/components/dashboard/profile-preview.tsx
git commit -m "refactor: use ToggleGroup pill in profile-preview"
```

---

## Chunk 3: Remaining File Migrations

### Task 9: color-theme-picker.tsx — Popover 전환

**Files:**
- Modify: `src/components/color-theme-picker.tsx`

- [ ] **Step 1: Popover import 추가, useRef/useEffect 제거, useState 유지**

```tsx
"use client";

import { useState } from "react";
import { useColorTheme, COLOR_THEMES, type ColorTheme } from "@/hooks/use-color-theme";
import { Button } from "@/components/ui/button";
import { ColorSwatch } from "@/components/ui/color-swatch";
import { Palette } from "@phosphor-icons/react";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
```

`useRef`, `useEffect` import 제거. `useState`는 유지 (controlled Popover에 필요).
**기존 로컬 상수 `PRESET_THEMES`, `THEME_COLORS`, `THEME_LABELS`는 그대로 유지.**

- [ ] **Step 2: 컴포넌트 교체 (controlled Popover로 선택 시 닫히도록)**

```tsx
export function ColorThemePicker() {
  const { colorTheme, setColorTheme } = useColorTheme();
  const [open, setOpen] = useState(false);

  return (
    <Popover open={open} onOpenChange={setOpen}>
      <PopoverTrigger asChild>
        <Button variant="ghost" size="icon" aria-label="색상 테마 선택">
          <Palette className="size-4" />
        </Button>
      </PopoverTrigger>
      <PopoverContent className="w-auto p-2.5" align="end">
        <div className="flex gap-2">
          {PRESET_THEMES.map((theme) => (
            <ColorSwatch
              key={theme}
              color={THEME_COLORS[theme as Exclude<ColorTheme, "custom">]}
              selected={colorTheme === theme}
              bordered={theme === "default" || theme === "white"}
              onClick={() => {
                setColorTheme(theme);
                setOpen(false);
              }}
              label={THEME_LABELS[theme as Exclude<ColorTheme, "custom">]}
            />
          ))}
        </div>
      </PopoverContent>
    </Popover>
  );
}
```

- [ ] **Step 3: 빌드 확인 + Commit**

```bash
pnpm build 2>&1 | tail -5
git add src/components/color-theme-picker.tsx
git commit -m "refactor: replace manual dropdown with Popover in ColorThemePicker"
```

---

### Task 10: fab.tsx — Popover 전환

**Files:**
- Modify: `src/components/fab.tsx`

- [ ] **Step 1: Popover import, useRef/useEffect 제거**

```tsx
"use client";

import { Question, X, ChatCircle, BookOpen, Lightbulb, Megaphone } from "@phosphor-icons/react";
import { cn } from "@/lib/utils";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
```

`useState`, `useEffect`, `useRef` 제거.

- [ ] **Step 2: 컴포넌트 교체**

```tsx
export function Fab() {
  return (
    <div className="fixed bottom-6 right-6 z-[60]">
      <Popover>
        <PopoverContent
          side="top"
          align="end"
          className="mb-2 min-w-[200px] rounded-xl p-1.5"
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
        </PopoverContent>
        <PopoverTrigger asChild>
          <button
            aria-label="도움말 메뉴"
            className="flex size-[52px] cursor-pointer items-center justify-center rounded-full bg-primary text-primary-foreground shadow-lg transition-transform duration-150 hover:scale-105 active:scale-95"
          >
            <Question className="size-6" weight="bold" />
          </button>
        </PopoverTrigger>
      </Popover>
    </div>
  );
}
```

**주의:** FAB 버튼은 커스텀 디자인(52px 원형 primary)이므로 `<Button>` 대신 raw button을 유지합니다 — 스펙에서 scope 제외된 FAB 트리거 버튼입니다. X/Question 토글 아이콘은 Popover의 open state를 직접 제어하기 어렵습니다. Radix Popover는 `data-state="open"` attribute를 트리거에 추가하므로, CSS로 처리:

```tsx
<PopoverTrigger asChild>
  <button
    aria-label="도움말 메뉴"
    aria-haspopup="menu"
    className="group flex size-[52px] cursor-pointer items-center justify-center rounded-full bg-primary text-primary-foreground shadow-lg transition-transform duration-150 hover:scale-105 active:scale-95"
  >
    <Question className="size-6 group-data-[state=open]:hidden" weight="bold" />
    <X className="size-6 hidden group-data-[state=open]:block" weight="bold" />
  </button>
</PopoverTrigger>
```

- [ ] **Step 3: 빌드 확인 + Commit**

```bash
pnpm build 2>&1 | tail -5
git add src/components/fab.tsx
git commit -m "refactor: replace manual dropdown with Popover in FAB"
```

---

### Task 11: edit/page.tsx — ConfirmDialog 적용

**Files:**
- Modify: `src/app/(app)/dashboard/edit/page.tsx`

- [ ] **Step 1: import 추가 + state 추가**

```tsx
import { ConfirmDialog } from "@/components/ui/confirm-dialog";
```

컴포넌트 내부에:
```tsx
const [confirmResetOpen, setConfirmResetOpen] = useState(false);
```

`useState` import 추가 필요 — 기존 `import { useEffect } from "react"`를 `import { useState, useEffect } from "react"`로 변경.

- [ ] **Step 2: window.confirm → ConfirmDialog 교체 (line 55-58)**

SidebarButton onClick 변경:
```tsx
<SidebarButton
  icon={<Trash className="size-4" />}
  label="초기화"
  onClick={() => setConfirmResetOpen(true)}
  variant="destructive"
/>
```

EditFocusProvider 닫는 태그 직전에 ConfirmDialog 추가:
```tsx
<ConfirmDialog
  open={confirmResetOpen}
  onOpenChange={setConfirmResetOpen}
  title="프로필 초기화"
  description="모든 프로필 데이터가 삭제됩니다. 초기화하시겠습니까?"
  onConfirm={() => {
    profile.resetProfile();
    setConfirmResetOpen(false);
  }}
  confirmLabel="초기화"
  variant="destructive"
/>
```

- [ ] **Step 3: 빌드 확인 + Commit**

```bash
pnpm build 2>&1 | tail -5
git add src/app/(app)/dashboard/edit/page.tsx
git commit -m "refactor: replace window.confirm with ConfirmDialog"
```

---

### Task 12: auth/layout.tsx — 캐러셀 버튼 Button 교체

**Files:**
- Modify: `src/app/(public)/auth/layout.tsx`

- [ ] **Step 1: Button import 추가**

```tsx
import { Button } from "@/components/ui/button";
```

- [ ] **Step 2: 캐러셀 버튼 교체 (line 81-92, 104-115)**

좌측:
```tsx
<Button
  variant="ghost"
  size="icon"
  onClick={goPrev}
  className={cn(
    "rounded-full transition-colors duration-700",
    panel.btnBg, panel.btnText, panel.btnHoverBg, panel.btnHoverText,
  )}
>
  <CaretLeft className="size-4" />
</Button>
```

우측:
```tsx
<Button
  variant="ghost"
  size="icon"
  onClick={goNext}
  className={cn(
    "rounded-full transition-colors duration-700",
    panel.btnBg, panel.btnText, panel.btnHoverBg, panel.btnHoverText,
  )}
>
  <CaretRight className="size-4" />
</Button>
```

- [ ] **Step 3: 빌드 확인 + Commit**

```bash
pnpm build 2>&1 | tail -5
git add src/app/(public)/auth/layout.tsx
git commit -m "refactor: use Button component for carousel navigation"
```

---

### Task 13: signup/page.tsx — 링크 버튼 Button 교체

**Files:**
- Modify: `src/app/(public)/auth/signup/page.tsx`

- [ ] **Step 1: 이용약관/개인정보 링크 버튼 교체 (line 243-257)**

**참고:** `Button`은 이미 line 6에서 import되어 있으므로 추가 import 불필요.

```tsx
<Button
  type="button"
  variant="link"
  className="h-auto p-0 text-sm text-foreground"
  onClick={() => setTermsOpen(true)}
>
  이용약관
</Button>{" "}
및{" "}
<Button
  type="button"
  variant="link"
  className="h-auto p-0 text-sm text-foreground"
  onClick={() => setPrivacyOpen(true)}
>
  개인정보처리방침
</Button>
```

- [ ] **Step 2: 빌드 확인 + Commit**

```bash
pnpm build 2>&1 | tail -5
git add src/app/(public)/auth/signup/page.tsx
git commit -m "refactor: use Button link variant for terms/privacy links"
```

---

### Task 14: image-crop-modal.tsx — 텍스트 버튼 Button 교체

**Files:**
- Modify: `src/components/dashboard/image-crop-modal.tsx`

- [ ] **Step 1: "초기화" 버튼 교체 (line 400-406)**

```tsx
<Button variant="ghost" size="sm" className="gap-1" onClick={handleReset}>
  <ArrowsClockwise className="size-3" />
  초기화
</Button>
```

- [ ] **Step 2: "다른 이미지 선택" 버튼 교체 (line 407-412)**

```tsx
<Button variant="ghost" size="sm" onClick={() => fileInputRef.current?.click()}>
  다른 이미지 선택
</Button>
```

- [ ] **Step 3: 빌드 확인 + Commit**

```bash
pnpm build 2>&1 | tail -5
git add src/components/dashboard/image-crop-modal.tsx
git commit -m "refactor: use Button component in image-crop-modal"
```

---

## Chunk 4: Verification

### Task 15: 전체 빌드 + 수동 확인

- [ ] **Step 1: 전체 빌드**

Run: `cd /Users/kiducklee/IdeaProjects/programmer-study/hddc/hddc-client-web && pnpm build`
Expected: Build succeeds with no errors

- [ ] **Step 2: raw button 잔여 검사**

Run: `grep -rn '<button' src/components/dashboard/ src/components/color-theme-picker.tsx src/components/fab.tsx src/app/ --include='*.tsx' | grep -v 'node_modules' | grep -v '<Button'`

scope 제외 항목만 남아있어야 함:
- `profile-header-editor.tsx` 레이아웃 프리뷰 (4개) — Phase B
- `edit/page.tsx` SidebarButton 내부 (1개) — scope 제외
- `fab.tsx` FAB 트리거 (1개) — 커스텀 디자인

- [ ] **Step 3: dev 서버 기동 확인**

Run: `cd /Users/kiducklee/IdeaProjects/programmer-study/hddc/hddc-client-web && pnpm dev`
수동으로 http://localhost:3100 접속하여:
- 대시보드 에디터: 링크 배치/스타일/애니메이션 셀렉터 동작 확인
- 테마 에디터: 프리셋, 커스텀 컬러, 배경색, light/dark, 폰트 확인
- 프리뷰: mobile/web 토글 확인
- 회원가입: 이용약관/개인정보 링크 확인
- FAB: 메뉴 열기/닫기 확인
