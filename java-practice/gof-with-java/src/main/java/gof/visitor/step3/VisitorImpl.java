package gof.visitor.step3;

class VisitorImpl implements Visitor {

    @Override
    public void visit(Book book) {
        System.out.println(book.getName() + " 행위 변경..!!");
    }

    @Override
    public void visit(Pork pork) {
        System.out.println(pork.getName() + " 행위");
    }
}
