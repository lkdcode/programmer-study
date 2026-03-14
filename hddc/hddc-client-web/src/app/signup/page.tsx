"use client";

import { useState, useEffect, type FormEvent } from "react";
import Link from "next/link";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Checkbox } from "@/components/ui/checkbox";
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
    <div className="flex min-h-svh flex-col lg:flex-row">
      {/* ─── Left: Branding Panel ─── */}

      {/* Desktop: full branding with mockups */}
      <div className="hidden lg:flex lg:w-1/2 flex-col items-center justify-center gap-8 bg-gradient-to-br from-[#1a1a2e] to-[#16213e] p-12 text-white">
        <span className="text-2xl font-bold tracking-tight">핫딜닷쿨</span>
        <div className="flex items-end gap-4">
          <PhoneMockup className="w-[120px] opacity-90" />
          <BrowserMockup className="w-[200px] opacity-90" />
        </div>
        <p className="text-center text-sm leading-relaxed opacity-70">
          하나의 링크,
          <br />
          <span className="text-primary">두 개의 완벽한 뷰</span>
        </p>
      </div>

      {/* Mobile: compact banner */}
      <div className="flex items-center justify-center gap-2 bg-gradient-to-r from-[#1a1a2e] to-[#16213e] px-4 py-5 text-white lg:hidden">
        <span className="text-lg font-bold">핫딜닷쿨</span>
        <span className="text-xs opacity-60">·</span>
        <span className="text-xs opacity-70">
          하나의 링크, <span className="text-primary">두 개의 완벽한 뷰</span>
        </span>
      </div>

      {/* ─── Right: Form / Verify ─── */}
      <div className="flex flex-1 items-center justify-center px-4 py-12 lg:w-1/2">
        <div className="w-full max-w-sm">
          {step === "form" ? (
            <form onSubmit={handleSubmit} className="flex flex-col gap-5">
              <div>
                <h1 className="text-2xl font-bold tracking-tight">회원가입</h1>
                <p className="mt-1 text-sm text-muted-foreground">
                  이메일로 시작하기
                </p>
              </div>

              {/* Email */}
              <div className="flex flex-col gap-1.5">
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
                  <p className="text-sm text-destructive">{errors.email}</p>
                )}
              </div>

              {/* Nickname */}
              <div className="flex flex-col gap-1.5">
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
                  <p className="text-sm text-destructive">{errors.nickname}</p>
                )}
              </div>

              {/* Password */}
              <div className="flex flex-col gap-1.5">
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
                  <p className="text-sm text-destructive">{errors.password}</p>
                ) : (
                  <p className="text-xs text-muted-foreground">
                    8자 이상, 영문/숫자/특수문자 포함
                  </p>
                )}
              </div>

              {/* Password Confirm */}
              <div className="flex flex-col gap-1.5">
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
                  <p className="text-sm text-destructive">
                    {errors.passwordConfirm}
                  </p>
                )}
              </div>

              {/* Terms */}
              <div className="flex flex-col gap-1.5">
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
                    <Link href="/" className="underline text-foreground">
                      이용약관
                    </Link>{" "}
                    및{" "}
                    <Link href="/" className="underline text-foreground">
                      개인정보처리방침
                    </Link>
                    에 동의합니다
                  </label>
                </div>
                {errors.agreed && (
                  <p className="text-sm text-destructive">{errors.agreed}</p>
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
  );
}
