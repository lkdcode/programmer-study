"use client";

import { useState, useRef, useEffect } from "react";
import {
  useColorTheme,
  COLOR_THEMES,
  type ColorTheme,
} from "@/hooks/use-color-theme";
import { Button } from "@/components/ui/button";
import { Palette } from "@phosphor-icons/react";

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

export function ColorThemePicker() {
  const { colorTheme, setColorTheme } = useColorTheme();
  const [open, setOpen] = useState(false);
  const ref = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (!open) return;
    function handleClick(e: MouseEvent) {
      if (ref.current && !ref.current.contains(e.target as Node)) {
        setOpen(false);
      }
    }
    document.addEventListener("mousedown", handleClick);
    return () => document.removeEventListener("mousedown", handleClick);
  }, [open]);

  return (
    <div className="relative" ref={ref}>
      <Button
        variant="ghost"
        size="icon"
        onClick={() => setOpen(!open)}
        aria-label="색상 테마 선택"
      >
        <Palette className="size-4" />
      </Button>
      {open && (
        <div className="absolute right-0 top-full mt-2 rounded-lg border border-border bg-popover p-2.5 shadow-md">
          <div className="flex gap-2">
            {COLOR_THEMES.map((theme) => (
              <button
                key={theme}
                onClick={() => {
                  setColorTheme(theme);
                  setOpen(false);
                }}
                className={`cursor-pointer size-7 rounded-full transition-transform hover:scale-110 ${
                  colorTheme === theme
                    ? "ring-2 ring-foreground ring-offset-2 ring-offset-background"
                    : ""
                }`}
                style={{ backgroundColor: THEME_COLORS[theme] }}
                aria-label={THEME_LABELS[theme]}
              />
            ))}
          </div>
        </div>
      )}
    </div>
  );
}
