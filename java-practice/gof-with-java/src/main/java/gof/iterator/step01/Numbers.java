package gof.iterator.step01;

interface Numbers {
    void add(int number);
    int getNext();

    boolean hasNext();
    void reset();
}
