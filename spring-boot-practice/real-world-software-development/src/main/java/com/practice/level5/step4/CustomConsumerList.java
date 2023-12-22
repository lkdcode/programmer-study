package com.practice.level5.step4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class CustomConsumerList<T> {

    private final List<CustomConsumer<T>> consumerList = new ArrayList<>();

    @SafeVarargs
    public CustomConsumerList(CustomConsumer<T>... consumerList) {
        this.consumerList.addAll(Arrays.asList(consumerList));
    }

    public void addCustomConsumer(CustomConsumer<T> customConsumer) {
        this.consumerList.add(customConsumer);
    }

    public List<CustomConsumer<T>> getConsumerList() {
        return new ArrayList<>(consumerList);
    }

}
