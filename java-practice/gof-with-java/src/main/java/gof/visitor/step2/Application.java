package gof.visitor.step2;

class Application {
    public static void main(String[] args) {
        ListVisitor listVisitor = new ListVisitor();
        Book book = new Book();
        Coffee coffee = new Coffee();

        listVisitor.addElement(book);
        listVisitor.addElement(coffee);
        listVisitor.visit(coffee);
    }
}
