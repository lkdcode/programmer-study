package gof.visitor.step3;

class Book implements Item {

    private static final String name = "book";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
