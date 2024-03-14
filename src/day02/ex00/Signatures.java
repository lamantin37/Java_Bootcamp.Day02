package day02.ex00;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Signatures {
    private static final Map<String, String> signatures = new HashMap<>();
    private static final String SIGNATURES_TXT_PATH = "src/day02/ex00/signatures.txt";

    public static Map<String, String> readSignatures() {
        try {
            FileInputStream fileStream = new FileInputStream(SIGNATURES_TXT_PATH);
            Scanner scanner = new Scanner(fileStream);
            while (scanner.hasNext()) {
                signatures.put(scanner.next().replace(",", " ").trim(), scanner.nextLine().trim());
            }
            scanner.close();
            fileStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return signatures;
    }
}
