"use client";

import { useState, useEffect, useCallback, useRef } from "react";
import {
  type ProfileData,
  type ProfileLink,
  type SocialLink,
  type SocialPlatform,
  type LinkLayout,
  type LinkStyle,
  type FontFamily,
  type HeaderLayout,
  type LinkAnimation,
  DEFAULT_PROFILE,
} from "@/lib/profile-types";
import { useHistory } from "./use-history";

const STORAGE_KEY = "hddc-profile-data";
const DEBOUNCE_MS = 1000;
const HISTORY_DEBOUNCE_MS = 500;

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
  const historyTimerRef = useRef<ReturnType<typeof setTimeout> | undefined>(undefined);
  const initialized = useRef(false);
  const skipHistoryRef = useRef(false);
  const lastSnapshotRef = useRef<ProfileData>(DEFAULT_PROFILE);

  const history = useHistory<ProfileData>(30);

  useEffect(() => {
    const data = loadFromStorage();
    setProfileData(data);
    lastSnapshotRef.current = data;
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

  // Snapshot immediately before structural changes
  const snapshot = useCallback(() => {
    history.push(lastSnapshotRef.current);
    lastSnapshotRef.current = profileData;
  }, [history, profileData]);

  // Debounced snapshot for text edits (coalesce rapid typing)
  const debouncedSnapshot = useCallback(() => {
    clearTimeout(historyTimerRef.current);
    const before = lastSnapshotRef.current;
    historyTimerRef.current = setTimeout(() => {
      if (before !== lastSnapshotRef.current) return; // already snapshotted
      history.push(before);
      lastSnapshotRef.current = profileData;
    }, HISTORY_DEBOUNCE_MS);
  }, [history, profileData]);

  // Wrapped setProfileData that tracks history
  const setWithHistory = useCallback(
    (updater: (prev: ProfileData) => ProfileData, immediate = false) => {
      if (skipHistoryRef.current) {
        setProfileData(updater);
        return;
      }
      if (immediate) snapshot();
      else debouncedSnapshot();
      setProfileData(updater);
    },
    [snapshot, debouncedSnapshot],
  );

  const undoProfile = useCallback(() => {
    const prev = history.undo(profileData);
    if (prev) {
      skipHistoryRef.current = true;
      setProfileData(prev);
      lastSnapshotRef.current = prev;
      skipHistoryRef.current = false;
    }
  }, [history, profileData]);

  const redoProfile = useCallback(() => {
    const next = history.redo(profileData);
    if (next) {
      skipHistoryRef.current = true;
      setProfileData(next);
      lastSnapshotRef.current = next;
      skipHistoryRef.current = false;
    }
  }, [history, profileData]);

  // --- Actions ---

  const updateProfile = useCallback(
    (fields: Partial<Pick<ProfileData, "avatarUrl" | "backgroundUrl" | "slug" | "nickname" | "bio">>) => {
      setWithHistory((prev) => ({ ...prev, ...fields }));
    },
    [setWithHistory],
  );

  const addLink = useCallback(() => {
    setWithHistory((prev) => {
      if (prev.links.length >= 20) return prev;
      const newLink: ProfileLink = {
        id: crypto.randomUUID(),
        title: "",
        url: "",
        imageUrl: "",
        description: "",
        order: prev.links.length,
        enabled: true,
      };
      return { ...prev, links: [...prev.links, newLink] };
    }, true);
  }, [setWithHistory]);

  const updateLink = useCallback(
    (id: string, fields: Partial<Pick<ProfileLink, "title" | "url" | "imageUrl" | "description">>) => {
      setWithHistory((prev) => ({
        ...prev,
        links: prev.links.map((l) => (l.id === id ? { ...l, ...fields } : l)),
      }));
    },
    [setWithHistory],
  );

  const removeLink = useCallback((id: string) => {
    setWithHistory((prev) => ({
      ...prev,
      links: prev.links
        .filter((l) => l.id !== id)
        .map((l, i) => ({ ...l, order: i })),
    }), true);
  }, [setWithHistory]);

  const toggleLink = useCallback((id: string) => {
    setWithHistory((prev) => ({
      ...prev,
      links: prev.links.map((l) =>
        l.id === id ? { ...l, enabled: !l.enabled } : l
      ),
    }), true);
  }, [setWithHistory]);

  const moveLink = useCallback((id: string, direction: "up" | "down") => {
    setWithHistory((prev) => {
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
    }, true);
  }, [setWithHistory]);

  const addSocial = useCallback((platform: SocialPlatform) => {
    setWithHistory((prev) => {
      if (prev.socials.length >= 8) return prev;
      if (prev.socials.some((s) => s.platform === platform)) return prev;
      const newSocial: SocialLink = {
        id: crypto.randomUUID(),
        platform,
        url: "",
      };
      return { ...prev, socials: [...prev.socials, newSocial] };
    }, true);
  }, [setWithHistory]);

  const updateSocial = useCallback((id: string, url: string) => {
    setWithHistory((prev) => ({
      ...prev,
      socials: prev.socials.map((s) => (s.id === id ? { ...s, url } : s)),
    }));
  }, [setWithHistory]);

  const removeSocial = useCallback((id: string) => {
    setWithHistory((prev) => ({
      ...prev,
      socials: prev.socials.filter((s) => s.id !== id),
    }), true);
  }, [setWithHistory]);

  const reorderSocials = useCallback((activeId: string, overId: string) => {
    setWithHistory((prev) => {
      const oldIndex = prev.socials.findIndex((s) => s.id === activeId);
      const newIndex = prev.socials.findIndex((s) => s.id === overId);
      if (oldIndex === -1 || newIndex === -1) return prev;
      const newSocials = [...prev.socials];
      const [removed] = newSocials.splice(oldIndex, 1);
      newSocials.splice(newIndex, 0, removed);
      return { ...prev, socials: newSocials };
    }, true);
  }, [setWithHistory]);

  const setColorTheme = useCallback(
    (theme: ProfileData["colorTheme"]) => {
      setWithHistory((prev) => ({ ...prev, colorTheme: theme }), true);
    },
    [setWithHistory],
  );

  const setDarkMode = useCallback((dark: boolean) => {
    setWithHistory((prev) => ({ ...prev, darkMode: dark }), true);
  }, [setWithHistory]);

  const setLinkLayout = useCallback((layout: LinkLayout) => {
    setWithHistory((prev) => ({ ...prev, linkLayout: layout }), true);
  }, [setWithHistory]);

  const setLinkStyle = useCallback((style: LinkStyle) => {
    setWithHistory((prev) => ({ ...prev, linkStyle: style }), true);
  }, [setWithHistory]);

  const setFontFamily = useCallback((font: FontFamily) => {
    setWithHistory((prev) => ({ ...prev, fontFamily: font }), true);
  }, [setWithHistory]);

  const setHeaderLayout = useCallback((layout: HeaderLayout) => {
    setWithHistory((prev) => ({ ...prev, headerLayout: layout }), true);
  }, [setWithHistory]);

  const setLinkAnimation = useCallback((anim: LinkAnimation) => {
    setWithHistory((prev) => ({ ...prev, linkAnimation: anim }), true);
  }, [setWithHistory]);

  const setBackgroundColor = useCallback((color: string | null) => {
    setWithHistory((prev) => ({ ...prev, backgroundColor: color }), true);
  }, [setWithHistory]);

  const setFontColor = useCallback((color: string | null) => {
    setWithHistory((prev) => ({ ...prev, fontColor: color }), true);
  }, [setWithHistory]);

  const setCustomColors = useCallback((primary: string, secondary: string) => {
    setWithHistory((prev) => ({ ...prev, customPrimaryColor: primary, customSecondaryColor: secondary }), true);
  }, [setWithHistory]);

  const reorderLinks = useCallback((activeId: string, overId: string) => {
    setWithHistory((prev) => {
      const oldIndex = prev.links.findIndex((l) => l.id === activeId);
      const newIndex = prev.links.findIndex((l) => l.id === overId);
      if (oldIndex === -1 || newIndex === -1) return prev;
      const newLinks = [...prev.links];
      const [removed] = newLinks.splice(oldIndex, 1);
      newLinks.splice(newIndex, 0, removed);
      return { ...prev, links: newLinks.map((l, i) => ({ ...l, order: i })) };
    }, true);
  }, [setWithHistory]);

  const resetProfile = useCallback(() => {
    snapshot();
    setProfileData(DEFAULT_PROFILE);
    lastSnapshotRef.current = DEFAULT_PROFILE;
    clearTimeout(timerRef.current);
    saveToStorage(DEFAULT_PROFILE);
  }, [snapshot]);

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
    toggleLink,
    moveLink,
    addSocial,
    updateSocial,
    removeSocial,
    reorderSocials,
    setColorTheme,
    setDarkMode,
    setLinkLayout,
    setLinkStyle,
    setFontFamily,
    setHeaderLayout,
    setLinkAnimation,
    setBackgroundColor,
    setFontColor,
    setCustomColors,
    reorderLinks,
    resetProfile,
    saveNow,
    undoProfile,
    redoProfile,
    canUndo: history.canUndo,
    canRedo: history.canRedo,
  };
}
