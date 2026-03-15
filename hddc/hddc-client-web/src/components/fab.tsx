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
