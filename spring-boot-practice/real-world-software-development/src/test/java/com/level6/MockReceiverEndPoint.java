package com.level6;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MockReceiverEndPoint implements ReceiverEndPoint {
    private final List<Twoot> receivedTwoots = new ArrayList<>();

    @Override
    public void onTwoot(final Twoot twoot) {
        receivedTwoots.add(twoot);
    }

    public void verifyOnTwoot(final Twoot twoot) {
        assertThat(receivedTwoots)
                .contains(twoot);
    }

}
