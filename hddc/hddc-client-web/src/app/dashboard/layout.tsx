"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { SiteHeader } from "@/components/site-header";
import { ThemeToggle } from "@/components/theme-toggle";
import { Button } from "@/components/ui/button";
import { SignOut } from "@phosphor-icons/react";

export default function DashboardLayout({ children }: { children: React.ReactNode }) {
  const router = useRouter();
  const [authed, setAuthed] = useState(false);

  useEffect(() => {
    const flag = localStorage.getItem("hddc-auth");
    if (!flag) {
      router.replace("/auth/login");
    } else {
      setAuthed(true);
    }
  }, [router]);

  function handleLogout() {
    localStorage.removeItem("hddc-auth");
    router.push("/auth/login");
  }

  if (!authed) {
    return (
      <div className="flex min-h-svh items-center justify-center">
        <p className="text-sm text-muted-foreground">로딩 중...</p>
      </div>
    );
  }

  return (
    <div className="flex min-h-svh flex-col bg-muted/30">
      <SiteHeader
        maxWidth="max-w-6xl"
        nav={<span className="text-sm font-medium text-foreground">편집</span>}
      >
        <ThemeToggle />
        <Button variant="ghost" size="icon" onClick={handleLogout} aria-label="로그아웃">
          <SignOut className="size-4" />
        </Button>
      </SiteHeader>
      <main className="flex flex-1 flex-col">{children}</main>
    </div>
  );
}
