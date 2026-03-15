"use client";

import { useProfileData } from "@/hooks/use-profile-data";
import { ProfileEditor } from "@/components/dashboard/profile-editor";
import { ProfilePreview } from "@/components/dashboard/profile-preview";
import { MobilePreviewButton } from "@/components/dashboard/mobile-preview-overlay";
import { EditFocusProvider } from "@/contexts/edit-focus-context";
import { Button } from "@/components/ui/button";
import { FloppyDisk, Check, CircleNotch } from "@phosphor-icons/react";

export default function ProfileEditPage() {
  const profile = useProfileData();

  return (
    <EditFocusProvider>
      <div className="mx-auto w-full max-w-6xl px-4 py-6 sm:px-6">
        <div className="flex gap-6">
          {/* Left: Editor Column */}
          <div className="min-w-0 lg:w-2/5">
            <div className="mb-4 flex items-center gap-3">
              <h1 className="text-lg font-semibold">프로필 편집</h1>
            </div>
            <div className="rounded-xl border border-border bg-card shadow-sm">
              <ProfileEditor {...profile} />
            </div>
          </div>

          {/* Right: Preview Column (sticky header + mockup) */}
          <div className="hidden lg:block lg:w-3/5">
            <div className="sticky top-[calc(5rem+1px)]">
              <div className="mb-4 flex items-center justify-end gap-2">
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
                  size="sm"
                  onClick={profile.saveNow}
                  className="h-8 text-xs"
                >
                  <FloppyDisk className="mr-1 size-3.5" />
                  저장
                </Button>
              </div>
              <div className="h-[calc(100vh-11rem)] rounded-xl border border-border bg-card shadow-sm">
                <ProfilePreview
                  profileData={profile.profileData}
                  reorderLinks={profile.reorderLinks}
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </EditFocusProvider>
  );
}
