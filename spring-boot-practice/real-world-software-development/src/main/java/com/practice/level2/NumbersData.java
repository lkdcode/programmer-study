package com.practice.level2;

import java.util.ArrayList;
import java.util.List;

public class NumbersData {
    private final List<Integer> list;

    public NumbersData(List<Integer> list) {
        this.list = list;
    }

    public List<Integer> getList() {
        return new ArrayList<>(list);
    }
}
