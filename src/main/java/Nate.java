import java.util.Scanner;

/**
 * Represents a simple chatbot that stores tasks entered by the user
 * and supports listing, marking, and unmarking tasks as done.
 */
public class Nate {

    /**
     * Runs the Nate chatbot, reading user commands until "bye" is entered.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        String logo = "    _   _____  ____________\n"
                + "   / | / /   |/_  __/ ____/\n"
                + "  /  |/ / /| | / / / __/   \n"
                + " / /|  / ___ |/ / / /___   \n"
                + "/_/ |_/_/  |_/_/ /_____/   \n";
        System.out.println("Hello from\n" + logo);

        printLine();
        System.out.println("Welcome! I'm Nate.");
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
            } else if (input.startsWith("todo ")) {
                String description = input.substring("todo ".length());
                listOfTasks[countOfTasks] = new Todo(description);
                countOfTasks++;
                printAddedMessage(listOfTasks[countOfTasks - 1], countOfTasks);
                printLine();
            } else if (input.startsWith("deadline ")) {
                String details = input.substring("deadline ".length());
                String[] parts = details.split(" /by ", 2);
                listOfTasks[countOfTasks] = new Deadline(parts[0], parts[1]);
                countOfTasks++;
                printAddedMessage(listOfTasks[countOfTasks - 1], countOfTasks);
                printLine();
            } else if (input.startsWith("event ")) {
                String details = input.substring("event ".length());
                String[] fromSplit = details.split(" /from ", 2);
                String[] toSplit = fromSplit[1].split(" /to ", 2);
                listOfTasks[countOfTasks] = new Event(fromSplit[0], toSplit[0], toSplit[1]);
                countOfTasks++;
                printAddedMessage(listOfTasks[countOfTasks - 1], countOfTasks);
                printLine();
            } else {
                listOfTasks[countOfTasks] = new Todo(input);
                countOfTasks++;
                printAddedMessage(listOfTasks[countOfTasks - 1], countOfTasks);
                printLine();
            }
        }

        in.close();
    }

    private static void printAddedMessage(Task task, int totalTasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task.getTaskLine());
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    /** Prints a horizontal divider line. */
    private static void printLine() {
        System.out.println("________________________________________");
    }
}
