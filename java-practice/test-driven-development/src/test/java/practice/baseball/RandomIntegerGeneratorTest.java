package practice.baseball;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomIntegerGeneratorTest {
    @RepeatedTest(5_000)
    void randomNumberMaker() {
        final int number = RandomIntegerGenerator.getNumber();

        assertThat(number)
                .isGreaterThanOrEqualTo(0)
                .isLessThanOrEqualTo(9);
    }

}
