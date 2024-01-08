package com.go_stop.card_list;

import com.go_stop.hwatoo.HwaToo;

import java.util.ArrayList;
import java.util.List;

public class UserFloorCardList {

    private final List<HwaToo> list = new ArrayList<>();

    public void addHwaToo(final HwaToo hwaToo) {
        this.list.add(hwaToo);
    }

    public List<HwaToo> getList() {
        return new ArrayList<>(this.list);
    }

    @Override
    public String toString() {
        return "UserFloorCardList{" +
                "list=" + list +
                '}';
    }

}
