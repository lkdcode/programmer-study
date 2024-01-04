package gof.iterator.step01;

class Application {

    public static void main(String[] args) {
        final NumberList numberList = new NumberList();
        final NumberArray numberArray = new NumberArray();

        for (int i = 1; i <= 77; i++) {
            numberList.add(i);
            numberArray.add(i);
        }

        while (numberList.hasNext()) {
            System.out.print("List: " + numberList.getNext());
            System.out.println(" , Array: " + numberArray.getNext());
        }

    }

}
