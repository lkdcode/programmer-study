package chapC;

import java.util.ArrayList;
import java.util.List;

class FruitBox {
    private final List<Fruit> list = new ArrayList<>();

    public Fruit get(int index) {
        return this.list.get(index);
    }

    public void add(Fruit fruit) {
        this.list.add(fruit);
    }

    public boolean has(Fruit fruit) {
        return this.list.contains(fruit);
    }

    public int size() {
        return this.list.size();
    }
}
