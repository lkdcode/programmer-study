"use client";

import { useState } from "react";
import { DeviceMobile, Desktop } from "@phosphor-icons/react";
import { PhoneMockup, BrowserMockup } from "@/components/device-mockup";
import { ProfilePreviewContent } from "./profile-preview-content";
import type { ProfileData } from "@/lib/profile-types";

interface Props {
  profileData: ProfileData;
}

export function ProfilePreview({ profileData }: Props) {
  const [view, setView] = useState<"mobile" | "web">("mobile");

  const themeAttr = profileData.colorTheme;
  const darkClass = profileData.darkMode ? "dark" : "";

  return (
    <div className="flex flex-col items-center gap-4 p-6">
      {/* Toggle */}
      <div className="flex items-center gap-3">
        <span className="text-xs font-semibold text-muted-foreground">미리보기</span>
        <div className="inline-flex items-center gap-1 rounded-full border border-border bg-muted/50 p-1">
          <button
            onClick={() => setView("mobile")}
            className={`cursor-pointer inline-flex items-center gap-1 rounded-full px-3 py-1 text-xs font-medium transition-all ${
              view === "mobile"
                ? "bg-primary text-primary-foreground shadow-sm"
                : "text-muted-foreground hover:text-foreground"
            }`}
          >
            <DeviceMobile className="size-3.5" />
            Mobile
          </button>
          <button
            onClick={() => setView("web")}
            className={`cursor-pointer inline-flex items-center gap-1 rounded-full px-3 py-1 text-xs font-medium transition-all ${
              view === "web"
                ? "bg-primary text-primary-foreground shadow-sm"
                : "text-muted-foreground hover:text-foreground"
            }`}
          >
            <Desktop className="size-3.5" />
            Web
          </button>
        </div>
      </div>

      {/* Mockup with scoped theme */}
      <div data-theme={themeAttr} className={darkClass}>
        {view === "mobile" ? (
          <PhoneMockup className="w-[200px]">
            <ProfilePreviewContent profileData={profileData} variant="mobile" />
          </PhoneMockup>
        ) : (
          <BrowserMockup className="w-[320px]">
            <ProfilePreviewContent profileData={profileData} variant="web" />
          </BrowserMockup>
        )}
      </div>
    </div>
  );
}
