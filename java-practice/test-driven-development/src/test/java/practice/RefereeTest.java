package practice;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class RefereeTest {

    @ParameterizedTest(name = "Referee Test - ComputerTest: {0}, User: {1}, Strikes: {2}, Balls: {3}")
    @MethodSource("generateData")
    void refereeTest(List<Integer> computerNumbers, List<Integer> userNumbers, int expectedStrikeCount, int expectedBallCount) {
        final Referee referee = new Referee();
        final GameResult gameResult = referee.makeJudgment(computerNumbers, userNumbers);

        assertThat(gameResult.strickeCount())
                .isEqualTo(expectedStrikeCount);

        assertThat(gameResult.ballCount())
                .isEqualTo(expectedBallCount);
    }

    private static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of(Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3), 3, 0)
                , Arguments.of(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6), 0, 0)
                , Arguments.of(Arrays.asList(9, 0, 2), Arrays.asList(9, 1, 0), 1, 1)
                , Arguments.of(Arrays.asList(3, 6, 1), Arrays.asList(3, 6, 0), 2, 0)
                , Arguments.of(Arrays.asList(4, 5, 2), Arrays.asList(5, 2, 4), 0, 3)
        );
    }

}
