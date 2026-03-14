"use client";

import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { UserCircle } from "@phosphor-icons/react";
import type { ProfileData } from "@/lib/profile-types";

interface Props {
  profileData: ProfileData;
  updateProfile: (fields: Partial<Pick<ProfileData, "avatarUrl" | "nickname" | "bio">>) => void;
}

export function ProfileHeaderEditor({ profileData, updateProfile }: Props) {
  return (
    <section className="flex flex-col gap-4">
      <h3 className="text-sm font-semibold">프로필</h3>
      <div className="flex items-center gap-4">
        {profileData.avatarUrl ? (
          <img src={profileData.avatarUrl} alt="" className="size-14 rounded-full object-cover border border-border" />
        ) : (
          <div className="flex size-14 items-center justify-center rounded-full bg-muted">
            <UserCircle className="size-8 text-muted-foreground" weight="duotone" />
          </div>
        )}
        <div className="flex-1">
          <Label htmlFor="avatar-url" className="text-xs text-muted-foreground">프로필 이미지 URL</Label>
          <Input id="avatar-url" type="url" placeholder="https://example.com/photo.jpg" value={profileData.avatarUrl ?? ""} onChange={(e) => updateProfile({ avatarUrl: e.target.value || null })} className="mt-1 h-8 text-sm" />
        </div>
      </div>
      <div>
        <Label htmlFor="nickname" className="text-xs text-muted-foreground">닉네임</Label>
        <Input id="nickname" placeholder="나만의 이름" value={profileData.nickname} onChange={(e) => updateProfile({ nickname: e.target.value.slice(0, 20) })} className="mt-1 h-8 text-sm" maxLength={20} />
        <p className="mt-1 text-right text-[10px] text-muted-foreground">{profileData.nickname.length}/20</p>
      </div>
      <div>
        <Label htmlFor="bio" className="text-xs text-muted-foreground">한줄 소개</Label>
        <Input id="bio" placeholder="자기소개를 입력하세요" value={profileData.bio} onChange={(e) => updateProfile({ bio: e.target.value.slice(0, 80) })} className="mt-1 h-8 text-sm" maxLength={80} />
        <p className="mt-1 text-right text-[10px] text-muted-foreground">{profileData.bio.length}/80</p>
      </div>
    </section>
  );
}
