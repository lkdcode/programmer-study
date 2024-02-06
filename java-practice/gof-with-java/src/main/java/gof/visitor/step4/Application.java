package gof.visitor.step4;

class Application {
    public static void main(String[] args) {
        final ConcreteElementAClazz concreteElementAClazz = new ConcreteElementAClazz();
        final ConcreteElementBClazz concreteElementBClazz = new ConcreteElementBClazz();

        final ConcreteVisitorAClazz concreteVisitorAClazz = new ConcreteVisitorAClazz();
        final ConcreteVisitorBClazz concreteVisitorBClazz = new ConcreteVisitorBClazz();

        System.out.println("--------------------------");
        concreteVisitorAClazz.visit(concreteElementAClazz);
        concreteVisitorAClazz.visit(concreteElementBClazz);

        System.out.println("--------------------------");
        concreteVisitorBClazz.visit(concreteElementAClazz);
        concreteVisitorBClazz.visit(concreteElementBClazz);
    }
}
