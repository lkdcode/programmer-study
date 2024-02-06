package gof.visitor.step2;

import java.util.ArrayList;
import java.util.List;

class ListVisitor implements Visitor {
    private final List<Element> list = new ArrayList<>();

    public void addElement(Element element) {
        list.add(element);
    }

    @Override
    public void visit(Element element) {
        System.out.println("--- ListVisitor.visit ---");
        list.forEach(e -> System.out.println(e.getName()));
    }
}
