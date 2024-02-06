package gof.visitor.step4;

class ConcreteElementAClazz implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void operationA() {
        System.out.println("ConcreteElementAClazz.operationA");
    }
}
