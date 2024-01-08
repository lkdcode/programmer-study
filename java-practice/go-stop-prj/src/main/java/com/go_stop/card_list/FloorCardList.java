package com.go_stop.card_list;

import com.go_stop.hwatoo.HwaToo;

import java.util.ArrayList;
import java.util.List;

public class FloorCardList {

    private final List<HwaToo> list = new ArrayList<>();

    public void addHwaToo(final HwaToo hwaToo) {
        this.list.add(hwaToo);
    }

    public int getSize() {
        return this.list.size();
    }

    @Override
    public String toString() {
        return "FloorCardList{" +
                "list=" + list +
                '}';
    }

}
