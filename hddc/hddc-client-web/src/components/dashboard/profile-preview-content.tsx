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
  rectSortingStrategy,
  useSortable,
} from "@dnd-kit/sortable";
import { CSS } from "@dnd-kit/utilities";
import { cn } from "@/lib/utils";
import { useEditFocus, type EditSection } from "@/contexts/edit-focus-context";
import { type ProfileData, type ProfileLink, type LinkLayout } from "@/lib/profile-types";
import {
  InstagramLogo,
  YoutubeLogo,
  XLogo,
  TiktokLogo,
  LinkedinLogo,
  Globe,
  UserCircle,
} from "@phosphor-icons/react";

const SOCIAL_ICONS: Record<string, typeof InstagramLogo> = {
  instagram: InstagramLogo,
  youtube: YoutubeLogo,
  x: XLogo,
  tiktok: TiktokLogo,
  linkedin: LinkedinLogo,
  website: Globe,
};

function HighlightWrapper({
  section,
  activeSection,
  className,
  inset,
  overlay,
  children,
}: {
  section: EditSection;
  activeSection: EditSection;
  className?: string;
  inset?: boolean;
  overlay?: boolean;
  children: React.ReactNode;
}) {
  const isActive = activeSection === section;
  const highlightClass = overlay
    ? "edit-highlight-overlay"
    : inset
      ? "edit-highlight-inset"
      : "edit-highlight";
  return (
    <div className={cn(
      "rounded-lg transition-all duration-300",
      isActive && highlightClass,
      className,
    )}>
      {children}
    </div>
  );
}

function Avatar({ src, isDefault }: { src: string | null; isDefault?: boolean }) {
  return (
    <div className="relative size-20 rounded-full ring-2 ring-background">
      <div className={cn(
        "flex size-20 items-center justify-center rounded-full text-base font-bold",
        isDefault
          ? "border-2 border-foreground bg-background text-foreground"
          : "bg-primary text-primary-foreground",
      )}>
        핫딜닷쿨
      </div>
      {src && (
        <img
          src={src}
          alt=""
          className="absolute inset-0 size-20 rounded-full object-cover"
          onError={(e) => { e.currentTarget.style.display = "none"; }}
        />
      )}
    </div>
  );
}

/* ─── Link items by layout ─── */

function LinkImage({ src, size = "size-9", isDefault }: { src: string; size?: string; isDefault?: boolean }) {
  return (
    <div className={cn("relative shrink-0", size)}>
      <div className={cn(
        "flex shrink-0 items-center justify-center rounded-full",
        isDefault ? "border border-foreground bg-background" : "bg-primary",
        size,
      )}>
        <span className={cn("text-[6px] font-bold leading-none", isDefault ? "text-foreground" : "text-primary-foreground")}>핫딜닷쿨</span>
      </div>
      <img
        src={src}
        alt=""
        className={cn("absolute inset-0 shrink-0 rounded-full object-cover", size)}
        onError={(e) => { e.currentTarget.style.display = "none"; }}
      />
    </div>
  );
}

function DefaultLinkIcon({ size = "size-9", isDefault }: { size?: string; isDefault?: boolean }) {
  return (
    <div className={cn(
      "flex shrink-0 items-center justify-center rounded-full",
      isDefault ? "border border-foreground bg-background" : "bg-primary",
      size,
    )}>
      <span className={cn("text-[6px] font-bold leading-none", isDefault ? "text-foreground" : "text-primary-foreground")}>핫딜닷쿨</span>
    </div>
  );
}

/* ─── Sortable link wrapper ─── */

function SortableLinkItem({
  link,
  children,
}: {
  link: ProfileLink;
  children: React.ReactNode;
}) {
  const { attributes, listeners, setNodeRef, transform, transition, isDragging } = useSortable({ id: link.id });
  const style = {
    transform: CSS.Transform.toString(transform),
    transition,
    zIndex: isDragging ? 50 : undefined,
    opacity: isDragging ? 0.8 : undefined,
  };

  return (
    <div
      ref={setNodeRef}
      style={style}
      {...attributes}
      {...listeners}
      onMouseDown={(e) => e.stopPropagation()}
      className="cursor-grab touch-none active:cursor-grabbing"
    >
      {children}
    </div>
  );
}

/* ─── Link renderers per layout ─── */

function ListLinkItem({ link, isDefault, tint }: { link: ProfileLink; isDefault?: boolean; tint?: string }) {
  return (
    <div
      className={cn(
        "flex h-12 items-center gap-3 rounded-xl border px-3 text-sm font-medium transition-colors",
        isDefault
          ? "border-foreground bg-background text-foreground hover:bg-muted/50"
          : tint ? "border-primary/15" : "border-primary/15 bg-primary/8 hover:bg-primary/12",
      )}
      style={tint ? { backgroundColor: tint } : undefined}
    >
      {link.imageUrl ? <LinkImage src={link.imageUrl} isDefault={isDefault} /> : <DefaultLinkIcon isDefault={isDefault} />}
      <span className="truncate">{link.title || "제목 없음"}</span>
    </div>
  );
}

function Grid2LinkItem({ link, isDefault, tint }: { link: ProfileLink; isDefault?: boolean; tint?: string }) {
  return (
    <div
      className={cn(
        "flex flex-col items-center gap-2 rounded-xl border p-3 transition-colors",
        isDefault
          ? "border-foreground bg-background text-foreground hover:bg-muted/50"
          : tint ? "border-primary/15" : "border-primary/15 bg-primary/8 hover:bg-primary/12",
      )}
      style={tint ? { backgroundColor: tint } : undefined}
    >
      {link.imageUrl ? <LinkImage src={link.imageUrl} size="size-12" isDefault={isDefault} /> : <DefaultLinkIcon size="size-12" isDefault={isDefault} />}
      <span className="w-full truncate text-center text-xs font-medium">{link.title || "제목 없음"}</span>
    </div>
  );
}

function Grid3LinkItem({ link, isDefault, tint }: { link: ProfileLink; isDefault?: boolean; tint?: string }) {
  return (
    <div
      className={cn(
        "flex flex-col items-center gap-1.5 rounded-lg border p-2 transition-colors",
        isDefault
          ? "border-foreground bg-background text-foreground hover:bg-muted/50"
          : tint ? "border-primary/15" : "border-primary/15 bg-primary/8 hover:bg-primary/12",
      )}
      style={tint ? { backgroundColor: tint } : undefined}
    >
      {link.imageUrl ? <LinkImage src={link.imageUrl} size="size-10" isDefault={isDefault} /> : <DefaultLinkIcon size="size-10" isDefault={isDefault} />}
      <span className="w-full truncate text-center text-[10px] font-medium">{link.title || "제목 없음"}</span>
    </div>
  );
}

/* ─── Links section with DnD ─── */

function LinksSection({
  links,
  linkLayout,
  activeSection,
  activeLinkId,
  onReorderLinks,
  isDefault,
  tint,
}: {
  links: ProfileLink[];
  linkLayout: LinkLayout;
  activeSection: EditSection;
  activeLinkId: string | null;
  onReorderLinks?: (activeId: string, overId: string) => void;
  isDefault?: boolean;
  tint?: string;
}) {
  const sensors = useSensors(
    useSensor(PointerSensor, { activationConstraint: { distance: 5 } }),
  );

  const handleDragEnd = useCallback(
    (event: DragEndEvent) => {
      const { active, over } = event;
      if (!over || active.id === over.id || !onReorderLinks) return;
      onReorderLinks(active.id as string, over.id as string);
    },
    [onReorderLinks],
  );

  if (links.length === 0) return null;

  const strategy = linkLayout === "list" ? verticalListSortingStrategy : rectSortingStrategy;

  const gridClass =
    linkLayout === "grid-3"
      ? "grid grid-cols-3 gap-2"
      : linkLayout === "grid-2"
        ? "grid grid-cols-2 gap-2.5"
        : "flex flex-col gap-2.5";

  const showSectionHighlight = activeSection === "links" && !activeLinkId;

  return (
    <HighlightWrapper section="links" activeSection={showSectionHighlight ? "links" : null} className="w-full">
      <DndContext sensors={sensors} collisionDetection={closestCenter} onDragEnd={handleDragEnd}>
        <SortableContext items={links.map((l) => l.id)} strategy={strategy}>
          <div className={gridClass}>
            {links.map((link) => (
              <SortableLinkItem key={link.id} link={link}>
                <div className={cn(
                  "transition-all duration-300",
                  linkLayout === "grid-3" ? "rounded-lg" : "rounded-xl",
                  activeLinkId === link.id && "edit-highlight",
                )}>
                  {linkLayout === "grid-3" ? (
                    <Grid3LinkItem link={link} isDefault={isDefault} tint={tint} />
                  ) : linkLayout === "grid-2" ? (
                    <Grid2LinkItem link={link} isDefault={isDefault} tint={tint} />
                  ) : (
                    <ListLinkItem link={link} isDefault={isDefault} tint={tint} />
                  )}
                </div>
              </SortableLinkItem>
            ))}
          </div>
        </SortableContext>
      </DndContext>
    </HighlightWrapper>
  );
}

/* ─── Main component ─── */

interface Props {
  profileData: ProfileData;
  variant: "mobile" | "web";
  onReorderLinks?: (activeId: string, overId: string) => void;
}

export function ProfilePreviewContent({ profileData, variant, onReorderLinks }: Props) {
  const { avatarUrl, backgroundUrl, backgroundColor, nickname, bio, links, socials, linkLayout, colorTheme, customSecondaryColor } = profileData;
  const isDefault = colorTheme === "default" || colorTheme === "white";
  const tint = colorTheme === "custom" && customSecondaryColor ? customSecondaryColor : undefined;
  const hasContent = nickname || bio || links.length > 0 || socials.length > 0;
  const { activeSection, activeLinkId } = useEditFocus();
  const bgStyle = backgroundColor ? { backgroundColor } : undefined;

  if (!hasContent) {
    return (
      <div className="flex flex-col items-center justify-center gap-3 py-16 text-center">
        <UserCircle className="size-16 text-muted-foreground/40" weight="duotone" />
        <p className="text-sm text-muted-foreground">프로필을 꾸며보세요</p>
      </div>
    );
  }

  if (variant === "mobile") {
    return (
      <div className="flex min-h-full w-full flex-col items-center gap-4 px-4" style={bgStyle}>
        {/* Background — always rendered; edge-to-edge via negative margin */}
        <HighlightWrapper section="background" activeSection={activeSection} overlay className="rounded-none -mx-4 mb-[-3.5rem] w-[calc(100%+2rem)]">
          {backgroundUrl ? (
            <img src={backgroundUrl} alt="" className="h-32 w-full object-cover" onError={(e) => { e.currentTarget.style.display = "none"; }} />
          ) : (
            <div className="h-32 w-full bg-background" />
          )}
        </HighlightWrapper>

        {/* Profile header */}
        <div className="flex flex-col items-center gap-2 pb-2">
          <HighlightWrapper section="avatar" activeSection={activeSection} className="rounded-full">
            <Avatar src={avatarUrl} isDefault={isDefault} />
          </HighlightWrapper>
          <HighlightWrapper section="nickname" activeSection={activeSection} className="px-3">
            <p className="text-base font-semibold">{nickname || "이름 없음"}</p>
          </HighlightWrapper>
          {bio && (
            <HighlightWrapper section="bio" activeSection={activeSection} className="px-3">
              <p className="whitespace-pre-line text-center text-sm text-muted-foreground">{bio}</p>
            </HighlightWrapper>
          )}
        </div>

        {/* Links with DnD */}
        <LinksSection
          links={links}
          linkLayout={linkLayout}
          activeSection={activeSection}
          activeLinkId={activeLinkId}
          onReorderLinks={onReorderLinks}
          isDefault={isDefault}
          tint={tint}
        />

        {/* Social icons */}
        {socials.length > 0 && (
          <HighlightWrapper section="socials" activeSection={activeSection}>
            <div className="flex gap-3 pt-2">
              {socials.map((social) => {
                const Icon = SOCIAL_ICONS[social.platform] ?? Globe;
                return (
                  <div key={social.id} className="flex size-9 items-center justify-center rounded-full bg-primary/10" style={tint ? { backgroundColor: tint } : undefined}>
                    <Icon className="size-4.5 text-primary" />
                  </div>
                );
              })}
            </div>
          </HighlightWrapper>
        )}
      </div>
    );
  }

  // Web variant — sidebar + content grid
  return (
    <div className="flex min-h-full flex-col gap-4" style={bgStyle}>
      {/* Background — always rendered */}
      <HighlightWrapper section="background" activeSection={activeSection} overlay className="rounded-none -mx-6 -mt-6">
        {backgroundUrl ? (
          <img src={backgroundUrl} alt="" className="h-36 w-full object-cover" onError={(e) => { e.currentTarget.style.display = "none"; }} />
        ) : (
          <div className="h-36 w-full bg-background" />
        )}
      </HighlightWrapper>

      <div className="grid grid-cols-[180px_1fr] gap-6">
        {/* Sidebar */}
        <div className="flex flex-col items-center gap-3 border-r border-border pr-6">
          <HighlightWrapper section="avatar" activeSection={activeSection} className="rounded-full">
            <Avatar src={avatarUrl} isDefault={isDefault} />
          </HighlightWrapper>
          <HighlightWrapper section="nickname" activeSection={activeSection} className="px-3">
            <p className="text-sm font-semibold">{nickname || "이름 없음"}</p>
          </HighlightWrapper>
          {bio && (
            <HighlightWrapper section="bio" activeSection={activeSection} className="px-3">
              <p className="whitespace-pre-line text-center text-xs text-muted-foreground">{bio}</p>
            </HighlightWrapper>
          )}
          {socials.length > 0 && (
            <HighlightWrapper section="socials" activeSection={activeSection}>
              <div className="flex flex-wrap justify-center gap-2 pt-2">
                {socials.map((social) => {
                  const Icon = SOCIAL_ICONS[social.platform] ?? Globe;
                  return (
                    <div key={social.id} className="flex size-8 items-center justify-center rounded-full bg-primary/10" style={tint ? { backgroundColor: tint } : undefined}>
                      <Icon className="size-4 text-primary" />
                    </div>
                  );
                })}
              </div>
            </HighlightWrapper>
          )}
        </div>

        {/* Links area */}
        <div>
          {links.length > 0 ? (
            <LinksSection
              links={links}
              linkLayout={linkLayout}
              activeSection={activeSection}
              activeLinkId={activeLinkId}
              onReorderLinks={onReorderLinks}
            />
          ) : (
            <p className="py-8 text-center text-sm text-muted-foreground">링크를 추가해보세요</p>
          )}
        </div>
      </div>
    </div>
  );
}
