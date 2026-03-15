"use client";

import { Question, X, ChatCircle, BookOpen, Lightbulb, Megaphone } from "@phosphor-icons/react";
import { cn } from "@/lib/utils";
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover";

const MENU_ITEMS = [
  { icon: ChatCircle, label: "문의하기", href: "#contact" },
  { icon: BookOpen, label: "FAQ / 가이드", href: "#faq" },
  { icon: Lightbulb, label: "피드백 보내기", href: "#feedback" },
  { icon: Megaphone, label: "공지사항", href: "#notice", badge: "NEW" },
];

export function Fab() {
  return (
    <div className="fixed bottom-6 right-6 z-[60]">
      <Popover>
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
        <PopoverContent
          side="top"
          align="end"
          className="mb-2 min-w-[200px] rounded-xl p-1.5"
          role="menu"
        >
          {MENU_ITEMS.map(({ icon: Icon, label, href, badge }) => (
            <a key={label} href={href} role="menuitem"
              className="flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm text-foreground transition-colors hover:bg-muted">
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
      </Popover>
    </div>
  );
}
