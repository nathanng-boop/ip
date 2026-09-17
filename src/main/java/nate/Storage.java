package nate;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
}