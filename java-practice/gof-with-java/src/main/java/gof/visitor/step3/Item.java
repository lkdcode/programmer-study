package gof.visitor.step3;

interface Item {
    void accept(Visitor visitor);
    String getName();
}
