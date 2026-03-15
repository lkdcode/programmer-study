"use client";

import { useState, useEffect } from "react";
import { DeviceMobile, Desktop } from "@phosphor-icons/react";
import { PhonePreviewFrame, BrowserPreviewFrame } from "./dashboard-preview-frame";
import { ProfilePreviewContent } from "./profile-preview-content";
import type { ProfileData } from "@/lib/profile-types";
import { FONT_FAMILY_CSS } from "@/lib/profile-types";
import { loadFont } from "@/lib/font-loader";

function luminanceContrast(hex: string): string {
  const h = hex.replace("#", "");
  const r = parseInt(h.slice(0, 2), 16);
  const g = parseInt(h.slice(2, 4), 16);
  const b = parseInt(h.slice(4, 6), 16);
  return (0.299 * r + 0.587 * g + 0.114 * b) / 255 > 0.5 ? "#000000" : "#ffffff";
}

interface Props {
  profileData: ProfileData;
  reorderLinks?: (activeId: string, overId: string) => void;
}

export function ProfilePreview({ profileData, reorderLinks }: Props) {
  const [view, setView] = useState<"mobile" | "web">("mobile");

  useEffect(() => {
    loadFont(profileData.fontFamily);
  }, [profileData.fontFamily]);

  const themeAttr = profileData.colorTheme;
  const darkClass = profileData.darkMode ? "dark" : "";

  // Custom theme: override CSS variables inline
  const fontStyle = { fontFamily: FONT_FAMILY_CSS[profileData.fontFamily] };
  const customStyle = themeAttr === "custom" && profileData.customPrimaryColor
    ? {
        "--primary": profileData.customPrimaryColor,
        "--primary-foreground": luminanceContrast(profileData.customPrimaryColor),
        "--ring": profileData.customPrimaryColor,
        ...fontStyle,
      } as React.CSSProperties
    : fontStyle as React.CSSProperties;

  return (
    <div className="flex h-full flex-col items-center gap-4 overflow-hidden p-3">
      {/* Toggle */}
      <div className="flex shrink-0 items-center gap-3">
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
      {view === "mobile" ? (
        <div
          data-theme={themeAttr}
          className={`flex min-h-0 flex-1 items-start justify-center overflow-hidden ${darkClass}`}
          style={customStyle}
        >
          <PhonePreviewFrame className="w-[345px] shrink-0" slug={profileData.slug} backgroundColor={profileData.backgroundColor}>
            <ProfilePreviewContent profileData={profileData} variant="mobile" onReorderLinks={reorderLinks} />
          </PhonePreviewFrame>
        </div>
      ) : (
        <div
          data-theme={themeAttr}
          className={`min-h-0 flex-1 overflow-hidden ${darkClass}`}
          style={customStyle}
        >
          <BrowserPreviewFrame className="h-full w-full" slug={profileData.slug} backgroundColor={profileData.backgroundColor}>
            <ProfilePreviewContent profileData={profileData} variant="web" onReorderLinks={reorderLinks} />
          </BrowserPreviewFrame>
        </div>
      )}
    </div>
  );
}
