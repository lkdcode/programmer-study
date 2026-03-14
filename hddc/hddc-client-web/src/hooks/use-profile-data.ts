"use client";

import { useState, useEffect, useCallback, useRef } from "react";
import {
  type ProfileData,
  type ProfileLink,
  type SocialLink,
  type SocialPlatform,
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
    setSaveStatus("saving");
    clearTimeout(timerRef.current);
    timerRef.current = setTimeout(() => {
      saveToStorage(profileData);
      setSaveStatus("saved");
    }, DEBOUNCE_MS);
    return () => clearTimeout(timerRef.current);
  }, [profileData]);

  const updateProfile = useCallback(
    (fields: Partial<Pick<ProfileData, "avatarUrl" | "nickname" | "bio">>) => {
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
        order: prev.links.length,
      };
      return { ...prev, links: [...prev.links, newLink] };
    });
  }, []);

  const updateLink = useCallback(
    (id: string, fields: Partial<Pick<ProfileLink, "title" | "url">>) => {
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
    saveNow,
  };
}
