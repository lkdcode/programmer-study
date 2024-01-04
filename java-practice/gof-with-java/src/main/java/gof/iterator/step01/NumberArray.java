package gof.iterator.step01;

import java.util.Arrays;
import java.util.NoSuchElementException;

class NumberArray implements Numbers {
    private static final int CAPACITY = 10;

    private int[] array = new int[CAPACITY];
    private int inputIndex = 0;
    private int nextIndex = 0;

    @Override
    public void add(int number) {
        if (this.array.length <= inputIndex) {
            this.array = Arrays.copyOf(this.array, array.length + CAPACITY);
        }

        this.array[inputIndex++] = number;
    }

    @Override
    public int getNext() {
        if (hasNext()) {
            return this.array[nextIndex++];
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean hasNext() {
        return this.inputIndex > this.nextIndex;
    }

    @Override
    public void reset() {
        this.nextIndex = 0;
    }

    @Override
    public String toString() {
        return "NumberArray{" +
                "array=" + Arrays.toString(array) +
                ", inputIndex=" + inputIndex +
                ", nextIndex=" + nextIndex +
                '}';
    }

}
