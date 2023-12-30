package chap06;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.*;

class MathUtilsTest {
    private final MathUtils mathUtils = new MathUtils();

    @Test
    void dataFileSumTest() {
        File file = new File("src/test/resources/datafile.txt");

        final int result = mathUtils.sum(file);
        Assertions.assertThat(result).isNotZero().isEqualTo(10);
    }

    @Test
    void noDataFile_Then_Exception() {
        File file = new File("");

        assertThatThrownBy(() -> mathUtils.sum(file))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
