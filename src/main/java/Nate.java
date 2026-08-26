import java.util.Scanner;

public class Nate {
    public static void main(String[] args) {
        printLine();
        System.out.println("Hello! I'm Nate.");
        System.out.println("What can I do for you?");
        printLine();

        Scanner in = new Scanner(System.in);
        String input;

        int countOfTasks = 0;
        String[] listOfTasks = new String[100];

        while (true) {
            input = in.nextLine();
            printLine();

            if (input.equals("bye")){
                System.out.println("Byebye. Hope to see you soon!");
                printLine();
                break;
            } else if (input.equals("list")) {
                for (int i = 0; i < countOfTasks; i++) {
                    System.out.println(i + 1 + ". " + listOfTasks[i]);
                }
                printLine();
            } else {
                System.out.println("added: " + input);
                printLine();
                listOfTasks[countOfTasks] = input;
                countOfTasks++;
            }
        }

        in.close();
    }

    private static void printLine(){
        System.out.println("________________________________________");
    }
}
