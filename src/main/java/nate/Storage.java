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
     *
     * @param tasks Array to populate with loaded tasks.
     * @return Number of tasks loaded.
     */
    public static int load(Task[] tasks) throws IOException {
        Path filePath = Path.of(FILE_PATH);
        List<String> lines = Files.readAllLines(filePath);

        int count = 0;
        for (String line : lines) {
            tasks[count] = fromFileFormat(line);
            count++;
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
            return "D | " + doneFlag + " | " + task.getDescription() + "| " + deadline.getBy();
        } else if (task instanceof Event) {
            Event event = (Event) task;
            return "E | " + doneFlag + " | " + task.getDescription()
                    + "| " + event.getFrom() + "| " + event.getTo();
        }
        return "";
    }

    /**
     * Converts a single pipe-delimited file line back into a Task.
     *
     * @param line Line read from the data file.
     * @return Reconstructed task.
     */
    private static Task fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        if (type.equals("T")) {
            task = new Todo(description);
        } else if (type.equals("D")) {
            task = new Deadline(description, parts[3]);
        } else {
            task = new Event(description, parts[3], parts[4]);
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}