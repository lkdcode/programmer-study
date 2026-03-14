"use client";

import { useState, useCallback } from "react";
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
import { Input } from "@/components/ui/input";
import { Trash, Plus, ImageSquare, List, GridFour, SquaresFour, DotsSixVertical } from "@phosphor-icons/react";
import { ImageCropModal } from "./image-crop-modal";
import { useSectionFocus, useEditFocus } from "@/contexts/edit-focus-context";
import { cn } from "@/lib/utils";
import type { ProfileLink, LinkLayout } from "@/lib/profile-types";

interface Props {
  links: ProfileLink[];
  linkLayout: LinkLayout;
  addLink: () => void;
  updateLink: (id: string, fields: Partial<Pick<ProfileLink, "title" | "url" | "imageUrl">>) => void;
  removeLink: (id: string) => void;
  reorderLinks: (activeId: string, overId: string) => void;
  setLinkLayout: (layout: LinkLayout) => void;
}

const LAYOUTS: { value: LinkLayout; label: string; icon: typeof List }[] = [
  { value: "list", label: "리스트", icon: List },
  { value: "grid-2", label: "2열", icon: GridFour },
  { value: "grid-3", label: "3열", icon: SquaresFour },
];

function SortableEditorCard({
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
    <div ref={setNodeRef} style={style} {...attributes} className="flex items-center gap-2 rounded-lg border border-border bg-card p-3">
      <div
        {...listeners}
        className="flex shrink-0 cursor-grab items-center touch-none text-muted-foreground/50 transition-colors hover:text-muted-foreground active:cursor-grabbing"
      >
        <DotsSixVertical className="size-5" weight="bold" />
      </div>
      {children}
    </div>
  );
}

export function LinkListEditor({ links, linkLayout, addLink, updateLink, removeLink, reorderLinks, setLinkLayout }: Props) {
  const [cropLinkId, setCropLinkId] = useState("");
  const cropLink = links.find((l) => l.id === cropLinkId);
  const sectionFocus = useSectionFocus("links");
  const { setActiveLinkId } = useEditFocus();

  const sensors = useSensors(
    useSensor(PointerSensor, { activationConstraint: { distance: 5 } }),
  );

  const handleDragEnd = useCallback(
    (event: DragEndEvent) => {
      const { active, over } = event;
      if (!over || active.id === over.id) return;
      reorderLinks(active.id as string, over.id as string);
    },
    [reorderLinks],
  );

  function handleCropApply(dataUrl: string) {
    updateLink(cropLinkId, { imageUrl: dataUrl });
    setCropLinkId("");
  }

  function handleCropCancel() {
    setCropLinkId("");
  }

  return (
    <section className="flex flex-col gap-3" {...sectionFocus}>
      <div className="flex items-center justify-between">
        <h3 className="text-sm font-semibold">링크</h3>
        <span className="text-[10px] text-muted-foreground">{links.length}/20</span>
      </div>

      {/* Layout selector */}
      <div className="flex items-center gap-1">
        <span className="mr-1 text-[11px] text-muted-foreground">배치</span>
        {LAYOUTS.map(({ value, label, icon: Icon }) => (
          <button
            key={value}
            onClick={() => setLinkLayout(value)}
            className={cn(
              "inline-flex cursor-pointer items-center gap-1 rounded-md px-2 py-1 text-[11px] font-medium transition-colors",
              linkLayout === value
                ? "bg-primary text-primary-foreground"
                : "bg-muted/60 text-muted-foreground hover:bg-muted",
            )}
          >
            <Icon className="size-3.5" weight={linkLayout === value ? "fill" : "regular"} />
            {label}
          </button>
        ))}
      </div>

      <DndContext sensors={sensors} collisionDetection={closestCenter} onDragEnd={handleDragEnd}>
        <SortableContext items={links.map((l) => l.id)} strategy={verticalListSortingStrategy}>
          {links.map((link) => (
            <div
              key={link.id}
              onMouseEnter={() => setActiveLinkId(link.id)}
              onMouseLeave={() => setActiveLinkId(null)}
            >
              <SortableEditorCard link={link}>
                {/* Image upload trigger */}
                <div className="shrink-0 cursor-pointer" onClick={() => setCropLinkId(link.id)}>
                  {link.imageUrl ? (
                    <img
                      src={link.imageUrl}
                      alt=""
                      className="size-12 rounded-full border border-border object-cover"
                      onError={() => updateLink(link.id, { imageUrl: "" })}
                    />
                  ) : (
                    <div className="flex size-12 items-center justify-center rounded-full border border-dashed border-border bg-muted/50">
                      <ImageSquare className="size-5 text-muted-foreground" />
                    </div>
                  )}
                </div>

                <div className="flex flex-1 flex-col gap-1.5">
                  <div className="relative">
                    <Input
                      placeholder="링크 제목"
                      value={link.title}
                      onChange={(e) => updateLink(link.id, { title: e.target.value.slice(0, 20) })}
                      className="h-7 pr-10 text-sm"
                      maxLength={20}
                    />
                    <span className="absolute right-2 top-1/2 -translate-y-1/2 text-[9px] text-muted-foreground">
                      {link.title.length}/20
                    </span>
                  </div>
                  <Input placeholder="https://..." type="url" value={link.url} onChange={(e) => updateLink(link.id, { url: e.target.value })} className="h-7 text-sm" />
                </div>

                <Button variant="ghost" size="icon-xs" onClick={() => removeLink(link.id)} className="text-muted-foreground hover:text-destructive" aria-label="링크 삭제">
                  <Trash className="size-3.5" />
                </Button>
              </SortableEditorCard>
            </div>
          ))}
        </SortableContext>
      </DndContext>

      <Button variant="outline" className="h-9 w-full text-sm" onClick={addLink} disabled={links.length >= 20}>
        <Plus className="mr-1 size-4" />
        링크 추가
      </Button>

      <ImageCropModal
        open={!!cropLinkId}
        initialSrc={cropLink?.imageUrl || null}
        onApply={handleCropApply}
        onCancel={handleCropCancel}
        linkTitle={cropLink?.title}
      />
    </section>
  );
}
