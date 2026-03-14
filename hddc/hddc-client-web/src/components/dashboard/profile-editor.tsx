"use client";

import { ProfileHeaderEditor } from "./profile-header-editor";
import { LinkListEditor } from "./link-list-editor";
import { SocialEditor } from "./social-editor";
import { ThemeEditor } from "./theme-editor";
import type { useProfileData } from "@/hooks/use-profile-data";

type ProfileActions = ReturnType<typeof useProfileData>;

export function ProfileEditor(props: ProfileActions) {
  return (
    <div className="flex flex-col gap-8 overflow-y-auto p-6">
      <ProfileHeaderEditor
        profileData={props.profileData}
        updateProfile={props.updateProfile}
      />

      <hr className="border-border" />

      <LinkListEditor
        links={props.profileData.links}
        addLink={props.addLink}
        updateLink={props.updateLink}
        removeLink={props.removeLink}
        moveLink={props.moveLink}
      />

      <hr className="border-border" />

      <SocialEditor
        socials={props.profileData.socials}
        addSocial={props.addSocial}
        updateSocial={props.updateSocial}
        removeSocial={props.removeSocial}
      />

      <hr className="border-border" />

      <ThemeEditor
        colorTheme={props.profileData.colorTheme}
        darkMode={props.profileData.darkMode}
        setColorTheme={props.setColorTheme}
        setDarkMode={props.setDarkMode}
      />
    </div>
  );
}
