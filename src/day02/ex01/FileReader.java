package day02.ex01;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class FileReader {
    private final String firstFilePath;
    private final String secondFilePath;

    FileReader(String firstFilePath, String secondFilePath) {
        this.firstFilePath = firstFilePath;
        this.secondFilePath = secondFilePath;
    }

    private Set<String> readEntries(String filePath) throws IOException {
        Set<String> entries = new HashSet<>();
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Scanner scanner = new Scanner(fileInputStream)) {
            while (scanner.hasNext()) {
                entries.add(scanner.next());
            }
        }
        return entries;
    }

    private int[] countOccurrences(Set<String> set, String filePath) throws IOException {
        int[] occurrences = new int[set.size()];
        Map<String, Integer> indexMap = new HashMap<>();
        int index = 0;
        for (String word : set) {
            indexMap.put(word, index++);
        }

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Scanner scanner = new Scanner(fileInputStream)) {
            while (scanner.hasNext()) {
                String word = scanner.next();
                Integer wordIndex = indexMap.get(word);
                if (wordIndex != null) {
                    occurrences[wordIndex]++;
                }
            }
        }

        return occurrences;
    }

    private double countSimilarity(int[] A, int[] B) {
        double similarity;

        int numerator = IntStream.range(0, A.length)
                .map(i -> A[i] * B[i])
                .sum();

        int denominatorA = Arrays.stream(A).map(i -> i * i)
                .sum();
        int denominatorB = Arrays.stream(B).map(i -> i * i)
                .sum();

        similarity = numerator / (Math.sqrt(denominatorA) * Math.sqrt(denominatorB));
        return similarity;
    }

    public double countIncludes() {
        try {
            String relationPath = "src/day02/ex01/";
            Set<String> set = new HashSet<>();
            set.addAll(readEntries(relationPath + firstFilePath));
            set.addAll(readEntries(relationPath + secondFilePath));

            int[] entriesA = countOccurrences(set, relationPath + firstFilePath);
            int[] entriesB = countOccurrences(set, relationPath + secondFilePath);

            return countSimilarity(entriesA, entriesB);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

