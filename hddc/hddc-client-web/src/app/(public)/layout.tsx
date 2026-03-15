import Link from "next/link";
import { SiteHeader } from "@/components/site-header";
import { ThemeToggle } from "@/components/theme-toggle";
import { Button } from "@/components/ui/button";
import { Fab } from "@/components/fab";

export default function PublicLayout({ children }: { children: React.ReactNode }) {
  return (
    <>
      <SiteHeader
        userMenu={
          <div className="flex items-center gap-2">
            <Button variant="ghost" size="sm" asChild>
              <Link href="/auth/login">로그인</Link>
            </Button>
            <Button size="sm" asChild>
              <Link href="/auth/signup">회원가입</Link>
            </Button>
          </div>
        }
      >
        <ThemeToggle />
      </SiteHeader>
      {children}
      <Fab />
    </>
  );
}
