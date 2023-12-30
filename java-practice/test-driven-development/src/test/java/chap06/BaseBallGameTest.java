package chap06;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class BaseBallGameTest {
    private BaseBallGame baseBallGame;

    @BeforeEach
    void givenGame() {
        this.baseBallGame = new BaseBallGame("456");
    }

    @Test
    void exactMatch() {
        final Score score = baseBallGame.guess("456");

        assertThat(score.strikes()).isEqualTo(3);
        assertThat(score.balls()).isEqualTo(0);
    }

    @Test
    void noMatch() {
        final Score score = baseBallGame.guess("123");

        assertThat(score.strikes()).isEqualTo(0);
        assertThat(score.balls()).isEqualTo(0);
    }

    @Test
    void exceptionThrownDuplicateNumberTest() {
        assertThatThrownBy(() -> new BaseBallGame("110"))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
