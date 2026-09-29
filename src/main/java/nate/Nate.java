package nate;

import java.util.ArrayList;
import java.io.IOException;
import nate.task.Task;
import nate.task.Todo;
import nate.task.Deadline;
import nate.task.Event;

/**
 * Represents a simple chatbot that stores tasks entered by the user
 * and supports listing, marking, and unmarking tasks as done.
 */
public class Nate {

    private static final String COMMAND_MARK = "mark ";
    private static final String COMMAND_UNMARK = "unmark ";
    private static final String COMMAND_TODO = "todo ";
    private static final String COMMAND_DEADLINE = "deadline ";
    private static final String COMMAND_EVENT = "event ";
    private static final String DEADLINE_SEPARATOR = "/by ";
    private static final String EVENT_FROM_SEPARATOR = "/from ";
    private static final String EVENT_TO_SEPARATOR = "/to ";
    private static final String COMMAND_DELETE = "delete ";
    private static final String COMMAND_FIND = "find ";

    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    /**
     * Creates a Nate chatbot that saves to and loads from the given file path.
     *
     * @param filePath Relative path to the data file, e.g. "data/nate.txt".
     */
    public Nate(String filePath) {
        storage = new Storage(filePath);
        ui = new Ui();
    }

    /** Runs the Nate chatbot, reading user commands until "bye" is entered. */
    public void run() {
        ui.showWelcome();
        tasks = storage.load();

        boolean isRunning = true;

        while (isRunning) {
            String input = ui.readCommand();
            String commandWord = Parser.getCommandWord(input);
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
                    case "find":
                        handleFind(input);
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
    private void handleBye() {
        ui.showGoodbye();
    }

    /** Prints all tasks currently in the list. */
    private void handleList() {
        ui.showTaskList(tasks.asList());
    }

    /** Marks the task specified in the input as done. */
    private void handleMark(String input) throws NateException {
        int taskIndex = Parser.parseIndex(input, COMMAND_MARK);

        if (!tasks.isValidIndex(taskIndex)) {
            throw new NateException("Task number nowhere to be found...");
        }

        tasks.get(taskIndex).markAsDone();
        saveTasks();

        ui.showTaskMarked(tasks.get(taskIndex));
    }

    /** Marks the task specified in the input as not done. */
    private void handleUnmark(String input) throws NateException {
        int taskIndex = Parser.parseIndex(input, COMMAND_UNMARK);

        if (!tasks.isValidIndex(taskIndex)) {
            throw new NateException("Task number nowhere to be found...");
        }

        tasks.get(taskIndex).markAsNotDone();
        saveTasks();

        ui.showTaskUnmarked(tasks.get(taskIndex));
    }

    /** Adds a Todo task using the given input. */
    private void handleTodo(String input) throws NateException {
        String description = input.startsWith(COMMAND_TODO) ? Parser.extractArguments(input, COMMAND_TODO) : input;

        if (description.isBlank()) {
            throw new NateException("Might you be missing a task description? :o");
        }

        addTask(new Todo(description));
    }

    /** Adds a Deadline task using the given input. */
    private void handleDeadline(String input) throws NateException {
        String details = Parser.extractArguments(input, COMMAND_DEADLINE);

        if (details.isBlank()) {
            throw new NateException("Deadline description is missing!");
        }

        if (!details.contains(DEADLINE_SEPARATOR)) {
            throw new NateException("Deadline must include '/by' followed by the due date/time.");
        }

        String[] parts = Parser.splitOnce(details, DEADLINE_SEPARATOR);
        String description = parts[0].trim();

        if (description.isBlank()) {
            throw new NateException("Deadline description is missing!");
        }

        addTask(new Deadline(description, parts[1].trim()));
    }


    /** Adds an Event task using the given input. */
    private void handleEvent(String input) throws NateException {
        String details = Parser.extractArguments(input, COMMAND_EVENT);

        if (details.isBlank()) {
            throw new NateException("Event description is missing!");
        }

        if (!details.contains(EVENT_FROM_SEPARATOR) || !details.contains(EVENT_TO_SEPARATOR)) {
            throw new NateException("Event must include '/from' and '/to' with the relevant dates/times");
        }

        String[] fromSplit = Parser.splitOnce(details, EVENT_FROM_SEPARATOR);
        String description = fromSplit[0].trim();

        if (description.isBlank()) {
            throw new NateException("Event description is missing!");
        }

        String[] toSplit = Parser.splitOnce(fromSplit[1], EVENT_TO_SEPARATOR);

        String from = toSplit[0].trim();
        String to = toSplit[1].trim();
        addTask(new Event(description, from, to));
    }

    /** Deletes a task from the list based on the task index. */
    private void handleDelete(String input) throws NateException {
        int taskIndex = Parser.parseIndex(input, COMMAND_DELETE);

        if (!tasks.isValidIndex(taskIndex)) {
            throw new NateException("Task number nowhere to be found...");
        }
        Task removedTask = tasks.remove(taskIndex);
        ui.showTaskRemoved(removedTask, tasks.size());
        saveTasks();
    }

    /** Finds and prints all tasks whose description contains the given keyword. */
    private void handleFind(String input) throws NateException {
        String keyword = Parser.extractArguments(input, COMMAND_FIND);

        if (keyword.isBlank()) {
            throw new NateException("What are you searching for? Please retry!");
        }

        ui.showMatchingTasks(tasks.find(keyword));
    }

    /** Adds the given task to the task list and prints the confirmation message. */
    private void addTask (Task task) {
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
        saveTasks();
    }

    private void saveTasks() {
        try {
            storage.save(tasks);
        } catch (IOException e) {
            ui.showMessage("Warning! Could not save tasks to disk.");
        }
    }

    /**
     * Starts the Nate chatbot.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Nate("data" + java.io.File.separator + "nate.txt").run();
    }
}
