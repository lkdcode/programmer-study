package gof.visitor.step1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Directory extends Entry implements Iterable<Entry> {
    private String name;
    private List<Entry> directory = new ArrayList<>();

    public Directory(String name) {
        this.name = name;
    }

    public Entry add(Entry entry) {
        directory.add(entry);
        return this;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return directory.stream()
                .mapToInt(Entry::getSize)
                .sum();
    }

    @Override
    public Iterator<Entry> iterator() {
        return directory.iterator();
    }
}