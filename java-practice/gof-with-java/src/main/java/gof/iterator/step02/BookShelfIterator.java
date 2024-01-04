package gof.iterator.step02;

import java.util.Iterator;
import java.util.NoSuchElementException;

class BookShelfIterator implements Iterator<Book> {

    private final BookShelf bookShelf;
    private int index;

    public BookShelfIterator(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return bookShelf.getLength() > this.index;
    }

    @Override
    public Book next() {
        if (hasNext()) return bookShelf.getBookAt(index++);
        throw new NoSuchElementException();
    }

}
