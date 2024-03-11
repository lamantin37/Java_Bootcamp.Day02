package day02.ex00;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Analyser {
    static byte[] bytes = new byte[8];
    static StringBuilder hex = new StringBuilder();

    public static void readBytes(String path) {
        try {
            FileInputStream inputStream = new FileInputStream(path);
            Scanner scanner = new Scanner(inputStream);
            if (inputStream.read(bytes) != -1) {
                printBytesInHex();
                compareBytes();
            }
            scanner.close();
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printBytesInHex() {
        for (byte b : bytes) {
            hex.append(String.format("%02X", b)).append(" ");
        }
        hex.deleteCharAt(hex.length() - 1);
    }

    private static void compareBytes() {
        Map<String, String> signatures = Signatures.readSignatures();
        try {
            FileOutputStream fileOutput = new FileOutputStream("src/day02/ex00/result.txt", true);
            for (Map.Entry<String, String> entry: signatures.entrySet()) {
                if (hex.toString().contains(entry.getValue())) {
                    fileOutput.write(entry.getKey().getBytes());
                    fileOutput.write('\n');
                    System.out.println("PROCESSED");
                    fileOutput.close();
                    return;
                }
            }
            System.out.println("UNDEFINED");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
