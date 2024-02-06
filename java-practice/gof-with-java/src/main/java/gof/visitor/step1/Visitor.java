package gof.visitor.step1;


abstract class Visitor {
    public abstract void visit(File file);

    public abstract void visit(Directory directory);
}
