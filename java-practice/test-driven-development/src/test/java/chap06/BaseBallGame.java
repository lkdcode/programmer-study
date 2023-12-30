package chap06;

public class BaseBallGame {
    private final String number;

    public BaseBallGame(final String number) {
        for (int i = 0; i < number.length(); i++) {
            char target = number.charAt(i);
            for (int j = 0; j < number.length(); j++) {
                if (i == j) continue;
                if (target == number.charAt(j)) throw new IllegalArgumentException("연속된 숫자는 안 됩니다.");
            }
        }
        this.number = number;
    }

    public Score guess(final String number) {
        return new Score(this.number, number);
    }
}
