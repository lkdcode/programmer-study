# Component Standardization — Phase A: UI Atoms

## Overview

프론트엔드 코드베이스의 raw `<button>`, 수동 드롭다운, `window.confirm`을 표준 컴포넌트로 통합한다. 목표는 일관된 UI와 접근성 확보.

### Scope 제외 (의도적)
- `profile-header-editor.tsx` 헤더 레이아웃 셀렉터 (4개) — 내부에 복잡한 미니 프리뷰 HTML 포함, Phase B에서 별도 처리
- `theme-editor.tsx` 배경 팔레트 컬러 버튼 (~24개) — ColorSwatch 컴포넌트로 전환 가능하나 Phase B로 연기
- `theme-editor.tsx:223` auto/manual 보조색 토글 — 단독 사용, Button으로 충분하지만 dual-color 피커 리팩터링 시 함께 처리
- `edit/page.tsx` SidebarButton (3개) — 로컬 헬퍼 컴포넌트, 이미 일관된 패턴으로 사용 중

## Decisions

| # | 항목 | 결정 | 근거 |
|---|------|------|------|
| 1 | 옵션 셀렉터 통합 | ToggleGroup 확장 + Button[data-active] 하이브리드 | 그룹 셀렉터는 ToggleGroup, 독립 토글은 Button으로 책임 분리 |
| 2 | RemoveButton | 인라인 중복 3곳 → 기존 컴포넌트로 교체 | 이미 존재하는 컴포넌트 활용 |
| 3 | ColorPickerPopover | 새 컴포넌트 추출 | temp state 캡슐화 + 2곳 중복 제거 |
| 4 | ConfirmDialog | AlertDialog 기반 새 컴포넌트 | 앱 디자인 일관성 + 향후 재사용 |
| 5 | 커스텀 드롭다운 | Popover로 전환 | Radix가 outside-click/escape/focus trap 제공 |
| 6 | 테마 프리셋 그리드 | Button variant로 교체 (인라인 유지) | 1곳 사용, 별도 컴포넌트 불필요 |
| 7 | 캐러셀 버튼 | Button size="icon" variant="outline" | 기존 Button으로 충분 |

## 1. ToggleGroup 확장

### 변경 사항
`variant` prop 추가: `"pill" | "square"`

```tsx
// pill (기존 — rounded-full, 큰 패딩)
<ToggleGroup variant="pill" value={view} onValueChange={setView} options={viewOptions} />

// square (신규 — rounded-md, 작은 패딩, 에디터용)
<ToggleGroup variant="square" size="sm" value={linkLayout} onValueChange={setLinkLayout} options={layoutOptions} />
```

### variant 스타일 차이

| | pill (기존) | square (신규) |
|---|---|---|
| container | `rounded-full border bg-muted/50 p-1` | `flex gap-1` (border 없음) |
| item | `rounded-full px-4 py-1.5` | `rounded-md px-2 py-1 text-[11px]` |
| active | `bg-primary text-primary-foreground shadow-sm` | `bg-primary text-primary-foreground` |
| inactive | `text-muted-foreground` | `bg-muted/60 text-muted-foreground` |

### ToggleGroup font 셀렉터 대응

현재 font 셀렉터는 폰트 이름 + 스타일 샘플을 2줄로 렌더링한다. ToggleGroup API에 `renderItem` prop을 추가하여 커스텀 렌더링을 지원:

```tsx
// renderItem이 없으면 기존 label+icon 렌더링
// renderItem이 있으면 커스텀 렌더링 (active 상태 전달)
<ToggleGroup
  variant="square"
  value={fontFamily}
  onValueChange={setFontFamily}
  options={fontOptions}
  renderItem={(option, isActive) => (
    <div className="flex flex-col items-center gap-0.5">
      <span className="text-[10px]">{option.label}</span>
      <span style={{ fontFamily: option.value }}>가나다</span>
    </div>
  )}
/>
```

### 적용 대상 (raw button 제거)
- `link-list-editor.tsx` — layout(3), style(5), animation(5) 셀렉터
- `theme-editor.tsx` — font family 셀렉터 (renderItem 사용)
- `theme-editor.tsx` — light/dark 모드 토글 (pill variant, icon 포함)
- `profile-preview.tsx` — mobile/web 토글 (pill variant 적용)

## 2. Button data-active 지원

### 변경 사항
`buttonVariants`에 active 상태 CSS 추가 (기존 variant 변경 없음):

```css
/* data-active="true" 일 때 ring 표시 */
[data-slot="button"][data-active="true"] {
  ring-2 ring-foreground ring-offset-2 ring-offset-background
}
```

Button 컴포넌트에 `active` prop 추가 → `data-active` attribute로 전달.

### 적용 대상
- `theme-editor.tsx` — 프리셋 컬러 그리드 (8개) → `<Button variant="ghost" active={selected}>`

## 3. ColorPickerPopover (신규 컴포넌트)

### 위치
`src/components/ui/color-picker-popover.tsx`

### API

```tsx
interface ColorPickerPopoverProps {
  color: string
  onChange: (color: string) => void
  triggerLabel?: string       // default: "직접 선택"
  triggerIcon?: React.ReactNode
  width?: string              // default: "240px"
  align?: "start" | "center" | "end"
}
```

### 내부 구조
```
Popover
├─ PopoverTrigger → Button (outline variant)
└─ PopoverContent
   ├─ HexColorPicker
   ├─ color preview swatch + HexColorInput
   └─ Button ("적용")
```

### 동작
- 열 때: `tempColor = color` 초기화
- 적용 클릭: `onChange(tempColor)` 호출 + 닫기
- 닫기(외부 클릭/Escape): 변경 없이 닫기

### 적용 대상
- `theme-editor.tsx` — 배경색 커스텀 피커
- `theme-editor.tsx` — 프리셋 커스텀 피커 (dual-color는 구조가 달라 별도 유지하되, 내부 raw button은 `<Button>`으로 교체)

## 4. ConfirmDialog (신규 컴포넌트)

### 위치
`src/components/ui/confirm-dialog.tsx`

### API

```tsx
interface ConfirmDialogProps {
  open: boolean
  onOpenChange: (open: boolean) => void
  title: string
  description: string
  onConfirm: () => void
  confirmLabel?: string       // default: "확인"
  cancelLabel?: string        // default: "취소"
  variant?: "default" | "destructive"  // default: "destructive"
}
```

### 내부 구조

Radix `AlertDialog` 기반 (Dialog와 달리 외부 클릭/Escape로 닫히지 않아 파괴적 동작에 적합).
프로젝트에 `AlertDialog` 미설치 시 `npx shadcn@latest add alert-dialog`로 추가.

```
AlertDialog
├─ AlertDialogContent
│  ├─ AlertDialogHeader → AlertDialogTitle + AlertDialogDescription
│  └─ AlertDialogFooter
│     ├─ AlertDialogCancel → Button (outline) 취소
│     └─ AlertDialogAction → Button (destructive | default) 확인
```

### 적용 대상
- `edit/page.tsx` — 프로필 초기화 (`window.confirm` 대체)

## 5. 커스텀 드롭다운 → Popover 전환

### 대상 파일
- `color-theme-picker.tsx` — 컬러 테마 드롭다운
- `fab.tsx` — FAB 메뉴

### 제거되는 코드
- `useRef` (outside-click용 ref)
- `useEffect` (mousedown listener)
- `useEffect` (keydown Escape listener — fab.tsx)
- 수동 `open` state 관리 → Popover의 controlled mode로 대체

### 변경 접근
- 기존 렌더링 구조는 유지하되 `<Popover>/<PopoverTrigger>/<PopoverContent>`로 래핑
- `fab.tsx`는 `side="top"` + `align="end"`로 위치 지정

## 6. RemoveButton 일관 적용

### 교체 대상
| 파일 | 라인 | 현재 텍스트 |
|------|------|------------|
| `profile-header-editor.tsx` | ~158 | "배경 제거" |
| `profile-header-editor.tsx` | ~195 | "사진 제거" |
| `theme-editor.tsx` | ~279 | "초기화" |

### 변경
```tsx
// Before
<button onClick={...} className="inline-flex cursor-pointer items-center gap-0.5 text-[11px] text-destructive hover:opacity-70">
  <X className="size-3" weight="bold" />
  배경 제거
</button>

// After
<RemoveButton label="배경 제거" onClick={...} />
```

## 7. 기타 raw button → Button 단순 치환

| 파일 | 요소 | 변경 |
|------|------|------|
| `theme-editor.tsx:259` | "적용" 버튼 | `<Button size="sm">적용</Button>` |
| `theme-editor.tsx:339` | "적용" 버튼 | ColorPickerPopover 추출 시 자동 해결 |
| `auth/layout.tsx:81,104` | 캐러셀 화살표 | `<Button variant="outline" size="icon" className="rounded-full">` |
| `signup/page.tsx:243,251` | 이용약관/개인정보 링크 | `<Button variant="link">` |
| `image-crop-modal.tsx:400` | "초기화" | `<Button variant="ghost" size="sm">` |
| `image-crop-modal.tsx:407` | "다른 이미지 선택" | `<Button variant="ghost" size="sm">` |

## 수정 대상 파일 목록

### 신규 생성 (2개)
- `src/components/ui/color-picker-popover.tsx`
- `src/components/ui/confirm-dialog.tsx`

### 기존 수정 (12개)
- `src/components/ui/button.tsx` — active prop + data-active 스타일
- `src/components/ui/toggle-group.tsx` — variant prop (pill/square)
- `src/components/dashboard/theme-editor.tsx` — ToggleGroup, ColorPickerPopover, RemoveButton, Button 적용
- `src/components/dashboard/link-list-editor.tsx` — ToggleGroup 적용
- `src/components/dashboard/profile-header-editor.tsx` — RemoveButton 적용
- `src/components/dashboard/profile-preview.tsx` — ToggleGroup pill 유지 (이미 적용 중이면 확인)
- `src/components/dashboard/image-crop-modal.tsx` — raw button → Button
- `src/components/color-theme-picker.tsx` — Popover 전환
- `src/components/fab.tsx` — Popover 전환
- `src/app/(public)/auth/layout.tsx` — Button 캐러셀
- `src/app/(public)/auth/signup/page.tsx` — Button link variant
- `src/app/(app)/dashboard/edit/page.tsx` — ConfirmDialog 적용

## Phase B (다음 단계 — 에디터 복합 컴포넌트)

Phase A 완료 후 진행:
- `SectionHeader` — 제목+설명 패턴 (10곳)
- `InputWithCounter` — 입력+글자수 카운터 (3곳)
- `ImageUploadField` — 업로드+제거 패턴 (3곳)
- `EditorCard` + `DragHandle` — 드래그 가능 카드 (3곳)
