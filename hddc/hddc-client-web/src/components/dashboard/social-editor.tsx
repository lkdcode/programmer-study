"use client";

import { useCallback } from "react";
import {
  DndContext,
  closestCenter,
  PointerSensor,
  useSensor,
  useSensors,
  type DragEndEvent,
} from "@dnd-kit/core";
import {
  SortableContext,
  verticalListSortingStrategy,
  useSortable,
} from "@dnd-kit/sortable";
import { CSS } from "@dnd-kit/utilities";
import { Button } from "@/components/ui/button";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Trash, DotsSixVertical } from "@phosphor-icons/react";
import { useSectionFocus } from "@/contexts/edit-focus-context";
import {
  type SocialPlatform,
  type SocialLink,
  SOCIAL_PLATFORMS,
  SOCIAL_PLATFORM_LABELS,
  SOCIAL_PLATFORM_BASE_URLS,
  normalizeSocialHandle,
} from "@/lib/profile-types";

interface Props {
  socials: SocialLink[];
  addSocial: (platform: SocialPlatform) => void;
  updateSocial: (id: string, url: string) => void;
  removeSocial: (id: string) => void;
  reorderSocials: (activeId: string, overId: string) => void;
}

function SortableSocialItem({ social, children }: { social: SocialLink; children: React.ReactNode }) {
  const { attributes, listeners, setNodeRef, transform, transition, isDragging } = useSortable({ id: social.id });
  const style = {
    transform: CSS.Transform.toString(transform),
    transition,
    zIndex: isDragging ? 50 : undefined,
    opacity: isDragging ? 0.8 : undefined,
  };

  return (
    <div ref={setNodeRef} style={style} {...attributes} className="flex items-center gap-2">
      <div
        {...listeners}
        className="flex shrink-0 cursor-grab items-center touch-none text-muted-foreground/50 transition-colors hover:text-muted-foreground active:cursor-grabbing"
      >
        <DotsSixVertical className="size-4" weight="bold" />
      </div>
      {children}
    </div>
  );
}

/** Placeholder text per platform */
const SOCIAL_PLACEHOLDERS: Partial<Record<SocialPlatform, string>> = {
  instagram: "username",
  youtube: "@channel",
  x: "username",
  tiktok: "username",
  threads: "username",
  facebook: "pagename",
  kakaotalk: "_abc123",
  "naver-blog": "blogid",
  email: "you@example.com",
  website: "https://example.com",
};

function SocialInput({
  social,
  updateSocial,
}: {
  social: SocialLink;
  updateSocial: (id: string, url: string) => void;
}) {
  const baseUrl = SOCIAL_PLATFORM_BASE_URLS[social.platform];
  const placeholder = SOCIAL_PLACEHOLDERS[social.platform] || "입력";

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    updateSocial(social.id, e.target.value);
  };

  const handleBlur = (e: React.FocusEvent<HTMLInputElement>) => {
    const normalized = normalizeSocialHandle(social.platform, e.target.value);
    if (normalized !== e.target.value) {
      updateSocial(social.id, normalized);
    }
  };

  const handlePaste = (e: React.ClipboardEvent<HTMLInputElement>) => {
    const pasted = e.clipboardData.getData("text");
    if (pasted && baseUrl) {
      e.preventDefault();
      const normalized = normalizeSocialHandle(social.platform, pasted);
      updateSocial(social.id, normalized);
    }
  };

  // email & website: free-form input (no prefix)
  if (!baseUrl) {
    return (
      <input
        placeholder={placeholder}
        value={social.url}
        onChange={handleChange}
        className="h-7 w-full min-w-0 flex-1 rounded-md border border-input bg-transparent px-2 text-sm outline-none focus:ring-1 focus:ring-ring placeholder:text-muted-foreground"
      />
    );
  }

  // Platform with base URL: show prefix + handle input
  return (
    <div className="flex h-7 flex-1 items-center overflow-hidden rounded-md border border-input text-sm">
      <span className="flex h-full shrink-0 items-center bg-muted px-2 text-[10px] text-muted-foreground">
        {baseUrl.replace("https://", "")}
      </span>
      <input
        placeholder={placeholder}
        value={social.url}
        onChange={handleChange}
        onBlur={handleBlur}
        onPaste={handlePaste}
        className="h-full min-w-0 flex-1 bg-transparent px-2 text-sm outline-none placeholder:text-muted-foreground"
      />
    </div>
  );
}

export function SocialEditor({ socials, addSocial, updateSocial, removeSocial, reorderSocials }: Props) {
  const usedPlatforms = new Set(socials.map((s) => s.platform));
  const availablePlatforms = SOCIAL_PLATFORMS.filter((p) => !usedPlatforms.has(p));
  const sectionFocus = useSectionFocus("socials");

  const sensors = useSensors(
    useSensor(PointerSensor, { activationConstraint: { distance: 5 } }),
  );

  const handleDragEnd = useCallback(
    (event: DragEndEvent) => {
      const { active, over } = event;
      if (!over || active.id === over.id) return;
      reorderSocials(active.id as string, over.id as string);
    },
    [reorderSocials],
  );

  return (
    <section className="flex flex-col gap-3" {...sectionFocus}>
      <div className="flex items-center justify-between">
        <h3 className="text-sm font-semibold">소셜 아이콘</h3>
        <span className="text-[10px] text-muted-foreground">{socials.length}/8</span>
      </div>
      <DndContext sensors={sensors} collisionDetection={closestCenter} onDragEnd={handleDragEnd}>
        <SortableContext items={socials.map((s) => s.id)} strategy={verticalListSortingStrategy}>
          {socials.map((social) => (
            <SortableSocialItem key={social.id} social={social}>
              <span className="w-16 shrink-0 text-[11px] font-medium">{SOCIAL_PLATFORM_LABELS[social.platform]}</span>
              <SocialInput social={social} updateSocial={updateSocial} />
              <Button variant="ghost" size="icon-xs" onClick={() => removeSocial(social.id)} className="shrink-0 text-muted-foreground hover:text-destructive" aria-label="삭제">
                <Trash className="size-3.5" />
              </Button>
            </SortableSocialItem>
          ))}
        </SortableContext>
      </DndContext>
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
