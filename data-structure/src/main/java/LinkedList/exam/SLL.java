package LinkedList.exam;

public class SLL<E> {

    private Node<E> head;
    private Node<E> tail;

    private int size = 0;

    public SLL() {
        this.head = this.tail = null;
    }

    //- addFirst(e) - 연결 리스트 맨 앞에 요소 추가
    public void addFirst(E e) {
        final Node<E> newNode = new Node<>(e, null);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }

        size++;
    }

    //- addLast(e) - 연결 리스트 맨 마지막에 요소 추가
    public void addLast(E e) {
        final Node<E> lastNode = new Node<>(e, null);

        if (tail == null) {

        } else {

        }
    }

    //- add(index, e) - 특정 위치에 요소를 추가
    public void add() {
    }

    //- get(index) - 특정 위치에 요소를 반환
    public void get() {
    }

    //- set(index, e) - 특정 위치에 요소를 parameter로 전달된 요소로 변환
    public void set() {
    }

    //- remove(index) - 특정 위치에 요소를 삭제
    public void remove() {
    }


}
