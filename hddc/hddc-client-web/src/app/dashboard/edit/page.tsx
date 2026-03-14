"use client";

import { useProfileData } from "@/hooks/use-profile-data";
import { ProfileEditor } from "@/components/dashboard/profile-editor";
import { ProfilePreview } from "@/components/dashboard/profile-preview";
import { MobilePreviewButton } from "@/components/dashboard/mobile-preview-overlay";
import { Button } from "@/components/ui/button";
import { FloppyDisk, Check, CircleNotch } from "@phosphor-icons/react";

export default function ProfileEditPage() {
  const profile = useProfileData();

  return (
    <div className="flex flex-1 flex-col">
      {/* Sub-header: save status + mobile preview btn */}
      <div className="flex items-center justify-between border-b border-border px-4 py-2">
        <div className="flex items-center gap-2">
          <Button
            variant="outline"
            size="sm"
            onClick={profile.saveNow}
            className="h-8 text-xs"
          >
            <FloppyDisk className="mr-1 size-3.5" />
            저장
          </Button>
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
                저장됨
              </span>
            )}
          </span>
        </div>
        <MobilePreviewButton profileData={profile.profileData} />
      </div>

      {/* Main content: editor + preview */}
      <div className="flex flex-1">
        {/* Left: Editor */}
        <div className="flex-1 overflow-y-auto">
          <ProfileEditor {...profile} />
        </div>

        {/* Right: Preview (desktop only) */}
        <div className="hidden border-l border-border lg:flex lg:w-[380px] lg:flex-col lg:items-center lg:justify-start lg:overflow-y-auto sticky top-14 h-[calc(100svh-3.5rem-2.75rem)]">
          <ProfilePreview profileData={profile.profileData} />
        </div>
      </div>
    </div>
  );
}
