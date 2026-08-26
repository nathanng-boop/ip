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
        Task[] listOfTasks = new Task[100];

        while (true) {
            input = in.nextLine();
            printLine();

            if (input.equals("bye")) {
                System.out.println("Byebye. Hope to see you soon!");
                printLine();
                break;
            } else if (input.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < countOfTasks; i++) {
                    System.out.println((i + 1) + "." + listOfTasks[i].getTaskLine());
                }
                printLine();
            } else if (input.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(input.substring(5)) - 1;
                if (taskIndex >= 0 && taskIndex < countOfTasks) {
                    listOfTasks[taskIndex].markAsDone();
                    System.out.println("Good job! I've marked this task as done:");
                    System.out.println("  " + listOfTasks[taskIndex].getTaskLine());
                }
                printLine();
            } else if (input.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(input.substring(7)) - 1;
                if (taskIndex >= 0 && taskIndex < countOfTasks) {
                    listOfTasks[taskIndex].markAsNotDone();
                    System.out.println("Okay, I've marked this task as not done yet:");
                    System.out.println("  " + listOfTasks[taskIndex].getTaskLine());
                }
                printLine();
            } else {
                listOfTasks[countOfTasks] = new Task(input);
                countOfTasks++;
                System.out.println("added: " + input);
                printLine();
            }
        }

        in.close();
    }

    private static void printLine() {
        System.out.println("________________________________________");
    }
}
