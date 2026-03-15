"use client";

import { useState, useEffect } from "react";
import { useProfileData } from "@/hooks/use-profile-data";
import { ProfileEditor } from "@/components/dashboard/profile-editor";
import { ProfilePreview } from "@/components/dashboard/profile-preview";
import { MobilePreviewButton } from "@/components/dashboard/mobile-preview-overlay";
import { EditFocusProvider } from "@/contexts/edit-focus-context";
import { Button } from "@/components/ui/button";
import {
  FloppyDisk, Check, CircleNotch, Link as LinkIcon,
  ArrowCounterClockwise, ArrowClockwise, Trash,
} from "@phosphor-icons/react";
import { toast } from "sonner";
import { cn } from "@/lib/utils";
import { ConfirmDialog } from "@/components/ui/confirm-dialog";

export default function ProfileEditPage() {
  const profile = useProfileData();
  const [confirmResetOpen, setConfirmResetOpen] = useState(false);

  useEffect(() => {
    const handler = (e: KeyboardEvent) => {
      if ((e.metaKey || e.ctrlKey) && e.key === "z") {
        e.preventDefault();
        if (e.shiftKey) profile.redoProfile();
        else profile.undoProfile();
      }
    };
    window.addEventListener("keydown", handler);
    return () => window.removeEventListener("keydown", handler);
  }, [profile.undoProfile, profile.redoProfile]);

  return (
    <EditFocusProvider>
      <div className="mx-auto flex h-full w-full max-w-6xl">
        {/* Left: Editor (scrolls, hidden scrollbar) */}
        <div className="flex-1 overflow-y-auto [scrollbar-width:none] [&::-webkit-scrollbar]:hidden lg:w-2/5 lg:flex-none">
          <div className="mx-auto max-w-xl px-4 py-6 sm:px-6">
            <div className="mb-4 flex h-8 items-center gap-3">
              <h1 className="text-lg font-semibold">프로필 편집</h1>
            </div>
            <div className="rounded-xl border border-border bg-card shadow-sm">
              <ProfileEditor {...profile} />
            </div>
          </div>
        </div>

        {/* Right: Preview (fixed, no scroll) */}
        <div className="hidden lg:flex lg:flex-1 flex-col border-l border-border">
          <div className="flex h-8 shrink-0 items-center justify-end gap-2 px-4 py-6">
            <span className="text-xs text-muted-foreground">
              {profile.saveStatus === "saving" && (
                <span className="inline-flex items-center gap-1">
                  <CircleNotch className="size-3 animate-spin" />
                  저장 중...
                </span>
              )}
              {profile.saveStatus === "saved" && (
                <span className="inline-flex items-center gap-1 text-primary">
                  <Check className="size-3" />
                  임시 저장됨
                </span>
              )}
            </span>
            <MobilePreviewButton profileData={profile.profileData} />
            <Button
              variant="outline"
              size="sm"
              className="h-8 text-xs"
              onClick={() => {
                const url = `https://hotdeal.cool/${profile.profileData.slug || "yourname"}`;
                navigator.clipboard.writeText(url);
                toast.success("링크가 복사되었습니다", { description: url });
              }}
              disabled={!profile.profileData.slug}
            >
              <LinkIcon className="mr-1 size-3.5" />
              공유
            </Button>
            <Button
              size="sm"
              onClick={profile.saveNow}
              className="h-8 text-xs"
            >
              <FloppyDisk className="mr-1 size-3.5" />
              저장
            </Button>
          </div>
          <div className="flex-1 overflow-hidden px-4 pb-4">
            <div className="h-full rounded-xl border border-border bg-card shadow-sm">
              <ProfilePreview
                profileData={profile.profileData}
                reorderLinks={profile.reorderLinks}
              />
            </div>
          </div>
        </div>

        {/* Floating edit tools */}
        <div className="fixed bottom-6 left-20 z-50 hidden lg:block">
          <div className="flex items-center gap-1 rounded-xl border border-border bg-card p-1.5 shadow-sm">
            <SidebarButton
              icon={<ArrowCounterClockwise className="size-4" />}
              label="되돌리기"
              onClick={profile.undoProfile}
              disabled={!profile.canUndo}
            />
            <SidebarButton
              icon={<ArrowClockwise className="size-4" />}
              label="다시 실행"
              onClick={profile.redoProfile}
              disabled={!profile.canRedo}
            />
            <div className="mx-1 h-5 w-px bg-border" />
            <SidebarButton
              icon={<Trash className="size-4" />}
              label="초기화"
              onClick={() => setConfirmResetOpen(true)}
              variant="destructive"
            />
          </div>
        </div>
      </div>
      <ConfirmDialog
        open={confirmResetOpen}
        onOpenChange={setConfirmResetOpen}
        title="프로필 초기화"
        description="모든 프로필 데이터가 삭제됩니다. 초기화하시겠습니까?"
        onConfirm={() => {
          profile.resetProfile();
          setConfirmResetOpen(false);
        }}
        confirmLabel="초기화"
        variant="destructive"
      />
    </EditFocusProvider>
  );
}

function SidebarButton({
  icon,
  label,
  onClick,
  disabled,
  variant,
}: {
  icon: React.ReactNode;
  label: string;
  onClick: () => void;
  disabled?: boolean;
  variant?: "destructive";
}) {
  return (
    <button
      onClick={onClick}
      disabled={disabled}
      title={label}
      className={cn(
        "flex size-8 cursor-pointer items-center justify-center rounded-lg transition-colors disabled:cursor-not-allowed disabled:opacity-30",
        variant === "destructive"
          ? "text-destructive hover:bg-destructive/10"
          : "text-muted-foreground hover:bg-muted hover:text-foreground",
      )}
    >
      {icon}
    </button>
  );
}
