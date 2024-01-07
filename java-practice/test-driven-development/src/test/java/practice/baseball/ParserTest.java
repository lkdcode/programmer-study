package practice.baseball;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserTest {

    @ParameterizedTest
    @MethodSource("data")
    void parseTest(final String str, final BaseBallNumbers expectedBaseBallNumbers) {
        final Parser parser = new Parser();
        final BaseBallNumbers baseBallNumbers = parser.parseInt(str);

        assertThat(baseBallNumbers)
                .isEqualTo(expectedBaseBallNumbers);
    }

    private static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of("123", new BaseBallNumbers(Arrays.asList(1, 2, 3)))
                , Arguments.of("3246", new BaseBallNumbers(Arrays.asList(3, 2, 4, 6)))
                , Arguments.of("522", new BaseBallNumbers(Arrays.asList(5, 2, 2)))
                , Arguments.of("963", new BaseBallNumbers(Arrays.asList(9, 6, 3)))
                , Arguments.of("271", new BaseBallNumbers(Arrays.asList(2, 7, 1)))
                , Arguments.of("05719", new BaseBallNumbers(Arrays.asList(0, 5, 7, 1, 9)))
                , Arguments.of("195983", new BaseBallNumbers(Arrays.asList(1, 9, 5, 9, 8, 3)))
        );
    }

}
