package com.go_stop.dealer;

import com.go_stop.card_list.FloorCardList;
import com.go_stop.card_list.UserCardList;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DealerTest {

    @Test
    void dealerTest() {
        final Dealer dealer = new Dealer();
        final UserCardList firstUserHwaTooList = dealer.getFirstUserHwaTooList();
        final UserCardList secondUserHwaTooList = dealer.getSecondUserHwaTooList();
        final FloorCardList floorCardList = dealer.getFloorCardList();

        System.out.println(firstUserHwaTooList);
        System.out.println(secondUserHwaTooList);
        System.out.println(floorCardList);

        assertThat(firstUserHwaTooList.getSize())
                .isEqualTo(10);

        assertThat(secondUserHwaTooList.getSize())
                .isEqualTo(10);

        assertThat(floorCardList.getSize())
                .isEqualTo(8);
    }

}