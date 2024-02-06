package gof.visitor.step3;

public class Pork implements Item {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String getName() {
        return "족발";
    }
}
