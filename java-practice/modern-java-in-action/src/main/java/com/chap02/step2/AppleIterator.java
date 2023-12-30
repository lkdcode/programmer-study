package com.chap02.step2;

import java.util.List;

class AppleIterator {
    private final List<Apple> appleList;
    //    private final Apple[] appleList;
    //    private final LinkedList<Apple> appleList;
    private int index = 0;

    public AppleIterator(List<Apple> appleList) {
        this.appleList = appleList;
    }

    public boolean hasNext() {
//        return appleList.length > index;
        return appleList.size() > index;
    }

    public Apple next() {
//        return appleList[index++];
        return appleList.get(index++);
    }

}
