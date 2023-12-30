package com.level7.examples;

import com.level6.ReceiverEndPoint;
import com.level6.Twoot;

public class PrintingEndPointSnippects {
    public static void main(String[] args) {
        final ReceiverEndPoint anonymousClass = new ReceiverEndPoint() {

            @Override
            public void onTwoot(Twoot twoot) {
                System.out.println(twoot.getSenderId() + ": " + twoot.getContent());
            }
        };

        final ReceiverEndPoint lambda = twoot -> System.out.println(twoot.getSenderId() + ": " + twoot.getContent());
    }
}
