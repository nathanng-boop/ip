import java.util.Scanner;

public class Nate {
    public static void main(String[] args) {
        printLine();
        System.out.println("Hello! I'm Nate.");
        System.out.println("What can I do for you?");
        printLine();

        Scanner in = new Scanner(System.in);
        String input;

        while (true) {
            input = in.nextLine();
            printLine();

            if (input.equals("bye")) {
                System.out.println("Byebye. Hope to see you soon!");
                printLine();
                break;
            } else {
                System.out.println(input);
                printLine();
            }
        }
        in.close();
    }

    private static void printLine(){
        System.out.println("________________________________________");
    }
}
