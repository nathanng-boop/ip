package nate;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import nate.task.Deadline;
import nate.task.Event;
import nate.task.Task;
import nate.task.Todo;

/**
 * Handles saving the task list to a data file on disk.
 */
public class Storage {
    private static final String FILE_PATH = "data" + java.io.File.separator + "nate.txt";
    private static final int EXPECTED_PARTS_TODO = 3;
    private static final int EXPECTED_PARTS_DEADLINE = 4;
    private static final int EXPECTED_PARTS_EVENT = 5;

    private final String filePath;

    /**
     * Creates a Storage that reads from and writes to the given file path.
     *
     * @param filePath Relative path to the data file, e.g. "data/nate.txt".
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Saves the given tasks to the data file, overwriting any existing content.
     *
     * @param tasks List of tasks to save.
     */
    public static void save(TaskList  tasks) throws IOException {
        Path filePath = Path.of(FILE_PATH);
        Files.createDirectories(filePath.getParent());

        FileWriter fw = new FileWriter(filePath.toFile());
        for (Task task : tasks.asList()) {
            fw.write(toFileFormat(task) + System.lineSeparator());
        }
        fw.close();
    }

    /**
     * Loads tasks from the data file into a new TaskList.
     * If the file does not exist, an empty TaskList is returned.
     * Lines that are corrupted are skipped with a warning.
     *
     * @return TaskList containing the tasks read from disk.
     */
    public TaskList load() {
        TaskList tasks = new TaskList();
        Path filePath = Path.of(FILE_PATH);

        if (!Files.exists(filePath)) {
            System.out.println("No saved tasks found. Starting with an empty list.");
            return tasks;
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            System.out.println("Warning! Could not read the data file! Starting with an empty list.");
            return tasks;
        }

        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }
            Task task = fromFileFormat(line);
            if (task != null) {
                tasks.add(task);
            } else {
                System.out.println("Warning! Skipped a corrupted line in the data file: " + line);
            }
        }
        return tasks;
    }

    /**
     * Converts a single task into its pipe-delimited file format.
     *
     * @param task Task to convert.
     * @return Formatted line representing the task.
     */
    private static String toFileFormat(Task task) {
        String doneFlag = task.isDone() ? "1" : "0";
        if (task instanceof Todo) {
            return "T | " + doneFlag + " | " + task.getDescription();
        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return "D | " + doneFlag + " | " + task.getDescription() + " | " + deadline.getBy();
        } else if (task instanceof Event) {
            Event event = (Event) task;
            return "E | " + doneFlag + " | " + task.getDescription()
                    + " | " + event.getFrom() + " | " + event.getTo();
        }
        return "";
    }

    /**
     * Converts a single pipe-delimited file line back into a Task.
     * Returns null if the line is malformed in any way, so the caller
     * can skip it instead of crashing.
     *
     * @param line Line read from the data file.
     * @return Reconstructed task, or null if the line could not be parsed.
     */
    private static Task fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < EXPECTED_PARTS_TODO) {
            return null;
        }

        String type = parts[0];
        String doneFlagText = parts[1];
        String description = parts[2];

        if (!doneFlagText.equals("0") && !doneFlagText.equals("1")) {
            return null;
        }
        boolean isDone = doneFlagText.equals("1");

        Task task;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                if (parts.length < EXPECTED_PARTS_DEADLINE) {
                    return null;
                }
                task = new Deadline(description, parts[3]);
                break;
            case "E":
                if (parts.length < EXPECTED_PARTS_EVENT) {
                    return null;
                }
                task = new Event(description, parts[3], parts[4]);
                break;
            default:
                return null;
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}