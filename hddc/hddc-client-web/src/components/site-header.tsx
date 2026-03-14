import Link from "next/link";
import { cn } from "@/lib/utils";

interface SiteHeaderProps {
  nav?: React.ReactNode;
  children?: React.ReactNode;
  maxWidth?: string;
}

export function SiteHeader({ nav, children, maxWidth = "max-w-5xl" }: SiteHeaderProps) {
  return (
    <header className="sticky top-0 z-50 border-b border-border bg-background/80 backdrop-blur-md">
      <nav className={cn("mx-auto flex h-14 items-center justify-between px-4 sm:px-6", maxWidth)}>
        <div className="flex items-center gap-6">
          <Link href="/" className="text-lg font-bold tracking-tight">
            핫딜닷쿨
          </Link>
          {nav}
        </div>
        {children && <div className="flex items-center gap-1">{children}</div>}
      </nav>
    </header>
  );
}
