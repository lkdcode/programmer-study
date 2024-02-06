package gof.visitor.step4;

class ConcreteVisitorBClazz implements Visitor {
    @Override
    public void visit(Element element) {
        System.out.println("ConcreteVisitorBClazz.visit" + element.getClass().getSimpleName() + " !!");
    }
}
