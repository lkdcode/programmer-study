package gof.visitor.step2;

interface Element {
    void accept(Visitor visitor);

    String getName();
}
