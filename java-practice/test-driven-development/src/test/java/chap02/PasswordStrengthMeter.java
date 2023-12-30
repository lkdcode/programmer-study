package chap02;

public class PasswordStrengthMeter {

    public PasswordStrength meter(String s) {
        if (s != null && !s.isEmpty()) {
            int metCounts = this.getMetCriteriaCounts(s);
            if (metCounts <= 1) {
                return PasswordStrength.WEAK;
            } else {
                return metCounts == 2 ? PasswordStrength.NORMAL : PasswordStrength.STRONG;
            }
        } else {
            return PasswordStrength.INVALID;
        }
    }

    private int getMetCriteriaCounts(String s) {
        int metCounts = 0;
        if (s.length() >= 8) {
            ++metCounts;
        }

        if (meetsContainingNumberCriteria(s)) {
            ++metCounts;
        }

        if (meetsContainingUppercaseCriteria(s)) {
            ++metCounts;
        }

        return metCounts;
    }

    private static boolean meetsContainingUppercaseCriteria(String s) {
        char[] var1 = s.toCharArray();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            char ch = var1[var3];
            if (Character.isUpperCase(ch)) {
                return true;
            }
        }

        return false;
    }

    private static boolean meetsContainingNumberCriteria(String s) {
        char[] var1 = s.toCharArray();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            char ch = var1[var3];
            if (ch >= '0' && ch <= '9') {
                return true;
            }
        }

        return false;
    }
}
