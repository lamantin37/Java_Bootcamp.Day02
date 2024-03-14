package day02.ex02;

import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;
import java.util.stream.Stream;

public class fileHandler {
    private static Path path;

    public static void runner(String[] args) {
        checkArgs(args);
        path = Paths.get(args[0].substring("--current-folder=".length())).toAbsolutePath().normalize();
        checkPath(path);
        System.out.println(path);
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                scanInput(scanner);
            }
        }
    }

    private static void scanInput(Scanner scanner) {
        String inputAll = scanner.nextLine().trim();
        String[] input = inputAll.split("\\s+");
        switch (input[0]) {
            case "exit":
                System.exit(0);
            case "ls":
                commandLS();
                break;
            case "cd":
                if (input.length == 2) commandCD(input[1]);
                else System.out.println("cd: wrong number of arguments");
                break;
            case "mv":
                if (input.length == 3) commandMV(input[1], input[2]);
                else System.out.println("mv: missing file operand");
                break;
            default:
                System.out.println(input[0] + ": command not found");
        }
    }

    private static void commandLS() {
        try (Stream<Path> files = Files.list(path)) {
            files.forEach(temp -> {
                try {
                    long size = Files.isDirectory(temp) ? directorySize(temp) : Files.size(temp);
                    System.out.println(temp.getFileName() + " " + (size / 1000) + " KB");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void commandCD(String s) {
        Path nPath = path.resolve(Paths.get(s)).normalize();
        if (Files.isDirectory(nPath)) {
            path = nPath;
            System.out.println(path);
        } else {
            System.out.println("cd: " + s + ": Not a directory");
        }
    }

    private static void commandMV(String what, String where) {
        Path source = null;
        try (Stream<Path> files = Files.list(path)) {
            source = files.filter(temp -> temp.getFileName().toString().equals(what) && Files.isRegularFile(temp))
                    .findFirst().orElse(null);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (source == null) {
            System.out.println("mv: " + what + " no such file");
            return;
        }

        Path target = isDirectory(where) ? path.resolve(where).resolve(source.getFileName()) : Paths.get(where);
        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void checkArgs(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--current-folder=")) {
            System.out.println("You should write one argument like \"--current-folder=YOUR_FOLDER\"");
            System.exit(-1);
        }
    }

    private static void checkPath(Path path) {
        if (!Files.isDirectory(path)) {
            System.out.println("Error: Path must be a directory");
            System.exit(-1);
        }
    }

    private static boolean isDirectory(String strPath) {
        Path nPath = path.resolve(Paths.get(strPath)).normalize();
        return Files.isDirectory(nPath);
    }

    private static long directorySize(Path path) throws IOException {
        return Files.walk(path)
                .filter(p -> p.toFile().isFile())
                .mapToLong(p -> p.toFile().length())
                .sum();
    }
}

