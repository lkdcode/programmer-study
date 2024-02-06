package gof.visitor.step2;

public class Coffee implements Element {
    private final String name = "coffee";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
