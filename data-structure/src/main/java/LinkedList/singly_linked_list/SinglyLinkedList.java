package LinkedList.singly_linked_list;

public class SinglyLinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void addFirst(E data) {
        final Node<E> newNode = new Node<>(data, null);

        if (head == null) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    public void addLast(E data) {
        final Node<E> newNode = new Node<>(data, null);

        if (tail == null) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public void add(int index, E data) {
        checkIndex(index);

        if (index == size - 1) {
            addLast(data);
        } else if (index == 0) {
            addFirst(data);
        } else {
            Node<E> prevNode = getNode(index - 1);
            Node<E> newNode = new Node<>(data);
            newNode.next = prevNode.next;
            prevNode.next = newNode;
            size++;
        }
    }

    public Node<E> getNode(int index) {
        checkIndex(index);

        Node<E> currentNode = head;

        for (int i = 0; i < index; i++) {
            currentNode = currentNode.next;
        }

        return currentNode;
    }

    public E get(int index) {
        checkIndex(index);

        return getNode(index).data;
    }

    public E set(int index, E data) {
        checkIndex(index);

        Node<E> currentNode = getNode(index);
        E oldData = currentNode.data;
        currentNode.data = data;

        return oldData;
    }

    public E remove(int index) {
        checkIndex(index);

        Node<E> currentNode = null;

        if (index == 0) {
            currentNode = head;
            head = head.next;
        } else if (index == size) {
            Node<E> prevNode = getNode(index - 1);
            prevNode.next = null;
            tail = prevNode;
        } else {
            Node<E> prevNode = getNode(index - 1);
            currentNode = prevNode.next;
            prevNode.next = currentNode.next;
        }

        return currentNode.data;
    }

    public void print() {
        Node<E> currentNode = head;

        if (currentNode == null) System.out.println("empty!");

        int index = 1;

        while (currentNode != null) {
            System.out.print("index : " + index++ + "  data : " + currentNode.data + ", ");
            currentNode = currentNode.next;
        }
        System.out.println();
    }

    private void checkIndex(int index) {
        if (!(index >= 0 && index < size)) {
            throw new IndexOutOfBoundsException("Index : " + index + ", Size : " + size);
        }
    }

}
