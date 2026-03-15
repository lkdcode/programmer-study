"use client";

import { useState, useEffect } from "react";
import { HexColorPicker, HexColorInput } from "react-colorful";
import { COLOR_THEMES, type ColorTheme } from "@/hooks/use-color-theme";
import { Sun, Moon, Palette } from "@phosphor-icons/react";
import { FONT_FAMILY_LABELS, FONT_FAMILY_CSS, type FontFamily } from "@/lib/profile-types";
import { useSectionFocus } from "@/contexts/edit-focus-context";
import { cn } from "@/lib/utils";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { Button } from "@/components/ui/button";
import { RemoveButton } from "@/components/ui/remove-button";
import { ColorPickerPopover } from "@/components/ui/color-picker-popover";
import { ToggleGroup, type ToggleGroupOption } from "@/components/ui/toggle-group";
import { SectionHeader } from "@/components/ui/section-header";

/* ─── Color utilities ─── */

function hexToRgb(hex: string): [number, number, number] {
  const h = hex.replace("#", "");
  return [
    parseInt(h.slice(0, 2), 16),
    parseInt(h.slice(2, 4), 16),
    parseInt(h.slice(4, 6), 16),
  ];
}

function hexToHsl(hex: string): [number, number, number] {
  const [r, g, b] = hexToRgb(hex).map((v) => v / 255);
  const max = Math.max(r, g, b), min = Math.min(r, g, b);
  const l = (max + min) / 2;
  if (max === min) return [0, 0, l * 100];
  const d = max - min;
  const s = l > 0.5 ? d / (2 - max - min) : d / (max + min);
  let h = 0;
  if (max === r) h = ((g - b) / d + (g < b ? 6 : 0)) / 6;
  else if (max === g) h = ((b - r) / d + 2) / 6;
  else h = ((r - g) / d + 4) / 6;
  return [h * 360, s * 100, l * 100];
}

function hslToHex(h: number, s: number, l: number): string {
  s /= 100; l /= 100;
  const a = s * Math.min(l, 1 - l);
  const f = (n: number) => {
    const k = (n + h / 30) % 12;
    const color = l - a * Math.max(Math.min(k - 3, 9 - k, 1), -1);
    return Math.round(255 * color).toString(16).padStart(2, "0");
  };
  return `#${f(0)}${f(8)}${f(4)}`;
}

function autoSecondary(primaryHex: string): string {
  const [h, s, l] = hexToHsl(primaryHex);
  return hslToHex(h, Math.max(s * 0.4, 10), Math.min(l + 30, 92));
}

function autoForeground(hex: string): string {
  const [r, g, b] = hexToRgb(hex);
  const luminance = (0.299 * r + 0.587 * g + 0.114 * b) / 255;
  return luminance > 0.5 ? "#000000" : "#ffffff";
}

/* ─── Theme data ─── */

const PRESET_THEMES = COLOR_THEMES.filter((t) => t !== "custom");

const THEME_COLORS: Record<Exclude<ColorTheme, "custom">, { primary: string; secondary: string }> = {
  default: { primary: "oklch(0.205 0 0)", secondary: "oklch(0.55 0 0)" },
  teal: { primary: "oklch(0.6 0.118 184.704)", secondary: "oklch(0.828 0.08 184.364)" },
  orange: { primary: "oklch(0.553 0.195 38.402)", secondary: "oklch(0.837 0.128 66.29)" },
  blue: { primary: "oklch(0.5 0.134 242.749)", secondary: "oklch(0.828 0.111 230.318)" },
  violet: { primary: "oklch(0.491 0.27 292.581)", secondary: "oklch(0.811 0.111 293.571)" },
  yellow: { primary: "oklch(0.852 0.199 91.936)", secondary: "oklch(0.905 0.182 98.111)" },
  red: { primary: "oklch(0.505 0.213 27.518)", secondary: "oklch(0.808 0.114 19.571)" },
  white: { primary: "oklch(0.985 0 0)", secondary: "oklch(0.8 0 0)" },
};

const THEME_LABELS: Record<Exclude<ColorTheme, "custom">, string> = {
  default: "Default",
  teal: "틸",
  orange: "오렌지",
  blue: "블루",
  violet: "바이올렛",
  yellow: "옐로",
  red: "레드",
  white: "화이트",
};

const BG_PALETTE = [
  "#ffffff", "#f8fafc", "#f1f5f9", "#e2e8f0", "#94a3b8", "#475569", "#1e293b", "#0f172a",
  "#fefce8", "#fef3c7", "#ffedd5", "#fee2e2", "#fce7f3", "#fae8ff", "#ede9fe", "#dbeafe",
  "#ecfdf5", "#d1fae5", "#cffafe", "#e0f2fe", "#fef9c3", "#fed7aa", "#fecaca", "#e9d5ff",
];

const FONT_OPTIONS: ToggleGroupOption<FontFamily>[] = (Object.keys(FONT_FAMILY_LABELS) as FontFamily[]).map((font) => ({
  value: font,
  label: FONT_FAMILY_LABELS[font],
}));

/* ─── Component ─── */

interface Props {
  colorTheme: ColorTheme;
  darkMode: boolean;
  backgroundColor: string | null;
  customPrimaryColor: string | null;
  customSecondaryColor: string | null;
  fontFamily: FontFamily;
  setColorTheme: (theme: ColorTheme) => void;
  setDarkMode: (dark: boolean) => void;
  setBackgroundColor: (color: string | null) => void;
  setCustomColors: (primary: string, secondary: string) => void;
  setFontFamily: (font: FontFamily) => void;
}

export function ThemeEditor({
  colorTheme, darkMode, backgroundColor,
  customPrimaryColor, customSecondaryColor, fontFamily,
  setColorTheme, setDarkMode, setBackgroundColor, setCustomColors, setFontFamily,
}: Props) {
  const sectionFocus = useSectionFocus("theme");

  const [presetPickerOpen, setPresetPickerOpen] = useState(false);
  const [tempPrimary, setTempPrimary] = useState(customPrimaryColor || "#3b82f6");
  const [tempSecondary, setTempSecondary] = useState(customSecondaryColor || autoSecondary("#3b82f6"));
  const [autoSync, setAutoSync] = useState(true);

  useEffect(() => {
    if (autoSync) setTempSecondary(autoSecondary(tempPrimary));
  }, [tempPrimary, autoSync]);

  return (
    <section className="flex flex-col gap-4" {...sectionFocus}>
      <SectionHeader title="테마" />

      {/* Color presets */}
      <div>
        <p className="mb-2 text-xs text-muted-foreground">컬러 프리셋</p>
        <div className="grid grid-cols-4 gap-2">
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
        </div>

        {/* Custom color preset picker */}
        <Popover open={presetPickerOpen} onOpenChange={(open) => {
          if (open) {
            const p = customPrimaryColor || "#3b82f6";
            setTempPrimary(p);
            setTempSecondary(customSecondaryColor || autoSecondary(p));
            setAutoSync(!customSecondaryColor);
          }
          setPresetPickerOpen(open);
        }}>
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
          <PopoverContent className="w-auto p-3" align="start">
            <div className="flex flex-col gap-3">
              <div className="flex gap-3">
                {/* Primary picker */}
                <div className="w-[200px]">
                  <p className="mb-1.5 text-[11px] font-medium text-muted-foreground">Primary</p>
                  <HexColorPicker
                    color={tempPrimary}
                    onChange={setTempPrimary}
                    style={{ width: "100%", height: "140px" }}
                  />
                  <div className="mt-2 flex items-center gap-2">
                    <div
                      className="size-7 shrink-0 rounded-md border border-border"
                      style={{ backgroundColor: tempPrimary }}
                    />
                    <div className="flex items-center gap-1 rounded-md border border-input px-2 py-1 text-sm">
                      <span className="text-muted-foreground">#</span>
                      <HexColorInput
                        color={tempPrimary}
                        onChange={setTempPrimary}
                        className="w-[5rem] bg-transparent uppercase outline-none"
                        prefixed={false}
                      />
                    </div>
                  </div>
                </div>

                {/* Secondary picker */}
                <div className="w-[200px]">
                  <div className="mb-1.5 flex items-center justify-between">
                    <p className="text-[11px] font-medium text-muted-foreground">Secondary</p>
                    <button
                      onClick={() => {
                        setAutoSync(!autoSync);
                        if (!autoSync) setTempSecondary(autoSecondary(tempPrimary));
                      }}
                      className={cn(
                        "cursor-pointer text-[10px] transition-colors",
                        autoSync ? "text-primary font-medium" : "text-muted-foreground hover:text-foreground",
                      )}
                    >
                      {autoSync ? "자동" : "수동"}
                    </button>
                  </div>
                  <HexColorPicker
                    color={tempSecondary}
                    onChange={(hex) => { setAutoSync(false); setTempSecondary(hex); }}
                    style={{ width: "100%", height: "140px" }}
                  />
                  <div className="mt-2 flex items-center gap-2">
                    <div
                      className="size-7 shrink-0 rounded-md border border-border"
                      style={{ backgroundColor: tempSecondary }}
                    />
                    <div className="flex items-center gap-1 rounded-md border border-input px-2 py-1 text-sm">
                      <span className="text-muted-foreground">#</span>
                      <HexColorInput
                        color={tempSecondary}
                        onChange={(hex) => { setAutoSync(false); setTempSecondary(hex); }}
                        className="w-[5rem] bg-transparent uppercase outline-none"
                        prefixed={false}
                      />
                    </div>
                  </div>
                </div>
              </div>

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
            </div>
          </PopoverContent>
        </Popover>
      </div>

      {/* Background color */}
      <div>
        <div className="mb-2 flex items-center justify-between">
          <p className="text-xs text-muted-foreground">배경 색상</p>
          {backgroundColor && (
            <RemoveButton label="초기화" onClick={() => setBackgroundColor(null)} />
          )}
        </div>
        <div className="grid grid-cols-8 gap-1.5">
          {BG_PALETTE.map((color) => (
            <button
              key={color}
              onClick={() => setBackgroundColor(color)}
              className={cn(
                "size-7 cursor-pointer rounded-full border transition-transform hover:scale-110",
                backgroundColor === color
                  ? "ring-2 ring-foreground ring-offset-2 ring-offset-background"
                  : "",
                color === "#ffffff" ? "border-border" : "border-transparent",
              )}
              style={{ backgroundColor: color }}
              aria-label={color}
            />
          ))}
        </div>

        {/* Custom background color picker popover */}
        <ColorPickerPopover
          color={backgroundColor || "#ffffff"}
          onChange={(color) => setBackgroundColor(color)}
        />
      </div>

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

      {/* Font selector */}
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
    </section>
  );
}
