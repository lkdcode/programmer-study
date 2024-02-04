package gof.flyweight.step2;

import gof.flyweight.step2.files.BigChar;

public class Application {
    public static void main(String[] args) {
        final BigFactory bigFactory = BigFactory.getInstance();
        BigChar bigChar1 = bigFactory.getBigChar('1');
        BigChar bigChar2 = bigFactory.getBigChar('1');
        BigChar bigChar3 = bigFactory.getBigChar('1');
        BigChar bigChar4 = bigFactory.getBigChar('1');

        System.out.println("bigChar1 = " + bigChar1);
        System.out.println("bigChar2 = " + bigChar2);
        System.out.println("bigChar3 = " + bigChar3);
        System.out.println("bigChar4 = " + bigChar4);
    }
}
