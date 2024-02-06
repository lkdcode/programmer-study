package gof.visitor.step3;


class Application {
    public static void main(String[] args) {
        final VisitorImpl visitor = new VisitorImpl();

        Book book = new Book();
        Pork pork = new Pork();
        visitor.visit(book);
        visitor.visit(pork);
    }
}
