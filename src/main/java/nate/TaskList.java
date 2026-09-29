package nate;

import java.util.ArrayList;
import nate.task.Task;

/**
 * Represents the list of tasks, with operations to add, remove and query tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /** Creates an empty TaskList. */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Creates a TaskList pre-populated with the given tasks, e.g. loaded from storage.
     *
     * @param tasks Initial tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /** Adds a task to the list. */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the given index.
     *
     * @param index Zero-based index of the task to remove.
     * @return The removed task.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the given index.
     *
     * @param index Zero-based index of the task.
     * @return Task at that index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns whether the given index is a valid position in the list.
     *
     * @param index Zero-based index to check.
     * @return True if the index refers to an existing task.
     */
    public boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    /**
     * Returns the underlying list of tasks, e.g. for display or saving to disk.
     *
     * @return The tasks in this list.
     */
    public ArrayList<Task> asList() {
        return tasks;
    }
}
