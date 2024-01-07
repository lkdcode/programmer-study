package practice.baseball;

public class Referee {

    public GameResult makeJudgment(
            final BaseBallNumbers defenseNumbers,
            final BaseBallNumbers offenceNumbers
    ) {
        int strikeCount = 0;
        int ballCount = 0;

        while (defenseNumbers.hasNext() && offenceNumbers.hasNext()) {
            final int offenceNumber = offenceNumbers.getNext();
            final int defenceNumber = defenseNumbers.getNext();
            if (offenceNumber == defenceNumber) strikeCount++;
            else if (defenseNumbers.isContains(offenceNumber)) ballCount++;
        }

        return new GameResult(strikeCount, ballCount);
    }

}
