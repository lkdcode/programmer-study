"use client";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Trash } from "@phosphor-icons/react";
import { useSectionFocus } from "@/contexts/edit-focus-context";
import { type SocialPlatform, type SocialLink, SOCIAL_PLATFORMS, SOCIAL_PLATFORM_LABELS } from "@/lib/profile-types";

interface Props {
  socials: SocialLink[];
  addSocial: (platform: SocialPlatform) => void;
  updateSocial: (id: string, url: string) => void;
  removeSocial: (id: string) => void;
}

export function SocialEditor({ socials, addSocial, updateSocial, removeSocial }: Props) {
  const usedPlatforms = new Set(socials.map((s) => s.platform));
  const availablePlatforms = SOCIAL_PLATFORMS.filter((p) => !usedPlatforms.has(p));
  const sectionFocus = useSectionFocus("socials");

  return (
    <section className="flex flex-col gap-3" {...sectionFocus}>
      <div className="flex items-center justify-between">
        <h3 className="text-sm font-semibold">소셜 아이콘</h3>
        <span className="text-[10px] text-muted-foreground">{socials.length}/8</span>
      </div>
      {socials.map((social) => (
        <div key={social.id} className="flex items-center gap-2">
          <span className="w-20 shrink-0 text-xs font-medium">{SOCIAL_PLATFORM_LABELS[social.platform]}</span>
          <Input placeholder="URL 입력" value={social.url} onChange={(e) => updateSocial(social.id, e.target.value)} className="h-7 flex-1 text-sm" />
          <Button variant="ghost" size="icon-xs" onClick={() => removeSocial(social.id)} className="text-muted-foreground hover:text-destructive" aria-label="삭제">
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
              <SelectItem key={platform} value={platform}>{SOCIAL_PLATFORM_LABELS[platform]}</SelectItem>
            ))}
          </SelectContent>
        </Select>
      )}
    </section>
  );
}
