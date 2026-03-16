"use client";

import { useState, useMemo } from "react";
import { Fire, CaretLeft, CaretRight, MagnifyingGlass, X } from "@phosphor-icons/react";
import { Button } from "@/components/ui/button";
import { ToggleGroup } from "@/components/ui/toggle-group";
import { SiteFooter } from "@/components/site-footer";
import { DealCard } from "@/components/hot-deals/deal-card";
import { SponsorAd } from "@/components/hot-deals/sponsor-ad";
import { ProfileCard } from "@/components/hot-deals/profile-card";
import { buildFeed, type HotDeal, type FeedProfile } from "@/lib/hot-deal-types";

/* ─── Mock data ─── */

const MOCK_DEALS: HotDeal[] = [
  {
    id: "1",
    title: "Apple 에어팟 프로 2 (USB-C) — 역대 최저가",
    description: "액티브 노이즈 캔슬링, 적응형 오디오, 맞춤형 공간 음향",
    imageUrl: "",
    originalPrice: 299000,
    salePrice: 199000,
    discountRate: 33,
    url: "#",
    source: "쿠팡",
    category: "electronics",
    postedAt: new Date(Date.now() - 2 * 60 * 60 * 1000).toISOString(),
    likes: 124,
    comments: 18,
  },
  {
    id: "2",
    title: "맥북 에어 M3 15인치 16GB/512GB",
    description: "교육 할인 추가 적용 가능",
    imageUrl: "",
    originalPrice: 1890000,
    salePrice: 1590000,
    discountRate: 16,
    url: "#",
    source: "Apple 공식",
    category: "electronics",
    postedAt: new Date(Date.now() - 5 * 60 * 60 * 1000).toISOString(),
    likes: 89,
    comments: 12,
  },
  {
    id: "3",
    title: "삼성 갤럭시 S25 울트라 자급제",
    description: "사전예약 혜택 포함, 512GB 모델",
    imageUrl: "",
    originalPrice: 1698400,
    salePrice: 1350000,
    discountRate: 21,
    url: "#",
    source: "11번가",
    category: "electronics",
    postedAt: new Date(Date.now() - 8 * 60 * 60 * 1000).toISOString(),
    likes: 203,
    comments: 45,
  },
  {
    id: "4",
    title: "다이슨 에어랩 멀티 스타일러 컴플리트 롱",
    description: "새로운 컬러 리미티드 에디션",
    imageUrl: "",
    originalPrice: 699000,
    salePrice: 549000,
    discountRate: 21,
    url: "#",
    source: "네이버쇼핑",
    category: "electronics",
    postedAt: new Date(Date.now() - 12 * 60 * 60 * 1000).toISOString(),
    likes: 67,
    comments: 8,
  },
  {
    id: "5",
    title: "LG 그램 2025 16인치 Ultra 7",
    description: "980g 초경량, 배터리 최대 25시간",
    imageUrl: "",
    originalPrice: 1990000,
    salePrice: 1490000,
    discountRate: 25,
    url: "#",
    source: "LG 공식",
    category: "electronics",
    postedAt: new Date(Date.now() - 1 * 24 * 60 * 60 * 1000).toISOString(),
    likes: 56,
    comments: 7,
  },
  {
    id: "6",
    title: "나이키 에어맥스 97 — 언더리테일",
    description: "한정 컬러 블랙/화이트/실버",
    imageUrl: "",
    originalPrice: 199000,
    salePrice: 129000,
    discountRate: 35,
    url: "#",
    source: "무신사",
    category: "fashion",
    postedAt: new Date(Date.now() - 1.5 * 24 * 60 * 60 * 1000).toISOString(),
    likes: 312,
    comments: 28,
  },
  {
    id: "7",
    title: "닌텐도 스위치 2 예약판매 시작",
    description: "2025년 출시, 마리오카트 번들",
    imageUrl: "",
    originalPrice: 449000,
    salePrice: 449000,
    discountRate: 0,
    url: "#",
    source: "아마존 JP",
    category: "electronics",
    postedAt: new Date(Date.now() - 2 * 24 * 60 * 60 * 1000).toISOString(),
    likes: 445,
    comments: 92,
  },
  {
    id: "8",
    title: "소니 WH-1000XM5 무선 헤드폰",
    description: "최고의 노이즈 캔슬링, 30시간 배터리",
    imageUrl: "",
    originalPrice: 459000,
    salePrice: 289000,
    discountRate: 37,
    url: "#",
    source: "쿠팡",
    category: "electronics",
    postedAt: new Date(Date.now() - 2.5 * 24 * 60 * 60 * 1000).toISOString(),
    likes: 178,
    comments: 23,
  },
  {
    id: "9",
    title: "곰곰 1+ 등급 한우 채끝 스테이크 300g",
    description: "로켓프레시 새벽배송",
    imageUrl: "",
    originalPrice: 39900,
    salePrice: 29900,
    discountRate: 25,
    url: "#",
    source: "쿠팡",
    category: "food",
    postedAt: new Date(Date.now() - 3 * 24 * 60 * 60 * 1000).toISOString(),
    likes: 34,
    comments: 5,
  },
  {
    id: "10",
    title: "아이로봇 룸바 j9+ 로봇청소기",
    description: "자동 먼지 비움, 장애물 회피",
    imageUrl: "",
    originalPrice: 1290000,
    salePrice: 890000,
    discountRate: 31,
    url: "#",
    source: "G마켓",
    category: "living",
    postedAt: new Date(Date.now() - 3.5 * 24 * 60 * 60 * 1000).toISOString(),
    likes: 91,
    comments: 14,
  },
  {
    id: "11",
    title: "유니클로 울트라라이트다운 베스트",
    description: "겨울 마지막 시즌오프 50%",
    imageUrl: "",
    originalPrice: 79900,
    salePrice: 39900,
    discountRate: 50,
    url: "#",
    source: "유니클로",
    category: "fashion",
    postedAt: new Date(Date.now() - 4 * 24 * 60 * 60 * 1000).toISOString(),
    likes: 267,
    comments: 31,
  },
  {
    id: "12",
    title: "스타벅스 e-카드 5만원권",
    description: "카카오페이 결제 시 10% 추가 할인",
    imageUrl: "",
    originalPrice: 50000,
    salePrice: 42500,
    discountRate: 15,
    url: "#",
    source: "카카오딜",
    category: "food",
    postedAt: new Date(Date.now() - 5 * 24 * 60 * 60 * 1000).toISOString(),
    likes: 156,
    comments: 19,
  },
];

const MOCK_PROFILES: FeedProfile[] = [
  { slug: "techdeals", nickname: "테크딜러", bio: "IT/전자기기 핫딜만 큐레이션합니다", avatarUrl: null },
  { slug: "fashionhunter", nickname: "패션헌터", bio: "무신사/지그재그/에이블리 특가 모음", avatarUrl: null },
];

type SortKey = "latest" | "popular" | "discount";

function sortDeals(deals: HotDeal[], key: SortKey): HotDeal[] {
  const sorted = [...deals];
  switch (key) {
    case "latest":
      return sorted.sort((a, b) => new Date(b.postedAt).getTime() - new Date(a.postedAt).getTime());
    case "popular":
      return sorted.sort((a, b) => b.likes - a.likes);
    case "discount":
      return sorted.sort((a, b) => b.discountRate - a.discountRate);
  }
}

const PAGE_SIZE = 10;

export default function HotDealsPage() {
  const [sortKey, setSortKey] = useState<SortKey>("latest");
  const [page, setPage] = useState(1);
  const [query, setQuery] = useState("");

  const filtered = useMemo(() => {
    if (!query.trim()) return MOCK_DEALS;
    const q = query.trim().toLowerCase();
    return MOCK_DEALS.filter(
      (d) => d.title.toLowerCase().includes(q) || d.description.toLowerCase().includes(q) || d.source.toLowerCase().includes(q),
    );
  }, [query]);

  const sorted = useMemo(() => sortDeals(filtered, sortKey), [filtered, sortKey]);
  const totalPages = Math.max(1, Math.ceil(sorted.length / PAGE_SIZE));
  const safePage = Math.min(page, totalPages);
  const paged = useMemo(() => sorted.slice((safePage - 1) * PAGE_SIZE, safePage * PAGE_SIZE), [sorted, safePage]);
  const feed = useMemo(() => buildFeed(paged, MOCK_PROFILES), [paged]);

  function goTo(p: number) {
    setPage(p);
    window.scrollTo({ top: 0, behavior: "smooth" });
  }

  return (
    <div className="flex min-h-full flex-col">
      <div className="mx-auto w-full max-w-3xl flex-1 px-4 py-8 sm:px-6">
        {/* Header */}
        <div className="mb-6 flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
          <div className="flex items-center gap-2">
            <Fire className="size-6 text-red-500" weight="fill" />
            <h1 className="text-2xl font-bold tracking-tight">핫딜</h1>
          </div>
          <ToggleGroup
            variant="pill"
            size="sm"
            value={sortKey}
            onValueChange={(v) => { setSortKey(v); setPage(1); }}
            options={[
              { value: "latest" as const, label: "최신순" },
              { value: "popular" as const, label: "인기순" },
              { value: "discount" as const, label: "할인율순" },
            ]}
          />
        </div>

        {/* Search */}
        <div className="relative mb-4">
          <MagnifyingGlass className="absolute left-3 top-1/2 size-4 -translate-y-1/2 text-muted-foreground" />
          <input
            type="text"
            placeholder="상품명, 출처로 검색..."
            value={query}
            onChange={(e) => { setQuery(e.target.value); setPage(1); }}
            className="h-10 w-full rounded-lg border border-border bg-background pl-9 pr-9 text-sm outline-none transition-colors placeholder:text-muted-foreground focus:border-primary focus:ring-2 focus:ring-primary/30"
          />
          {query && (
            <button
              onClick={() => { setQuery(""); setPage(1); }}
              className="absolute right-3 top-1/2 -translate-y-1/2 cursor-pointer text-muted-foreground transition-colors hover:text-foreground"
            >
              <X className="size-4" />
            </button>
          )}
        </div>

        {/* Feed */}
        {sorted.length === 0 ? (
          <div className="flex flex-col items-center gap-2 py-16 text-center">
            <MagnifyingGlass className="size-10 text-muted-foreground/30" />
            <p className="text-sm text-muted-foreground">
              &ldquo;{query}&rdquo;에 대한 검색 결과가 없습니다
            </p>
          </div>
        ) : (
          <div className="flex flex-col gap-3">
            {feed.map((item, i) => {
              switch (item.type) {
                case "deal":
                  return <DealCard key={item.data.id} deal={item.data} />;
                case "sponsor":
                  return <SponsorAd key={`sponsor-${i}`} />;
                case "profile":
                  return <ProfileCard key={item.data.slug} profile={item.data} />;
              }
            })}
          </div>
        )}

        {/* Pagination */}
        {totalPages > 1 && (
          <div className="mt-8 flex items-center justify-center gap-1">
            <Button
              variant="ghost"
              size="icon-sm"
              onClick={() => goTo(page - 1)}
              disabled={page <= 1}
            >
              <CaretLeft className="size-4" />
            </Button>
            {Array.from({ length: totalPages }, (_, i) => i + 1).map((p) => (
              <Button
                key={p}
                variant={p === page ? "default" : "ghost"}
                size="sm"
                className="size-8 p-0 text-xs"
                onClick={() => goTo(p)}
              >
                {p}
              </Button>
            ))}
            <Button
              variant="ghost"
              size="icon-sm"
              onClick={() => goTo(page + 1)}
              disabled={page >= totalPages}
            >
              <CaretRight className="size-4" />
            </Button>
          </div>
        )}
      </div>

      <SiteFooter />
    </div>
  );
}
