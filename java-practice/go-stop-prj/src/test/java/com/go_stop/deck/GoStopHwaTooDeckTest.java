package com.go_stop.deck;

import com.go_stop.hwatoo.HwaToo;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class GoStopHwaTooDeckTest {

    @Test
    void goStopHwaTooDeckTest() {
        final GoStopHwaTooDeck goStopHwaTooDeck = new GoStopHwaTooDeck();
        final HwaToo[] list = new HwaToo[HwaToo.values().length];

        int index = 0;
        while (goStopHwaTooDeck.hasNextHwaToo()) {
            list[index++] = goStopHwaTooDeck.getNextHwaToo();
        }

        Arrays.sort(list);
        assertThat(list)
                .isEqualTo(HwaToo.values());

        assertThat(index)
                .isEqualTo(50);
    }

}