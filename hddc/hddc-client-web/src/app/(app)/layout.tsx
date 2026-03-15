import { SiteHeader } from "@/components/site-header";
import { ThemeToggle } from "@/components/theme-toggle";
import { IconSidebar } from "@/components/icon-sidebar";
import { Fab } from "@/components/fab";

export default function AppLayout({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex min-h-svh flex-col">
      <SiteHeader maxWidth="max-w-full">
        <ThemeToggle />
      </SiteHeader>
      <div className="flex flex-1">
        <IconSidebar />
        <main className="flex flex-1 flex-col">{children}</main>
      </div>
      <Fab />
    </div>
  );
}
