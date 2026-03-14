import { Globe } from "@phosphor-icons/react";
import { cn } from "@/lib/utils";

export function PhoneMockup({
  onClick,
  className,
}: {
  onClick?: () => void;
  className?: string;
}) {
  return (
    <div onClick={onClick} className={cn("relative", className)}>
      <div className="rounded-[1.75rem] border-2 border-border bg-card p-2 shadow-lg">
        <div className="mx-auto mb-2 h-4 w-16 rounded-full bg-border/60" />
        <div className="rounded-[1.25rem] bg-background p-3">
          <div className="flex flex-col items-center gap-2 pb-3">
            <div className="flex size-10 items-center justify-center rounded-full bg-primary/10 text-sm font-bold text-primary">
              핫
            </div>
            <div className="text-center">
              <p className="text-xs font-semibold">핫딜닷쿨</p>
              <p className="text-[10px] text-muted-foreground">크리에이터</p>
            </div>
          </div>
          <div className="flex flex-col gap-1.5">
            {["추천 상품", "리뷰 블로그", "제휴 파트너스"].map((label) => (
              <div
                key={label}
                className="flex h-7 items-center justify-center rounded-lg bg-muted/60 text-[10px] font-medium"
              >
                {label}
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export function BrowserMockup({
  onClick,
  className,
}: {
  onClick?: () => void;
  className?: string;
}) {
  return (
    <div onClick={onClick} className={cn("relative", className)}>
      <div className="rounded-xl border-2 border-border bg-card shadow-lg">
        <div className="flex items-center gap-1.5 border-b border-border px-3 py-2">
          <div className="flex gap-1">
            <span className="size-2 rounded-full bg-red-400/70" />
            <span className="size-2 rounded-full bg-yellow-400/70" />
            <span className="size-2 rounded-full bg-green-400/70" />
          </div>
          <div className="ml-2 flex-1 rounded-md bg-muted/60 px-2 py-0.5 text-[9px] text-muted-foreground">
            hotdeal.cool/yourname
          </div>
        </div>
        <div className="p-3">
          <div className="grid grid-cols-[auto_1fr] gap-3">
            <div className="flex flex-col items-center gap-2 border-r border-border pr-3">
              <div className="flex size-8 items-center justify-center rounded-full bg-primary/10 text-xs font-bold text-primary">
                핫
              </div>
              <p className="text-[9px] font-semibold">핫딜닷쿨</p>
              <p className="text-[8px] text-muted-foreground">크리에이터</p>
            </div>
            <div className="flex flex-col gap-1.5">
              {["추천 상품", "리뷰 블로그", "제휴 파트너스"].map((label) => (
                <div
                  key={label}
                  className="flex h-6 items-center rounded-md bg-muted/60 px-2 text-[9px] font-medium"
                >
                  <Globe className="mr-1 size-3 text-muted-foreground" />
                  {label}
                </div>
              ))}
              <div className="mt-1 rounded-md bg-primary/5 p-2">
                <p className="mb-1 text-[8px] font-medium text-muted-foreground">
                  클릭 통계
                </p>
                <div className="flex gap-1">
                  {[60, 40, 80].map((h, i) => (
                    <div
                      key={i}
                      className="flex-1 rounded-sm bg-primary/20"
                      style={{ height: `${h * 0.2}px` }}
                    />
                  ))}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
