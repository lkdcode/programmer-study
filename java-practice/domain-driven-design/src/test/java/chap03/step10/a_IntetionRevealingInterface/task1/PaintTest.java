package chap03.step10.a_IntetionRevealingInterface.task1;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PaintTest {
    @Test
    void test() {
        Paint yellow = new Paint(100.0, 0, 50, 0);
        Paint blue = new Paint(100.0, 0, 0, 50);

        yellow.paint(blue);

        Assertions.assertThat(yellow.v).isEqualTo(200.0);
        Assertions.assertThat(yellow.y).isEqualTo(50);
        Assertions.assertThat(yellow.b).isEqualTo(50);
        Assertions.assertThat(yellow.r).isEqualTo(0);
    }
}