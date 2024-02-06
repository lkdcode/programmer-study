package gof.visitor.step1;

public class Application {
    public static void main(String[] args) {
        System.out.println("Making root entries...");

        final Directory root = new Directory("root");
        final Directory bin = new Directory("bin");
        final Directory tmp = new Directory("tmp");
        final Directory usr = new Directory("usr");

        root.add(bin);
        root.add(tmp);
        root.add(usr);
        bin.add(new File("vi", 10_000));
        bin.add(new File("latex", 20_000));

        //

        root.accept(new ListVisitor());
        System.out.println();

        System.out.println("Making user entries...");

        final Directory youngjin = new Directory("youngjin");
        final Directory gildong = new Directory("gildong");
        final Directory dojun = new Directory("dojun");
        usr.add(youngjin);
        usr.add(gildong);
        usr.add(dojun);

        youngjin.add(new File("diary.html", 100));
        youngjin.add(new File("Composite.java", 200));
        gildong.add(new File("memo.txt", 300));
        dojun.add(new File("game.doc", 400));
        dojun.add(new File("junk.mail", 500));

        //

        root.accept(new ListVisitor());
    }
}
