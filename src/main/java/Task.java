/**
 * Represents a task with a description and a completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a Task with the given description.
     * The task is initially marked as not done.
     *
     * @param description Description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon representing whether the task is done.
     *
     * @return "X" if the task is done, otherwise a blank space.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Returns the description of the task.
     *
     * @return Description of the task.
     */
    public String getDescription() {
        return description;
    }

    /** Marks the task as done. */
    public void markAsDone() {
        this.isDone = true;
    }

    /** Marks the task as not done. */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns a formatted line representing the task, including its status icon.
     * Subclasses override this to add their own type icon and extra details.
     *
     * @return Formatted task line in the form "[X] description".
     */
    public String getTaskLine() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
