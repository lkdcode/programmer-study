package gof.iterator.step02;

import java.util.Iterator;

class Application {
    public static void main(String[] args) {
        BookShelf bookShelf = new BookShelf(4);
        bookShelf.appendBook(new Book("A"));
        bookShelf.appendBook(new Book("B"));
        bookShelf.appendBook(new Book("C"));
        bookShelf.appendBook(new Book("D"));

        Iterator<Book> iterator = bookShelf.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
