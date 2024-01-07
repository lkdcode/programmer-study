package practice.baseball;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class RefereeTest {

    @ParameterizedTest(name = "Referee Test - ComputerNumbers: {0}, UserNumbers: {1}, Strikes: {2}, Balls: {3}")
    @MethodSource("generateData")
    void refereeTest(
            final BaseBallNumbers defenseNumber,
            final BaseBallNumbers offenceNumber,
            final int expectedStrikeCount,
            final int expectedBallCount) {
        final Referee referee = new Referee();
        final GameResult gameResult = referee.makeJudgment(defenseNumber, offenceNumber);

        assertThat(gameResult.strikeCount())
                .isEqualTo(expectedStrikeCount);

        assertThat(gameResult.ballCount())
                .isEqualTo(expectedBallCount);
    }

    private static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of(new BaseBallNumbers(Arrays.asList(1, 2, 3)), new BaseBallNumbers(Arrays.asList(1, 2, 3)), 3, 0)
                , Arguments.of(new BaseBallNumbers(Arrays.asList(1, 2, 3)), new BaseBallNumbers(Arrays.asList(4, 5, 6)), 0, 0)
                , Arguments.of(new BaseBallNumbers(Arrays.asList(9, 0, 2)), new BaseBallNumbers(Arrays.asList(9, 1, 0)), 1, 1)
                , Arguments.of(new BaseBallNumbers(Arrays.asList(3, 6, 1)), new BaseBallNumbers(Arrays.asList(3, 6, 0)), 2, 0)
                , Arguments.of(new BaseBallNumbers(Arrays.asList(4, 5, 2)), new BaseBallNumbers(Arrays.asList(5, 2, 4)), 0, 3)
        );
    }

}
