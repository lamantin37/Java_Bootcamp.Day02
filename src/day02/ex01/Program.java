package day02.ex01;


public class Program {
    public static void main(String[] args) {
        FileReader fl = new FileReader(args[0], args[1]);
        System.out.print(fl.countIncludes());
    }
}
