package com.practice.level5.step6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class PeopleList {

    private final List<People> list;

    public PeopleList(People... peopleList) {
        this.list = Arrays.asList(peopleList);
    }

    public People getPeople(final int index) {
        return this.list.get(index);
    }

    public List<People> getList() {
        return new ArrayList<>(this.list);
    }

    public Stream<People> stream() {
        return new ArrayList<>(this.getList()).stream();
    }

}
