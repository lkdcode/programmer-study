package com.hexagonal.chap01.domain.value;

public class Activity {
    private final String srcHost;
    private final String dstHost;

    public Activity(String srcHost, String dstHost) {
        this.srcHost = srcHost;
        this.dstHost = dstHost;
    }
}
