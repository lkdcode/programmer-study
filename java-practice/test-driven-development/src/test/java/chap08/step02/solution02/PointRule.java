package chap08.step02.solution02;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class PointRule {
    public int calculate(Subscription s, Product p, LocalDate now) {
        int point = 0;
        if (s.isFinished(now)) {
            point += p.getDefaultPoint();
        } else {
            point += p.getDefaultPoint() + 10;
        }

        if (s.getGrade() == Grade.GOLD) {
            point += 100;
        }

        return point;
    }

    @Test
    void 만료전_GOLD_등급은_130_포인트() {
        PointRule pointRule = new PointRule();
        Subscription subscription = new Subscription(
                LocalDate.of(2019, 5, 5),
                Grade.GOLD
        );
        Product product = new Product();
        product.setDefaultPoint(20);

        int point = pointRule.calculate(subscription, product, LocalDate.of(2019, 5, 5));
        assertThat(point).isEqualTo(130);
    }
}
