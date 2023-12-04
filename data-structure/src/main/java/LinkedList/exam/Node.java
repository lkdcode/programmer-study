package LinkedList.exam;

public class Node<E> {
    public E data;
    public Node<E> next;

    public Node(E data) {
        this.data = data;
        this.next = null;
    }

    public Node(E data, Node<E> next) {
        this.data = data;
        this.next = next;
    }

}
