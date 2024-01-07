package practice.baseball;

import org.junit.jupiter.api.RepeatedTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ComputerTest {

    @RepeatedTest(5_000)
    void computerTest() {
        final Computer computer = new Computer();
        final BaseBallNumbers baseBallNumbers = computer.getNumbers();
        final Set<Integer> set = new HashSet<>();

        while (baseBallNumbers.hasNext()) {
            final int nextNumber = baseBallNumbers.getNext();
            assertThat(nextNumber)
                    .isGreaterThanOrEqualTo(0)
                    .isLessThanOrEqualTo(9);
            set.add(nextNumber);
        }

        assertThat(set.size())
                .isEqualTo(3);
    }

}
