package nate;

import java.util.ArrayList;
import java.io.IOException;
import java.util.Scanner;
import nate.task.Task;
import nate.task.Todo;
import nate.task.Deadline;
import nate.task.Event;

/**
 * Represents a simple chatbot that stores tasks entered by the user
 * and supports listing, marking, and unmarking tasks as done.
 */
public class Nate {

    private static ArrayList<Task> listOfTasks = new ArrayList<>();
    private static final Ui ui = new Ui();

    private static final String COMMAND_MARK = "mark ";
    private static final String COMMAND_UNMARK = "unmark ";
    private static final String COMMAND_TODO = "todo ";
    private static final String COMMAND_DEADLINE = "deadline ";
    private static final String COMMAND_EVENT = "event ";
    private static final String DEADLINE_SEPARATOR = "/by ";
    private static final String EVENT_FROM_SEPARATOR = "/from ";
    private static final String EVENT_TO_SEPARATOR = "/to ";
    private static final String COMMAND_DELETE = "delete ";

    /**
     * Runs the Nate chatbot, reading user commands until "bye" is entered.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        ui.showWelcome();

        Storage.load(listOfTasks);

        boolean isRunning = true;

        while (isRunning) {
            String input = ui.readCommand();
            String commandWord = input.split(" ", 2)[0];
            ui.showLine();

            try {
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
                    case "delete":
                        handleDelete(input);
                        break;
                    default:
                        throw new NateException("Apologies, I do not understand that command :<");
                    }
                } catch (NateException e) {
                    ui.showError(e.getMessage());
            }
            ui.showLine();
        }
        ui.close();
    }

    /** Prints the farewell message. */
    private static void handleBye() {
        ui.showGoodbye();
    }

    /** Prints all tasks currently in the list. */
    private static void handleList() {
        ui.showTaskList(listOfTasks);
    }

    /** Marks the task specified in the input as done. */
    private static void handleMark(String input) throws NateException {
        int taskIndex = Integer.parseInt(input.substring(COMMAND_MARK.length())) - 1;

        if (taskIndex < 0 || taskIndex >= listOfTasks.size()) {
            throw new NateException("Task number nowhere to be found...");
        }

        listOfTasks.get(taskIndex).markAsDone();
        saveTasks();

        ui.showTaskMarked(listOfTasks.get(taskIndex));
    }

    /** Marks the task specified in the input as not done. */
    private static void handleUnmark(String input) throws NateException {
        int taskIndex = Integer.parseInt(input.substring(COMMAND_UNMARK.length())) - 1;

        if (taskIndex < 0 || taskIndex >= listOfTasks.size()) {
            throw new NateException("Task number nowhere to be found...");
        }

        listOfTasks.get(taskIndex).markAsNotDone();
        saveTasks();

        ui.showTaskUnmarked(listOfTasks.get(taskIndex));
    }

    /** Adds a Todo task using the given input. */
    private static void handleTodo(String input) throws NateException {
        String description = input.startsWith(COMMAND_TODO) ? input.substring(COMMAND_TODO.length()) : input;

        if (description.isBlank()) {
            throw new NateException("Might you be missing a task description? :o");
        }

        addTask(new Todo(description));
    }

    /** Adds a Deadline task using the given input. */
    private static void handleDeadline(String input) throws NateException {
        String details = input.length() > COMMAND_DEADLINE.length() ? input.substring(COMMAND_DEADLINE.length()) : "";

        if (details.isBlank()) {
            throw new NateException("Deadline description is missing!");
        }

        if (!details.contains(DEADLINE_SEPARATOR)) {
            throw new NateException("Deadline must include '/by' followed by the due date/time.");
        }

        String[] parts = details.split(DEADLINE_SEPARATOR, 2);
        String description = parts[0].trim();

        if (description.isBlank()) {
            throw new NateException("Deadline description is missing!");
        }

        addTask(new Deadline(description, parts[1].trim()));
    }


    /** Adds an Event task using the given input. */
    private static void handleEvent(String input) throws NateException {
        String details = input.length() > COMMAND_EVENT.length() ? input.substring(COMMAND_EVENT.length()) : "";

        if (details.isBlank()) {
            throw new NateException("Event description is missing!");
        }

        if (!details.contains(EVENT_FROM_SEPARATOR) || !details.contains(EVENT_TO_SEPARATOR)) {
            throw new NateException("Event must include '/from' and '/to' with the relevant dates/times");
        }

        String[] fromSplit = details.split(EVENT_FROM_SEPARATOR, 2);
        String description = fromSplit[0].trim();

        if (description.isBlank()) {
            throw new NateException("Event description is missing!");
        }

        String[] toSplit = fromSplit[1].split(EVENT_TO_SEPARATOR, 2);

        String from = toSplit[0].trim();
        String to = toSplit[1].trim();
        addTask(new Event(description, from, to));
    }

    private static void handleDelete(String input) throws NateException {
        int taskIndex = Integer.parseInt(input.substring(COMMAND_DELETE.length())) - 1;
        if (taskIndex < 0 || taskIndex >= listOfTasks.size()) {
            throw new NateException("Task number nowhere to be found...");
        }
        Task removedTask = listOfTasks.remove(taskIndex);
        ui.showTaskRemoved(removedTask, listOfTasks.size());
        saveTasks();
    }

    /** Adds the given task to the task list and prints the confirmation message. */
    private static void addTask (Task task) {
        listOfTasks.add(task);
        ui.showTaskAdded(task, listOfTasks.size());
        saveTasks();
    }

    private static void saveTasks() {
        try {
            Storage.save(listOfTasks);
        } catch (IOException e) {
            ui.showMessage("Warning! Could not save tasks to disk.");
        }
    }
}
