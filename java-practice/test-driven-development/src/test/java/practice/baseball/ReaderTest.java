package practice.baseball;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ReaderTest {

    @ParameterizedTest
    @ValueSource(strings = {"asdf23we", "asdfhoui2", "y9284ruwe", "1248asdf", "aldfjgo82"})
    void readerTest(final String input) {
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        final String read = Reader.readLine();

        assertThat(read)
                .isEqualTo(input);
    }

}
