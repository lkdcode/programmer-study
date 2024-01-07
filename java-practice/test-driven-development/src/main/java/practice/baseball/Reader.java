package practice.baseball;

import java.util.Scanner;

public class Reader {
    private static Scanner scanner;

    public static String readLine() {
        scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void close() {
        scanner.close();
    }

}
