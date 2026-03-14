"use client";

import { useState, useEffect, type FormEvent } from "react";
import Link from "next/link";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Checkbox } from "@/components/ui/checkbox";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { PhoneMockup, BrowserMockup } from "@/components/device-mockup";
import { EnvelopeSimple, ArrowCounterClockwise } from "@phosphor-icons/react";
import {
  validateEmail,
  validateNickname,
  validatePassword,
  validatePasswordConfirm,
} from "@/lib/validators";

type Step = "form" | "verify";

export default function SignupPage() {
  const [step, setStep] = useState<Step>("form");
  const [email, setEmail] = useState("");
  const [nickname, setNickname] = useState("");
  const [password, setPassword] = useState("");
  const [passwordConfirm, setPasswordConfirm] = useState("");
  const [agreed, setAgreed] = useState(false);

  const [errors, setErrors] = useState<Record<string, string | null>>({});
  const [touched, setTouched] = useState<Record<string, boolean>>({});

  const [cooldown, setCooldown] = useState(0);
  const [termsOpen, setTermsOpen] = useState(false);
  const [privacyOpen, setPrivacyOpen] = useState(false);

  const colorPresets = ["teal", "orange", "blue", "violet", "yellow", "red"] as const;
  const [presetIndex, setPresetIndex] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setPresetIndex((prev) => (prev + 1) % colorPresets.length);
    }, 3000);
    return () => clearInterval(timer);
  }, [colorPresets.length]);

  // Cooldown timer
  useEffect(() => {
    if (cooldown <= 0) return;
    const timer = setTimeout(() => setCooldown(cooldown - 1), 1000);
    return () => clearTimeout(timer);
  }, [cooldown]);

  function validate(field: string): string | null {
    switch (field) {
      case "email":
        return validateEmail(email);
      case "nickname":
        return validateNickname(nickname);
      case "password":
        return validatePassword(password);
      case "passwordConfirm":
        return validatePasswordConfirm(password, passwordConfirm);
      default:
        return null;
    }
  }

  function handleBlur(field: string) {
    setTouched((prev) => ({ ...prev, [field]: true }));
    setErrors((prev) => ({ ...prev, [field]: validate(field) }));
  }

  function handleSubmit(e: FormEvent) {
    e.preventDefault();

    const fields = ["email", "nickname", "password", "passwordConfirm"];
    const allTouched = Object.fromEntries(fields.map((f) => [f, true]));
    setTouched((prev) => ({ ...prev, ...allTouched }));

    const newErrors: Record<string, string | null> = {};
    for (const field of fields) {
      newErrors[field] = validate(field);
    }
    if (!agreed) {
      newErrors.agreed = "약관에 동의해주세요";
    }
    setErrors(newErrors);

    const hasErrors = Object.values(newErrors).some((e) => e !== null);
    if (hasErrors) return;

    setStep("verify");
  }

  function handleResend() {
    setCooldown(60);
  }

  return (
    <div className="flex min-h-svh items-center justify-center px-4 py-12">
      <div className="w-full max-w-4xl overflow-hidden rounded-2xl border border-border bg-card shadow-lg lg:flex">
        {/* ─── Left: Branding Panel ─── */}

        {/* Desktop: branding with mockups */}
        <div
          data-theme={colorPresets[presetIndex]}
          className="dark hidden lg:flex lg:w-1/2 flex-col items-center justify-center gap-8 rounded-l-2xl bg-gradient-to-br from-[#1a1a2e] to-[#16213e] p-12 text-white transition-colors duration-700"
        >
          <span className="text-2xl font-bold tracking-tight">핫딜닷쿨</span>
          <div className="flex items-end gap-4 sm:gap-6">
            <PhoneMockup className="w-[140px] transition-colors duration-700" />
            <BrowserMockup className="w-[185px] transition-colors duration-700" />
          </div>
          <p className="text-center text-sm leading-relaxed opacity-70">
            하나의 링크,
            <br />
            <span className="text-primary">두 개의 완벽한 뷰</span>
          </p>
        </div>

        {/* Mobile: compact banner */}
        <div className="dark flex items-center justify-center gap-2 rounded-t-2xl bg-gradient-to-r from-[#1a1a2e] to-[#16213e] px-4 py-5 text-white lg:hidden">
          <span className="text-lg font-bold">핫딜닷쿨</span>
          <span className="text-xs opacity-60">·</span>
          <span className="text-xs opacity-70">
            하나의 링크, <span className="text-primary">두 개의 완벽한 뷰</span>
          </span>
        </div>

        {/* ─── Right: Form / Verify ─── */}
        <div className="flex flex-1 items-center justify-center p-8 sm:p-12 lg:w-1/2">
          <div className="w-full max-w-sm">
          {step === "form" ? (
            <form onSubmit={handleSubmit} className="flex flex-col gap-7">
              <div>
                <h1 className="text-2xl font-bold tracking-tight">회원가입</h1>
                <p className="mt-1 text-sm text-muted-foreground">
                  이메일로 시작하기
                </p>
              </div>

              {/* Email */}
              <div className="relative flex flex-col gap-1.5">
                <Label htmlFor="email">이메일</Label>
                <Input
                  id="email"
                  type="email"
                  placeholder="name@example.com"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  onBlur={() => handleBlur("email")}
                  aria-invalid={touched.email && !!errors.email}
                  maxLength={254}
                />
                {touched.email && errors.email && (
                  <p className="absolute -bottom-4 text-xs text-destructive">{errors.email}</p>
                )}
              </div>

              {/* Nickname */}
              <div className="relative flex flex-col gap-1.5">
                <Label htmlFor="nickname">닉네임</Label>
                <Input
                  id="nickname"
                  type="text"
                  placeholder="나만의 이름"
                  value={nickname}
                  onChange={(e) => setNickname(e.target.value)}
                  onBlur={() => handleBlur("nickname")}
                  aria-invalid={touched.nickname && !!errors.nickname}
                  maxLength={20}
                />
                {touched.nickname && errors.nickname && (
                  <p className="absolute -bottom-4 text-xs text-destructive">{errors.nickname}</p>
                )}
              </div>

              {/* Password */}
              <div className="relative flex flex-col gap-1.5">
                <Label htmlFor="password">비밀번호</Label>
                <Input
                  id="password"
                  type="password"
                  placeholder="••••••••"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                  onBlur={() => handleBlur("password")}
                  aria-invalid={touched.password && !!errors.password}
                  maxLength={72}
                />
                {touched.password && errors.password ? (
                  <p className="absolute -bottom-4 text-xs text-destructive">{errors.password}</p>
                ) : (
                  <p className="absolute -bottom-4 text-xs text-muted-foreground">
                    8자 이상, 영문/숫자/특수문자 포함
                  </p>
                )}
              </div>

              {/* Password Confirm */}
              <div className="relative flex flex-col gap-1.5">
                <Label htmlFor="passwordConfirm">비밀번호 확인</Label>
                <Input
                  id="passwordConfirm"
                  type="password"
                  placeholder="••••••••"
                  value={passwordConfirm}
                  onChange={(e) => setPasswordConfirm(e.target.value)}
                  onBlur={() => handleBlur("passwordConfirm")}
                  aria-invalid={
                    touched.passwordConfirm && !!errors.passwordConfirm
                  }
                  maxLength={72}
                />
                {touched.passwordConfirm && errors.passwordConfirm && (
                  <p className="absolute -bottom-4 text-xs text-destructive">
                    {errors.passwordConfirm}
                  </p>
                )}
              </div>

              {/* Terms */}
              <div className="relative flex flex-col gap-1.5">
                <div className="flex items-start gap-2">
                  <Checkbox
                    id="terms"
                    checked={agreed}
                    onCheckedChange={(checked) => setAgreed(checked === true)}
                    aria-invalid={!!errors.agreed}
                  />
                  <label
                    htmlFor="terms"
                    className="text-sm leading-relaxed text-muted-foreground"
                  >
                    <button
                      type="button"
                      onClick={() => setTermsOpen(true)}
                      className="cursor-pointer underline text-foreground"
                    >
                      이용약관
                    </button>{" "}
                    및{" "}
                    <button
                      type="button"
                      onClick={() => setPrivacyOpen(true)}
                      className="cursor-pointer underline text-foreground"
                    >
                      개인정보처리방침
                    </button>
                    에 동의합니다
                  </label>
                </div>
                {errors.agreed && (
                  <p className="absolute -bottom-4 text-xs text-destructive">{errors.agreed}</p>
                )}
              </div>

              {/* Submit */}
              <Button type="submit" className="h-11 text-sm font-semibold">
                가입하기
              </Button>

              {/* Login Link */}
              <p className="text-center text-sm text-muted-foreground">
                이미 계정이 있으신가요?{" "}
                <Link href="/" className="font-semibold underline text-foreground">
                  로그인
                </Link>
              </p>
            </form>
          ) : (
            /* ─── Verify Email Screen ─── */
            <div className="flex flex-col items-center text-center gap-5">
              <div className="flex size-16 items-center justify-center rounded-full bg-primary/10">
                <EnvelopeSimple
                  className="size-8 text-primary"
                  weight="duotone"
                />
              </div>
              <div>
                <h1 className="text-2xl font-bold tracking-tight">
                  인증 메일을 보냈습니다
                </h1>
                <p className="mt-2 text-sm leading-relaxed text-muted-foreground">
                  <span className="font-semibold text-foreground">{email}</span>
                  으로
                  <br />
                  인증 링크를 보내드렸습니다.
                  <br />
                  메일함을 확인해주세요.
                </p>
              </div>

              <Button
                variant="outline"
                className="h-10 gap-2 text-sm"
                onClick={handleResend}
                disabled={cooldown > 0}
              >
                <ArrowCounterClockwise className="size-4" />
                {cooldown > 0
                  ? `${cooldown}초 후 재전송 가능`
                  : "인증 메일 재전송"}
              </Button>

              <Link
                href="/"
                className="text-sm font-semibold underline text-foreground"
              >
                로그인으로 돌아가기
              </Link>
            </div>
          )}
          </div>
        </div>
      </div>

      {/* ─── Terms Modal ─── */}
      <Dialog open={termsOpen} onOpenChange={setTermsOpen}>
        <DialogContent className="max-h-[80vh] overflow-y-auto sm:max-w-lg">
          <DialogHeader>
            <DialogTitle>이용약관</DialogTitle>
          </DialogHeader>
          <div className="prose prose-sm text-sm leading-relaxed text-muted-foreground space-y-4">
            <h3 className="text-base font-semibold text-foreground">제1조 (목적)</h3>
            <p>본 약관은 핫딜닷쿨(이하 &quot;회사&quot;)이 제공하는 프로필 링크 서비스(이하 &quot;서비스&quot;)의 이용조건 및 절차, 회사와 이용자의 권리·의무 및 책임사항을 규정함을 목적으로 합니다.</p>

            <h3 className="text-base font-semibold text-foreground">제2조 (정의)</h3>
            <p>① &quot;서비스&quot;란 회사가 제공하는 프로필 페이지 생성, 링크 관리, 클릭 분석 등 관련 제반 서비스를 의미합니다.</p>
            <p>② &quot;이용자&quot;란 본 약관에 따라 회사가 제공하는 서비스를 이용하는 회원 및 비회원을 말합니다.</p>
            <p>③ &quot;회원&quot;이란 회사에 개인정보를 제공하여 회원등록을 한 자로서, 회사의 서비스를 계속적으로 이용할 수 있는 자를 말합니다.</p>

            <h3 className="text-base font-semibold text-foreground">제3조 (약관의 효력 및 변경)</h3>
            <p>① 본 약관은 서비스를 이용하고자 하는 모든 이용자에 대하여 그 효력을 발생합니다.</p>
            <p>② 회사는 합리적인 사유가 발생할 경우 관련 법령에 위배되지 않는 범위 내에서 본 약관을 변경할 수 있으며, 변경된 약관은 서비스 내 공지사항을 통해 공지합니다.</p>

            <h3 className="text-base font-semibold text-foreground">제4조 (회원가입)</h3>
            <p>① 이용자는 회사가 정한 가입 양식에 따라 회원정보를 기입한 후 본 약관에 동의한다는 의사표시를 함으로써 회원가입을 신청합니다.</p>
            <p>② 회사는 전항의 신청에 대하여 서비스 이용을 승낙함을 원칙으로 합니다. 다만, 다음 각 호에 해당하는 경우 승낙을 거부할 수 있습니다.</p>

            <h3 className="text-base font-semibold text-foreground">제5조 (서비스의 제공)</h3>
            <p>① 회사는 회원에게 프로필 페이지 생성 및 관리, 듀얼뷰(모바일/웹) 최적화, 클릭 분석 및 통계, 테마 커스터마이징 등의 서비스를 제공합니다.</p>
            <p>② 서비스는 연중무휴, 1일 24시간 제공함을 원칙으로 합니다. 다만, 시스템 정기점검 등의 필요에 의해 회사가 정한 날이나 시간에는 서비스를 일시 중단할 수 있습니다.</p>

            <h3 className="text-base font-semibold text-foreground">제6조 (회원 탈퇴 및 자격 상실)</h3>
            <p>① 회원은 회사에 언제든지 탈퇴를 요청할 수 있으며, 회사는 즉시 회원탈퇴를 처리합니다.</p>
            <p>② 회원이 다음 각 호의 사유에 해당하는 경우, 회사는 회원자격을 제한 또는 정지시킬 수 있습니다.</p>
          </div>
        </DialogContent>
      </Dialog>

      {/* ─── Privacy Modal ─── */}
      <Dialog open={privacyOpen} onOpenChange={setPrivacyOpen}>
        <DialogContent className="max-h-[80vh] overflow-y-auto sm:max-w-lg">
          <DialogHeader>
            <DialogTitle>개인정보처리방침</DialogTitle>
          </DialogHeader>
          <div className="prose prose-sm text-sm leading-relaxed text-muted-foreground space-y-4">
            <h3 className="text-base font-semibold text-foreground">1. 개인정보의 수집 및 이용 목적</h3>
            <p>회사는 다음의 목적을 위하여 개인정보를 처리합니다. 처리하고 있는 개인정보는 다음의 목적 이외의 용도로는 이용되지 않으며, 이용 목적이 변경되는 경우에는 별도의 동의를 받는 등 필요한 조치를 이행합니다.</p>
            <p>① 회원가입 및 관리: 회원 가입의사 확인, 회원제 서비스 제공에 따른 본인 식별·인증, 회원자격 유지·관리, 서비스 부정이용 방지 등</p>
            <p>② 서비스 제공: 프로필 페이지 생성 및 관리, 콘텐츠 제공, 맞춤 서비스 제공 등</p>

            <h3 className="text-base font-semibold text-foreground">2. 수집하는 개인정보 항목</h3>
            <p>회사는 회원가입 시 서비스 이용을 위해 필요한 최소한의 개인정보를 수집합니다.</p>
            <p>① 필수항목: 이메일 주소, 닉네임, 비밀번호</p>
            <p>② 자동수집항목: 접속 IP 주소, 접속 로그, 브라우저 종류, 서비스 이용 기록</p>

            <h3 className="text-base font-semibold text-foreground">3. 개인정보의 보유 및 이용기간</h3>
            <p>회사는 법령에 따른 개인정보 보유·이용기간 또는 정보주체로부터 개인정보를 수집 시에 동의 받은 개인정보 보유·이용기간 내에서 개인정보를 처리·보유합니다.</p>
            <p>① 회원 정보: 회원 탈퇴 시까지 (단, 관계 법령에 따라 보존이 필요한 경우 해당 기간까지)</p>
            <p>② 서비스 이용 기록: 3년 (전자상거래 등에서의 소비자 보호에 관한 법률)</p>

            <h3 className="text-base font-semibold text-foreground">4. 개인정보의 제3자 제공</h3>
            <p>회사는 원칙적으로 이용자의 개인정보를 제3자에게 제공하지 않습니다. 다만, 이용자가 사전에 동의한 경우 또는 법령의 규정에 의한 경우에는 예외로 합니다.</p>

            <h3 className="text-base font-semibold text-foreground">5. 개인정보의 파기</h3>
            <p>회사는 개인정보 보유기간의 경과, 처리목적 달성 등 개인정보가 불필요하게 되었을 때에는 지체 없이 해당 개인정보를 파기합니다.</p>

            <h3 className="text-base font-semibold text-foreground">6. 이용자의 권리·의무</h3>
            <p>① 이용자는 회사에 대해 언제든지 개인정보 열람·정정·삭제·처리정지 요구 등의 권리를 행사할 수 있습니다.</p>
            <p>② 이용자는 개인정보 보호법 등 관계 법령을 준수하여야 하며, 타인의 개인정보를 침해하여서는 안됩니다.</p>

            <h3 className="text-base font-semibold text-foreground">7. 개인정보 보호책임자</h3>
            <p>회사는 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 이용자의 불만처리 및 피해구제 등을 위하여 개인정보 보호책임자를 지정하고 있습니다.</p>
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}
