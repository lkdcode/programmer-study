import { Heart, ChatCircle, ArrowSquareOut } from "@phosphor-icons/react/dist/ssr";
import type { HotDeal } from "@/lib/hot-deal-types";

function timeAgo(dateStr: string): string {
  const diff = Date.now() - new Date(dateStr).getTime();
  const mins = Math.floor(diff / 60_000);
  if (mins < 1) return "방금 전";
  if (mins < 60) return `${mins}분 전`;
  const hours = Math.floor(mins / 60);
  if (hours < 24) return `${hours}시간 전`;
  const days = Math.floor(hours / 24);
  if (days < 7) return `${days}일 전`;
  return new Date(dateStr).toLocaleDateString("ko-KR", { month: "short", day: "numeric" });
}

function formatPrice(n: number): string {
  return n.toLocaleString("ko-KR");
}

export function DealCard({ deal }: { deal: HotDeal }) {
  return (
    <a
      href={deal.url}
      target="_blank"
      rel="noopener noreferrer"
      className="group flex gap-4 rounded-xl border border-border bg-card p-4 transition-colors hover:bg-muted/30"
    >
      {/* Thumbnail */}
      <div className="relative size-24 shrink-0 overflow-hidden rounded-lg bg-muted sm:size-28">
        {deal.imageUrl ? (
          <img
            src={deal.imageUrl}
            alt={deal.title}
            className="size-full object-cover"
          />
        ) : (
          <div className="flex size-full items-center justify-center bg-foreground text-sm font-bold text-background">핫딜닷쿨</div>
        )}
        {deal.discountRate > 0 && (
          <span className="absolute left-1.5 top-1.5 rounded-full bg-red-500 px-2 py-0.5 text-[10px] font-bold text-white">
            {deal.discountRate}%
          </span>
        )}
      </div>

      {/* Content */}
      <div className="flex min-w-0 flex-1 flex-col justify-between">
        <div>
          <h3 className="line-clamp-2 text-sm font-semibold leading-snug group-hover:text-primary">
            {deal.title}
          </h3>
          {deal.description && (
            <p className="mt-1 line-clamp-1 text-xs text-muted-foreground">{deal.description}</p>
          )}
        </div>

        {/* Price */}
        <div className="mt-2 flex items-baseline gap-2">
          <span className="text-base font-bold">{formatPrice(deal.salePrice)}원</span>
          {deal.originalPrice > deal.salePrice && (
            <span className="text-xs text-muted-foreground line-through">{formatPrice(deal.originalPrice)}원</span>
          )}
        </div>

        {/* Meta */}
        <div className="mt-1.5 flex items-center gap-3 text-[11px] text-muted-foreground">
          <span>{deal.source}</span>
          <span>·</span>
          <span>{timeAgo(deal.postedAt)}</span>
          <div className="ml-auto flex items-center gap-2.5">
            <span className="flex items-center gap-0.5">
              <Heart className="size-3" />
              {deal.likes}
            </span>
            <span className="flex items-center gap-0.5">
              <ChatCircle className="size-3" />
              {deal.comments}
            </span>
            <ArrowSquareOut className="size-3 opacity-0 transition-opacity group-hover:opacity-100" />
          </div>
        </div>
      </div>
    </a>
  );
}
