"use client";

import { useState, type FormEvent } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { validateEmail } from "@/lib/validators";

export default function LoginPage() {
  const router = useRouter();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const [errors, setErrors] = useState<Record<string, string | null>>({});
  const [touched, setTouched] = useState<Record<string, boolean>>({});

  function validate(field: string): string | null {
    switch (field) {
      case "email":
        return validateEmail(email);
      case "password":
        return !password ? "비밀번호를 입력해주세요" : null;
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

    const fields = ["email", "password"];
    const allTouched = Object.fromEntries(fields.map((f) => [f, true]));
    setTouched((prev) => ({ ...prev, ...allTouched }));

    const newErrors: Record<string, string | null> = {};
    for (const field of fields) {
      newErrors[field] = validate(field);
    }
    setErrors(newErrors);

    const hasErrors = Object.values(newErrors).some((e) => e !== null);
    if (hasErrors) return;

    // MVP: set auth flag and redirect to dashboard
    localStorage.setItem("hddc-auth", "true");
    router.push("/dashboard");
  }

  return (
    <form onSubmit={handleSubmit} className="flex flex-col gap-6">
      <div>
        <h1 className="text-2xl font-bold tracking-tight">로그인</h1>
        <p className="mt-1 text-sm text-muted-foreground">
          이메일로 로그인하기
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

      {/* Password */}
      <div className="relative flex flex-col gap-1.5">
        <div className="flex items-center justify-between">
          <Label htmlFor="password">비밀번호</Label>
          <Link
            href="/auth/forgot-password"
            className="text-xs text-muted-foreground underline underline-offset-2 transition-colors hover:text-foreground"
          >
            비밀번호 찾기
          </Link>
        </div>
        <Input
          id="password"
          type="password"
          placeholder="••••••••"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          onBlur={() => handleBlur("password")}
          aria-invalid={touched.password && !!errors.password}
        />
        {touched.password && errors.password && (
          <p className="absolute -bottom-4 text-xs text-destructive">{errors.password}</p>
        )}
      </div>

      {/* Submit */}
      <Button type="submit" className="h-11 text-sm font-semibold">
        로그인
      </Button>

      {/* Signup Link */}
      <p className="text-center text-sm text-muted-foreground">
        계정이 없으신가요?{" "}
        <Link href="/auth/signup" className="font-semibold underline text-foreground">
          회원가입
        </Link>
      </p>
    </form>
  );
}
