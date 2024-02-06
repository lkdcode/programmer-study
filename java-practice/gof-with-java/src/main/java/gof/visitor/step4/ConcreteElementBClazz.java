package gof.visitor.step4;

class ConcreteElementBClazz implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void operationB() {
        System.out.println("ConcreteElementBClazz.operationB");
    }
}
