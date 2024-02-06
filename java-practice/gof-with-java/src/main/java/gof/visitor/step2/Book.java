package gof.visitor.step2;

public class Book implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String getName() {
        return "Book";
    }
}
