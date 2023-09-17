package LinkedList.singly_linked_list;

public class Main {
    public static void main(String[] args) {
        SinglyLinkedList<String> test = new SinglyLinkedList<>();

        test.print();

        test.addFirst("첫번째 글");
        test.addFirst("두번째 글");
        test.addFirst("세번째 글");
        test.print();

        Node<String> node = test.getNode(2);
        System.out.println("node = " + node.data);

        System.out.println();
        System.out.println();

        test.add(0, "New Add 1");
        test.add(0, "New Add 2");
        test.add(0, "New Add 3");
        System.out.println("-----------------");
        test.print();


        String remove = test.remove(5);
        String remove1 = test.remove(3);
        String remove2 = test.remove(0);

        System.out.println();
        System.out.println();
        test.print();
        System.out.println();
    }

}
