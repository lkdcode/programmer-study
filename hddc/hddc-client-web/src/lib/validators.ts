export function validateEmail(value: string): string | null {
  if (!value) return "이메일을 입력해주세요";
  if (value.length > 254) return "이메일은 254자 이하로 입력해주세요";
  if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value))
    return "올바른 이메일 형식이 아닙니다";
  return null;
}

export function validateNickname(value: string): string | null {
  if (!value) return "닉네임을 입력해주세요";
  if (value.length < 2) return "닉네임은 2자 이상이어야 합니다";
  if (value.length > 20) return "닉네임은 20자 이하로 입력해주세요";
  if (!/^[가-힣a-zA-Z0-9]+$/.test(value))
    return "한글, 영문, 숫자만 사용할 수 있습니다";
  return null;
}

export function validatePassword(value: string): string | null {
  if (!value) return "비밀번호를 입력해주세요";
  if (value.length < 8) return "비밀번호는 8자 이상이어야 합니다";
  if (value.length > 72) return "비밀번호는 72자 이하로 입력해주세요";
  if (!/[a-zA-Z]/.test(value)) return "영문을 포함해야 합니다";
  if (!/[0-9]/.test(value)) return "숫자를 포함해야 합니다";
  if (!/[^a-zA-Z0-9]/.test(value)) return "특수문자를 포함해야 합니다";
  return null;
}

export function validatePasswordConfirm(
  password: string,
  confirm: string,
): string | null {
  if (!confirm) return "비밀번호를 다시 입력해주세요";
  if (password !== confirm) return "비밀번호가 일치하지 않습니다";
  return null;
}
