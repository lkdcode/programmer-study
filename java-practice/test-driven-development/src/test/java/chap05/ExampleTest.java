package chap05;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleTest {

    @Test
    void sumTest() {
        final int result = 2 + 3;
        assertEquals(5, result);
    }

    @Test
    void localDateTest() {
        LocalDate localDate1 = LocalDate.now();
        LocalDate localDate2 = LocalDate.now();
        assertEquals(localDate1, localDate2);
    }

    @Test
    void assertAllTest() {
        assertAll(
                () -> assertEquals(3, 6 / 2),
                () -> assertEquals(4, 2 * 2),
                () -> assertEquals(5, 11 / 2)
        );

        assertThatCode(() -> {
            assertThat("").isNotNull();
            assertThat("start").startsWith("s");
        }).doesNotThrowAnyException();
    }

}
