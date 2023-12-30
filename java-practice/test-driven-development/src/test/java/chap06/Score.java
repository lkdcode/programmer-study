package chap06;

public class Score {
    private final String gameNumber;
    private final String inputNumber;

    public Score(String gameNumber, String inputNumber) {
        this.gameNumber = gameNumber;
        this.inputNumber = inputNumber;
    }

    public int strikes() {
        final char[] gameNumbers = this.gameNumber.toCharArray();
        final char[] inputNumbers = this.inputNumber.toCharArray();
        int count = 0;
        if (gameNumbers[0] == inputNumbers[0]) count++;
        if (gameNumbers[1] == inputNumbers[1]) count++;
        if (gameNumbers[2] == inputNumbers[2]) count++;

        return count;
    }

    public int balls() {
        final char[] gameNumbers = this.gameNumber.toCharArray();
        final char[] inputNumbers = this.inputNumber.toCharArray();
        int count = 0;
        if (gameNumbers[0] != inputNumbers[0]
                && gameNumbers[1] == inputNumbers[0]
                || gameNumbers[2] == inputNumbers[0]
        ) count++;

        if (gameNumbers[1] != inputNumbers[1]
                && gameNumbers[0] == inputNumbers[1]
                || gameNumbers[2] == inputNumbers[1]
        ) count++;

        if (gameNumbers[2] != inputNumbers[2]
                && gameNumbers[0] == inputNumbers[2]
                || gameNumbers[1] == inputNumbers[2]
        ) count++;

        return count;
    }
}
