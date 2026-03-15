import { SiteHeader } from "@/components/site-header";
import { ColorThemePicker } from "@/components/color-theme-picker";
import { ThemeToggle } from "@/components/theme-toggle";
import { Fab } from "@/components/fab";

export default function PublicLayout({ children }: { children: React.ReactNode }) {
  return (
    <>
      <SiteHeader>
        <ColorThemePicker />
        <ThemeToggle />
      </SiteHeader>
      {children}
      <Fab />
    </>
  );
}
