package gof.iterator.step02;

import java.util.Iterator;

public class BookShelf implements Iterable<Book> {

    private final Book[] books;
    private int last = 0;

    public BookShelf(int maxSize) {
        this.books = new Book[maxSize];
    }

    public Book getBookAt(int index) {
        return this.books[index];
    }

    public void appendBook(Book book) {
        this.books[last++] = book;
    }

    public int getLength() {
        return this.last;
    }

    @Override
    public Iterator<Book> iterator() {
        return new BookShelfIterator(this);
    }

}
