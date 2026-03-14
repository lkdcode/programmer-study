export const SOCIAL_PLATFORMS = [
  "instagram",
  "youtube",
  "x",
  "tiktok",
  "linkedin",
  "website",
] as const;

export type SocialPlatform = (typeof SOCIAL_PLATFORMS)[number];

export type LinkLayout = "list" | "grid-2" | "grid-3";

export interface ProfileLink {
  id: string;
  title: string;
  url: string;
  imageUrl: string;
  order: number;
}

export interface SocialLink {
  id: string;
  platform: SocialPlatform;
  url: string;
}

export interface ProfileData {
  avatarUrl: string | null;
  backgroundUrl: string | null;
  backgroundColor: string | null;
  slug: string;
  nickname: string;
  bio: string;
  links: ProfileLink[];
  socials: SocialLink[];
  linkLayout: LinkLayout;
  colorTheme: "teal" | "orange" | "blue" | "violet" | "yellow" | "red" | "white" | "default" | "custom";
  customPrimaryColor: string | null;
  customSecondaryColor: string | null;
  darkMode: boolean;
}

export const DEFAULT_PROFILE: ProfileData = {
  avatarUrl: null,
  backgroundUrl: null,
  backgroundColor: null,
  slug: "",
  nickname: "",
  bio: "",
  links: [],
  socials: [],
  linkLayout: "list",
  colorTheme: "default",
  customPrimaryColor: null,
  customSecondaryColor: null,
  darkMode: false,
};

export const SOCIAL_PLATFORM_LABELS: Record<SocialPlatform, string> = {
  instagram: "Instagram",
  youtube: "YouTube",
  x: "X (Twitter)",
  tiktok: "TikTok",
  linkedin: "LinkedIn",
  website: "웹사이트",
};
