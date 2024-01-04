package gof.iterator.step01;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class NumberList implements Numbers {

    private final List<Integer> list = new ArrayList<>();
    private int index = 0;

    @Override
    public void add(int number) {
        this.list.add(number);
    }

    @Override
    public int getNext() {
        if (hasNext()) return this.list.get(index++);
        throw new NoSuchElementException();
    }

    @Override
    public boolean hasNext() {
        return this.list.size() > index;
    }

    @Override
    public void reset() {
        this.index = 0;
    }

    @Override
    public String toString() {
        return "NumberList{" +
                "list=" + list +
                ", index=" + index +
                '}';
    }

}
