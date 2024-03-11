package day02.ex00;

import java.util.Map;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        while (!input.equals("42")) {
            Analyser.readBytes(input);
            input = scanner.nextLine();
        }

        scanner.close();
    }
}
