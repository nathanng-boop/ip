package nate;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

    /**
     * Saves the given tasks to the data file, overwriting any existing content.
     *
     * @param tasks Array containing the tasks.
     * @param taskCount Number of tasks actually stored in the array.
     */
    public static void save(Task[] tasks, int taskCount) throws IOException {
        Path filePath = Path.of(FILE_PATH);
        Files.createDirectories(filePath.getParent());

        FileWriter writer = new FileWriter(filePath.toFile());
        for (int i = 0; i < taskCount; i++) {
            writer.write(toFileFormat(tasks[i]) + System.lineSeparator());
        }
        writer.close();
    }

    /**
     * Loads tasks from the data file into the given array, starting at index 0.
     * If the data file or its folder does not exist, no tasks are loaded and
     * zero is returned rather than throwing an error.
     * Lines that are corrupted or unreadable are skipped with a warning,
     * rather than stopping the entire load.
     *
     * @param tasks Array to populate with loaded tasks.
     * @return Number of tasks successfully loaded.
     */
    public static int load(Task[] tasks) {
        Path filePath = Path.of(FILE_PATH);

        if (!Files.exists(filePath)) {
            System.out.println("No saved tasks found. Starting with an empty list.");
            return 0;
        }

        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException e) {
            System.out.println("Warning! Could not read the data file! Starting with an empty list.");
            return 0;
        }

        int count = 0;
        for (String line : lines) {
            if (line.isBlank()) {
                continue;
            }
            if (count >= tasks.length) {
                System.out.println("Warning! Too many saved tasks, some were not loaded.");
                break;
            }
            Task task = fromFileFormat(line);
            if (task != null) {
                tasks[count] = task;
                count++;
            } else {
                System.out.println("Warning! Skipped a corrupted line in the data file: " + line);
            }
        }
        return count;
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