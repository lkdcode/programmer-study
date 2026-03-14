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
      <div>
        <p className="mb-2 text-xs text-muted-foreground">컬러 프리셋</p>
        <div className="flex gap-2">
          {COLOR_THEMES.map((theme) => (
            <button key={theme} onClick={() => setColorTheme(theme)} className={`cursor-pointer flex flex-col items-center gap-1 rounded-lg p-2 transition-all ${colorTheme === theme ? "bg-muted ring-2 ring-foreground ring-offset-2 ring-offset-background" : "hover:bg-muted/50"}`} aria-label={THEME_LABELS[theme]}>
              <div className="size-7 rounded-full" style={{ backgroundColor: THEME_COLORS[theme] }} />
              <span className="text-[10px] text-muted-foreground">{THEME_LABELS[theme]}</span>
            </button>
          ))}
        </div>
      </div>
      <div>
        <p className="mb-2 text-xs text-muted-foreground">모드</p>
        <div className="inline-flex items-center gap-1 rounded-full border border-border bg-muted/50 p-1">
          <button onClick={() => setDarkMode(false)} className={`cursor-pointer inline-flex items-center gap-1.5 rounded-full px-4 py-1.5 text-sm font-medium transition-all ${!darkMode ? "bg-background text-foreground shadow-sm" : "text-muted-foreground hover:text-foreground"}`}>
            <Sun className="size-4" /> Light
          </button>
          <button onClick={() => setDarkMode(true)} className={`cursor-pointer inline-flex items-center gap-1.5 rounded-full px-4 py-1.5 text-sm font-medium transition-all ${darkMode ? "bg-background text-foreground shadow-sm" : "text-muted-foreground hover:text-foreground"}`}>
            <Moon className="size-4" /> Dark
          </button>
        </div>
      </div>
    </section>
  );
}
