import { SiteHeader } from "@/components/site-header";
import { ThemeToggle } from "@/components/theme-toggle";
import { Fab } from "@/components/fab";

export default function PublicLayout({ children }: { children: React.ReactNode }) {
  return (
    <>
      <SiteHeader>
        <ThemeToggle />
      </SiteHeader>
      {children}
      <Fab />
    </>
  );
}
