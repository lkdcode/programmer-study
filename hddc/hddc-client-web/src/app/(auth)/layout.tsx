"use client";

import { useState, useEffect } from "react";
import { usePathname } from "next/navigation";
import { PhoneMockup, BrowserMockup } from "@/components/device-mockup";
import { CaretLeft, CaretRight } from "@phosphor-icons/react";

const colorPresets = ["teal", "orange", "blue", "violet", "yellow", "red"] as const;

export default function AuthLayout({ children }: { children: React.ReactNode }) {
  const pathname = usePathname();
  const [presetIndex, setPresetIndex] = useState(0);
  const [mockupDark, setMockupDark] = useState(true);
  const [autoKey, setAutoKey] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setPresetIndex((prev) => (prev + 1) % colorPresets.length);
      setMockupDark((prev) => !prev);
    }, 3000);
    return () => clearInterval(timer);
  }, [autoKey]);

  function goNext() {
    setPresetIndex((prev) => (prev + 1) % colorPresets.length);
    setMockupDark((prev) => !prev);
    setAutoKey((prev) => prev + 1);
  }

  function goPrev() {
    setPresetIndex((prev) => (prev - 1 + colorPresets.length) % colorPresets.length);
    setMockupDark((prev) => !prev);
    setAutoKey((prev) => prev + 1);
  }

  return (
    <div className="flex min-h-svh items-center justify-center px-4 py-12">
      <div className="w-full max-w-4xl overflow-hidden rounded-2xl border border-border bg-card shadow-lg lg:flex lg:min-h-[680px]">
        {/* ─── Left: Branding Panel ─── */}

        {/* Desktop */}
        <div className={`${mockupDark ? "" : "dark"} hidden lg:flex lg:w-1/2 flex-col items-center justify-evenly rounded-l-2xl bg-background px-12 transition-colors duration-700`}>
          <span className="text-2xl font-bold tracking-tight text-foreground transition-colors duration-700">핫딜닷쿨</span>
          <div
            data-theme={colorPresets[presetIndex]}
            className={`${mockupDark ? "dark" : ""} flex items-end gap-4 sm:gap-6 transition-colors duration-700`}
          >
            <PhoneMockup className="w-[140px] transition-colors duration-700" />
            <BrowserMockup className="w-[185px] transition-colors duration-700" />
          </div>
          <div className="flex items-center gap-3">
            <button
              onClick={goPrev}
              className="cursor-pointer flex size-7 items-center justify-center rounded-full bg-foreground/5 text-muted-foreground transition-colors duration-700 hover:bg-foreground/10 hover:text-foreground"
            >
              <CaretLeft className="size-4" />
            </button>
            <p className="text-center text-sm leading-relaxed text-muted-foreground transition-colors duration-700">
              하나의 링크,{" "}
              <span className="text-primary transition-colors duration-700">두 개의 완벽한 뷰</span>
            </p>
            <button
              onClick={goNext}
              className="cursor-pointer flex size-7 items-center justify-center rounded-full bg-foreground/5 text-muted-foreground transition-colors duration-700 hover:bg-foreground/10 hover:text-foreground"
            >
              <CaretRight className="size-4" />
            </button>
          </div>
        </div>

        {/* Mobile */}
        <div className="flex items-center justify-center gap-2 rounded-t-2xl bg-muted/50 px-4 py-5 lg:hidden">
          <span className="text-lg font-bold">핫딜닷쿨</span>
          <span className="text-xs text-muted-foreground">·</span>
          <span className="text-xs text-muted-foreground">
            하나의 링크, <span className="text-primary">두 개의 완벽한 뷰</span>
          </span>
        </div>

        {/* ─── Right: Form ─── */}
        <div className="flex flex-1 items-center justify-center p-8 sm:p-12 lg:w-1/2">
          <div key={pathname} className="w-full max-w-sm animate-in fade-in slide-in-from-bottom-2 duration-300">
            {children}
          </div>
        </div>
      </div>
    </div>
  );
}
