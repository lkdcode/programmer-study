package gof.visitor.step4;

class ConcreteVisitorAClazz implements Visitor {
    @Override
    public void visit(Element element) {
        if (element instanceof ConcreteElementAClazz) {
            ((ConcreteElementAClazz) element).operationA();
        }

        if (element instanceof ConcreteElementBClazz) {
            ((ConcreteElementBClazz) element).operationB();
        }
    }
}
