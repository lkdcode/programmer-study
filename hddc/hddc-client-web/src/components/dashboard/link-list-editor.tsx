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
          <div className="flex flex-col gap-0.5">
            <Button variant="ghost" size="icon-xs" onClick={() => moveLink(link.id, "up")} disabled={idx === 0} aria-label="위로 이동">
              <ArrowUp className="size-3" />
            </Button>
            <Button variant="ghost" size="icon-xs" onClick={() => moveLink(link.id, "down")} disabled={idx === links.length - 1} aria-label="아래로 이동">
              <ArrowDown className="size-3" />
            </Button>
          </div>
          <div className="flex flex-1 flex-col gap-1.5">
            <Input placeholder="링크 제목" value={link.title} onChange={(e) => updateLink(link.id, { title: e.target.value.slice(0, 50) })} className="h-7 text-sm" maxLength={50} />
            <Input placeholder="https://..." type="url" value={link.url} onChange={(e) => updateLink(link.id, { url: e.target.value })} className="h-7 text-sm" />
          </div>
          <Button variant="ghost" size="icon-xs" onClick={() => removeLink(link.id)} className="text-muted-foreground hover:text-destructive" aria-label="링크 삭제">
            <Trash className="size-3.5" />
          </Button>
        </div>
      ))}
      <Button variant="outline" className="h-9 w-full text-sm" onClick={addLink} disabled={links.length >= 20}>
        <Plus className="mr-1 size-4" />
        링크 추가
      </Button>
    </section>
  );
}
