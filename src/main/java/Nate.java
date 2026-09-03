import java.util.Scanner;

/**
 * Represents a simple chatbot that stores tasks entered by the user
 * and supports listing, marking, and unmarking tasks as done.
 */
public class Nate {

    private static int countOfTasks = 0;
    private static Task[] listOfTasks = new Task[100];

    private static final String COMMAND_MARK = "mark ";
    private static final String COMMAND_UNMARK = "unmark ";
    private static final String COMMAND_TODO = "todo ";
    private static final String COMMAND_DEADLINE = "deadline ";
    private static final String COMMAND_EVENT = "event ";
    private static final String DEADLINE_SEPARATOR = " /by ";
    private static final String EVENT_FROM_SEPARATOR = " /from ";
    private static final String EVENT_TO_SEPARATOR = " /to ";

    /**
     * Runs the Nate chatbot, reading user commands until "bye" is entered.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        printGreeting();

        boolean isRunning = true;
        Scanner in = new Scanner(System.in);
        String input;

        while (isRunning) {
            input = in.nextLine();
            String commandWord = input.split(" ", 2)[0];
            printLine();

            switch (commandWord) {
                case "bye":
                    handleBye();
                    isRunning = false;
                    break;
                case "list":
                    handleList();
                    break;
                case "mark":
                    handleMark(input);
                    break;
                case "unmark":
                    handleUnmark(input);
                    break;
                case "todo":
                    handleTodo(input);
                    break;
                case "deadline":
                    handleDeadline(input);
                    break;
                case "event":
                    handleEvent(input);
                    break;
                default:
                    handleTodo(input);
                    break;
            }
            printLine();
        }
        in.close();
    }

    /** Prints the chatbot's logo and greeting. */
    private static void printGreeting() {
        String logo = "    _   _____  ____________\n"
                + "   / | / /   |/_  __/ ____/\n"
                + "  /  |/ / /| | / / / __/   \n"
                + " / /|  / ___ |/ / / /___   \n"
                + "/_/ |_/_/  |_/_/ /_____/   \n";
        System.out.println("Hello from\n" + logo);

        printLine();
        System.out.println("Welcome! I'm Nate.");
        System.out.println("How can I help you? Feel free to ask me anything :)");
        printLine();
    }

    /** Prints a horizontal divider line. */
    private static void printLine() {
        System.out.println("________________________________________");
    }

    /** Prints the farewell message. */
    private static void handleBye() {
        System.out.println("Byebye. Hope to see you soon!");
    }

    /** Prints all tasks currently in the list. */
    private static void handleList() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < countOfTasks; i++) {
            System.out.println((i + 1) + "." + listOfTasks[i].getTaskLine());
        }
    }

    /** Marks the task specified in the input as done. */
    private static void handleMark(String input) {
        int taskIndex = Integer.parseInt(input.substring(COMMAND_MARK.length())) - 1;
        if (taskIndex < 0 || taskIndex >= countOfTasks) {
            System.out.println("That task number doesn't exist.");
            return;
        }
        listOfTasks[taskIndex].markAsDone();
        System.out.println("Good job! I've marked this task as done:");
        System.out.println("  " + listOfTasks[taskIndex].getTaskLine());
    }

    /** Marks the task specified in the input as not done. */
    private static void handleUnmark(String input) {
        int taskIndex = Integer.parseInt(input.substring(COMMAND_UNMARK.length())) - 1;
        if (taskIndex < 0 || taskIndex >= countOfTasks) {
            System.out.println("That task number doesn't exist.");
            return;
        }
        listOfTasks[taskIndex].markAsNotDone();
        System.out.println("Okay, I've marked this task as not done yet:");
        System.out.println("  " + listOfTasks[taskIndex].getTaskLine());
    }

    /** Adds a Todo task using the given input. */
    private static void handleTodo(String input) {
        String description = input.startsWith(COMMAND_TODO) ? input.substring(COMMAND_TODO.length()) : input;
        addTask(new Todo(description));
    }

    /** Adds a Deadline task using the given input. */
    private static void handleDeadline(String input) {
        String details = input.substring(COMMAND_DEADLINE.length());
        String[] parts = details.split(DEADLINE_SEPARATOR, 2);
        addTask(new Deadline(parts[0], parts[1]));
    }

    /** AAdds an Event task using the given input. */
    private static void handleEvent(String input) {
        String details = input.substring(COMMAND_EVENT.length());
        String[] fromSplit = details.split(EVENT_FROM_SEPARATOR, 2);
        String[] toSplit = fromSplit[1].split(EVENT_TO_SEPARATOR, 2);
        addTask(new Event(fromSplit[0], toSplit[0], toSplit[1]));
    }

    /** Adds the given task to the task list and prints the confirmation message. */
    private static void addTask (Task task) {
        listOfTasks[countOfTasks] = task;
        countOfTasks++;
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task.getTaskLine());
        System.out.println("Now you have " + countOfTasks + " tasks in the list.");
    }
}
