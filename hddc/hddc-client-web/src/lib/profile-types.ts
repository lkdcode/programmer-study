export const SOCIAL_PLATFORMS = [
  "instagram",
  "youtube",
  "x",
  "github",
  "tiktok",
  "linkedin",
  "email",
  "website",
] as const;

export type SocialPlatform = (typeof SOCIAL_PLATFORMS)[number];

export interface ProfileLink {
  id: string;
  title: string;
  url: string;
  order: number;
}

export interface SocialLink {
  id: string;
  platform: SocialPlatform;
  url: string;
}

export interface ProfileData {
  avatarUrl: string | null;
  nickname: string;
  bio: string;
  links: ProfileLink[];
  socials: SocialLink[];
  colorTheme: "teal" | "orange" | "blue" | "violet" | "yellow" | "red";
  darkMode: boolean;
}

export const DEFAULT_PROFILE: ProfileData = {
  avatarUrl: null,
  nickname: "",
  bio: "",
  links: [],
  socials: [],
  colorTheme: "teal",
  darkMode: false,
};

export const SOCIAL_PLATFORM_LABELS: Record<SocialPlatform, string> = {
  instagram: "Instagram",
  youtube: "YouTube",
  x: "X (Twitter)",
  github: "GitHub",
  tiktok: "TikTok",
  linkedin: "LinkedIn",
  email: "이메일",
  website: "웹사이트",
};
