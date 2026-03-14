"use client";

import { useState, useEffect, useCallback, useRef } from "react";
import {
  type ProfileData,
  type ProfileLink,
  type SocialLink,
  type SocialPlatform,
  type LinkLayout,
  DEFAULT_PROFILE,
} from "@/lib/profile-types";

const STORAGE_KEY = "hddc-profile-data";
const DEBOUNCE_MS = 1000;

type SaveStatus = "idle" | "saving" | "saved";

function loadFromStorage(): ProfileData {
  try {
    const raw = localStorage.getItem(STORAGE_KEY);
    if (!raw) return DEFAULT_PROFILE;
    return { ...DEFAULT_PROFILE, ...JSON.parse(raw) };
  } catch {
    return DEFAULT_PROFILE;
  }
}

function saveToStorage(data: ProfileData) {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(data));
}

export function useProfileData() {
  const [profileData, setProfileData] = useState<ProfileData>(DEFAULT_PROFILE);
  const [saveStatus, setSaveStatus] = useState<SaveStatus>("idle");
  const timerRef = useRef<ReturnType<typeof setTimeout> | undefined>(undefined);
  const initialized = useRef(false);

  useEffect(() => {
    setProfileData(loadFromStorage());
    initialized.current = true;
  }, []);

  useEffect(() => {
    if (!initialized.current) return;
    clearTimeout(timerRef.current);
    timerRef.current = setTimeout(() => {
      setSaveStatus("saving");
      saveToStorage(profileData);
      setTimeout(() => setSaveStatus("saved"), 300);
    }, DEBOUNCE_MS);
    return () => clearTimeout(timerRef.current);
  }, [profileData]);

  const updateProfile = useCallback(
    (fields: Partial<Pick<ProfileData, "avatarUrl" | "backgroundUrl" | "slug" | "nickname" | "bio">>) => {
      setProfileData((prev) => ({ ...prev, ...fields }));
    },
    [],
  );

  const addLink = useCallback(() => {
    setProfileData((prev) => {
      if (prev.links.length >= 20) return prev;
      const newLink: ProfileLink = {
        id: crypto.randomUUID(),
        title: "",
        url: "",
        imageUrl: "",
        order: prev.links.length,
      };
      return { ...prev, links: [...prev.links, newLink] };
    });
  }, []);

  const updateLink = useCallback(
    (id: string, fields: Partial<Pick<ProfileLink, "title" | "url" | "imageUrl">>) => {
      setProfileData((prev) => ({
        ...prev,
        links: prev.links.map((l) => (l.id === id ? { ...l, ...fields } : l)),
      }));
    },
    [],
  );

  const removeLink = useCallback((id: string) => {
    setProfileData((prev) => ({
      ...prev,
      links: prev.links
        .filter((l) => l.id !== id)
        .map((l, i) => ({ ...l, order: i })),
    }));
  }, []);

  const moveLink = useCallback((id: string, direction: "up" | "down") => {
    setProfileData((prev) => {
      const idx = prev.links.findIndex((l) => l.id === id);
      if (idx === -1) return prev;
      const newIdx = direction === "up" ? idx - 1 : idx + 1;
      if (newIdx < 0 || newIdx >= prev.links.length) return prev;
      const newLinks = [...prev.links];
      [newLinks[idx], newLinks[newIdx]] = [newLinks[newIdx], newLinks[idx]];
      return {
        ...prev,
        links: newLinks.map((l, i) => ({ ...l, order: i })),
      };
    });
  }, []);

  const addSocial = useCallback((platform: SocialPlatform) => {
    setProfileData((prev) => {
      if (prev.socials.length >= 8) return prev;
      if (prev.socials.some((s) => s.platform === platform)) return prev;
      const newSocial: SocialLink = {
        id: crypto.randomUUID(),
        platform,
        url: "",
      };
      return { ...prev, socials: [...prev.socials, newSocial] };
    });
  }, []);

  const updateSocial = useCallback((id: string, url: string) => {
    setProfileData((prev) => ({
      ...prev,
      socials: prev.socials.map((s) => (s.id === id ? { ...s, url } : s)),
    }));
  }, []);

  const removeSocial = useCallback((id: string) => {
    setProfileData((prev) => ({
      ...prev,
      socials: prev.socials.filter((s) => s.id !== id),
    }));
  }, []);

  const setColorTheme = useCallback(
    (theme: ProfileData["colorTheme"]) => {
      setProfileData((prev) => ({ ...prev, colorTheme: theme }));
    },
    [],
  );

  const setDarkMode = useCallback((dark: boolean) => {
    setProfileData((prev) => ({ ...prev, darkMode: dark }));
  }, []);

  const setLinkLayout = useCallback((layout: LinkLayout) => {
    setProfileData((prev) => ({ ...prev, linkLayout: layout }));
  }, []);

  const setBackgroundColor = useCallback((color: string | null) => {
    setProfileData((prev) => ({ ...prev, backgroundColor: color }));
  }, []);

  const setCustomColors = useCallback((primary: string, secondary: string) => {
    setProfileData((prev) => ({ ...prev, customPrimaryColor: primary, customSecondaryColor: secondary }));
  }, []);

  const reorderLinks = useCallback((activeId: string, overId: string) => {
    setProfileData((prev) => {
      const oldIndex = prev.links.findIndex((l) => l.id === activeId);
      const newIndex = prev.links.findIndex((l) => l.id === overId);
      if (oldIndex === -1 || newIndex === -1) return prev;
      const newLinks = [...prev.links];
      const [removed] = newLinks.splice(oldIndex, 1);
      newLinks.splice(newIndex, 0, removed);
      return { ...prev, links: newLinks.map((l, i) => ({ ...l, order: i })) };
    });
  }, []);

  const saveNow = useCallback(() => {
    clearTimeout(timerRef.current);
    setSaveStatus("saving");
    saveToStorage(profileData);
    setTimeout(() => setSaveStatus("saved"), 300);
  }, [profileData]);

  return {
    profileData,
    saveStatus,
    updateProfile,
    addLink,
    updateLink,
    removeLink,
    moveLink,
    addSocial,
    updateSocial,
    removeSocial,
    setColorTheme,
    setDarkMode,
    setLinkLayout,
    setBackgroundColor,
    setCustomColors,
    reorderLinks,
    saveNow,
  };
}
