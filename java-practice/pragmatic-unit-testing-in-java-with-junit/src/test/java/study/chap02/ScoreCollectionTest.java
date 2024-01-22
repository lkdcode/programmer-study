package study.chap02;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreCollectionTest {
    @Test
    void answersArithmeticMeanOfTwoNumbers() {
        final ScoreCollection collection = new ScoreCollection();
        collection.add(() -> 5);
        collection.add(() -> 7);

        int actualResult = collection.arithmeticMean();
        assertThat(actualResult).isEqualTo(6);
    }
}