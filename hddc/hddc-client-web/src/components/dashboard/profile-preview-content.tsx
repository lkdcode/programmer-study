"use client";

import { type ProfileData } from "@/lib/profile-types";
import {
  InstagramLogo,
  YoutubeLogo,
  XLogo,
  GithubLogo,
  TiktokLogo,
  LinkedinLogo,
  EnvelopeSimple,
  Globe,
  UserCircle,
} from "@phosphor-icons/react";

const SOCIAL_ICONS: Record<string, typeof InstagramLogo> = {
  instagram: InstagramLogo,
  youtube: YoutubeLogo,
  x: XLogo,
  github: GithubLogo,
  tiktok: TiktokLogo,
  linkedin: LinkedinLogo,
  email: EnvelopeSimple,
  website: Globe,
};

interface Props {
  profileData: ProfileData;
  variant: "mobile" | "web";
}

export function ProfilePreviewContent({ profileData, variant }: Props) {
  const { avatarUrl, nickname, bio, links, socials } = profileData;
  const hasContent = nickname || bio || links.length > 0 || socials.length > 0;

  if (!hasContent) {
    return (
      <div className="flex flex-col items-center justify-center gap-2 py-8 text-center">
        <UserCircle className="size-10 text-muted-foreground/40" weight="duotone" />
        <p className="text-[10px] text-muted-foreground">프로필을 꾸며보세요</p>
      </div>
    );
  }

  if (variant === "mobile") {
    return (
      <div className="flex flex-col items-center gap-2">
        <div className="flex flex-col items-center gap-1 pb-2">
          {avatarUrl ? (
            <img src={avatarUrl} alt="" className="size-10 rounded-full object-cover" />
          ) : (
            <div className="flex size-10 items-center justify-center rounded-full bg-primary/10 text-sm font-bold text-primary">
              {nickname ? nickname[0] : "?"}
            </div>
          )}
          <p className="text-xs font-semibold">{nickname || "이름 없음"}</p>
          {bio && <p className="text-[10px] text-muted-foreground text-center">{bio}</p>}
        </div>
        {links.length > 0 && (
          <div className="flex w-full flex-col gap-1.5">
            {links.map((link) => (
              <div key={link.id} className="flex h-7 items-center justify-center rounded-lg bg-muted/60 text-[10px] font-medium">
                {link.title || "제목 없음"}
              </div>
            ))}
          </div>
        )}
        {socials.length > 0 && (
          <div className="flex gap-2 pt-1">
            {socials.map((social) => {
              const Icon = SOCIAL_ICONS[social.platform] ?? Globe;
              return (
                <div key={social.id} className="flex size-6 items-center justify-center rounded-full bg-muted/60">
                  <Icon className="size-3 text-muted-foreground" />
                </div>
              );
            })}
          </div>
        )}
      </div>
    );
  }

  // Web variant
  return (
    <div className="grid grid-cols-[auto_1fr] gap-3">
      <div className="flex flex-col items-center gap-1.5 border-r border-border pr-3">
        {avatarUrl ? (
          <img src={avatarUrl} alt="" className="size-8 rounded-full object-cover" />
        ) : (
          <div className="flex size-8 items-center justify-center rounded-full bg-primary/10 text-xs font-bold text-primary">
            {nickname ? nickname[0] : "?"}
          </div>
        )}
        <p className="text-[9px] font-semibold">{nickname || "이름 없음"}</p>
        {bio && <p className="text-[8px] text-muted-foreground text-center">{bio}</p>}
        {socials.length > 0 && (
          <div className="flex gap-1.5 pt-1">
            {socials.map((social) => {
              const Icon = SOCIAL_ICONS[social.platform] ?? Globe;
              return (
                <div key={social.id} className="flex size-5 items-center justify-center rounded-full bg-muted/60">
                  <Icon className="size-2.5 text-muted-foreground" />
                </div>
              );
            })}
          </div>
        )}
      </div>
      <div className="flex flex-col gap-1.5">
        {links.map((link) => (
          <div key={link.id} className="flex h-6 items-center rounded-md bg-muted/60 px-2 text-[9px] font-medium">
            <Globe className="mr-1 size-3 text-muted-foreground" />
            {link.title || "제목 없음"}
          </div>
        ))}
        {links.length === 0 && (
          <p className="py-4 text-center text-[9px] text-muted-foreground">링크를 추가해보세요</p>
        )}
      </div>
    </div>
  );
}
