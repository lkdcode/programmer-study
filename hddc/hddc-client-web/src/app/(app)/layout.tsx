"use client";

import { useRouter } from "next/navigation";
import { SiteHeader } from "@/components/site-header";
import { ThemeToggle } from "@/components/theme-toggle";
import { IconSidebar } from "@/components/icon-sidebar";
import { Fab } from "@/components/fab";
import { Button } from "@/components/ui/button";
import { SignOut } from "@phosphor-icons/react";
import { SiteFooter } from "@/components/site-footer";

export default function AppLayout({ children }: { children: React.ReactNode }) {
  const router = useRouter();

  function handleLogout() {
    localStorage.removeItem("hddc-auth");
    router.push("/auth/login");
  }

  return (
    <div className="flex h-svh flex-col">
      <SiteHeader
        maxWidth="max-w-full"
        userMenu={
          <Button variant="ghost" size="icon" onClick={handleLogout} aria-label="로그아웃">
            <SignOut className="size-4" />
          </Button>
        }
      >
        <ThemeToggle />
      </SiteHeader>
      <div className="flex flex-1 overflow-hidden">
        <IconSidebar />
        <main className="flex flex-1 flex-col overflow-y-auto">
          {children}
          <SiteFooter />
        </main>
      </div>
      <Fab />
    </div>
  );
}
