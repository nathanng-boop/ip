package nate;

import java.util.ArrayList;
import java.util.Scanner;
import nate.task.Task;

/**
 * Deals with all interactions with the user: reading commands and printing messages.
 */
public class Ui {
    private static final String LINE = "________________________________________";
    private static final String LOGO = "    _   _____  ____________\n"
            + "   / | / /   |/_  __/ ____/\n"
            + "  /  |/ / /| | / / / __/   \n"
            + " / /|  / ___ |/ / / /___   \n"
            + "/_/ |_/_/  |_/_/ /_____/   \n";

    private final Scanner in;

    /** Creates a Ui that reads user input from standard input. */
    public Ui() {
        in = new Scanner(System.in);
    }

    /**
     * Reads the next line of input from the user.
     *
     * @return The full command line typed by the user.
     */
    public String readCommand() {
        return in.nextLine();
    }

    /** Releases the input resource held by this Ui. */
    public void close() {
        in.close();
    }

    /** Prints a horizontal divider line. */
    public void showLine() {
        System.out.println(LINE);
    }

    /** Prints the chatbot's logo and welcome message. */
    public void showWelcome() {
        System.out.println("Hello from\n" + LOGO);
        showLine();
        System.out.println("Welcome! I'm Nate.");
        System.out.println("How can I help you? Feel free to ask me anything :)");
        showLine();
    }

    /** Prints the farewell message. */
    public void showGoodbye() {
        System.out.println("Byebye! Hope to see you soon!");
    }

    /**
     * Prints an error message to the user.
     *
     * @param message Description of what went wrong.
     */
    public void showError(String message) {
        System.out.println("Uh oh! " + message);
    }

    /**
     * Prints a plain message to the user.
     *
     * @param message Message to print.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints every task in the given list, numbered from 1.
     *
     * @param tasks Tasks to display.
     */
    public void showTaskList(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i).getTaskLine());
        }
    }

    /**
     * Prints the confirmation shown after a task is added.
     *
     * @param task Task that was added.
     * @param totalTasks Number of tasks now in the list.
     */
    public void showTaskAdded(Task task, int totalTasks) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task.getTaskLine());
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Prints the confirmation shown after a task is removed.
     *
     * @param task Task that was removed.
     * @param totalTasks Number of tasks left in the list.
     */
    public void showTaskRemoved(Task task, int totalTasks) {
        System.out.println("Task removed:");
        System.out.println("  " + task.getTaskLine());
        System.out.println("Now you have " + totalTasks + " tasks in the list.");
    }

    /**
     * Prints the confirmation shown after a task is marked as done.
     *
     * @param task Task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Good job! I've marked this task as done:");
        System.out.println("  " + task.getTaskLine());
    }

    /**
     * Prints the confirmation shown after a task is marked as not done.
     *
     * @param task Task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("Okay, I've marked this task as not done yet:");
        System.out.println("  " + task.getTaskLine());
    }
}