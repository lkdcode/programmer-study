package com.level7.examples;

import com.level6.ReceiverEndPoint;
import com.level6.Twoot;

class PrintingEndPoint implements ReceiverEndPoint {
    @Override
    public void onTwoot(Twoot twoot) {
        System.out.println(twoot.getSenderId() + " : " + twoot.getContent());
    }
}
