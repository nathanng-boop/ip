/**
 * Represents a task that needs to be done before a specific date/time.
 */
public class Deadline extends Task {
    protected String by;


    /**
     * Creates a Deadline with the given description and due date/time.
     *
     * @param description Description of the deadline.
     * @param by Date/time by which the task should be done.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns a formatted line representing the deadline, including its type icon,
     * status icon, and due date/time.
     *
     * @return Formatted line in the form "[D][X] description (by: ...)".
     */
    @Override
    public String getTaskLine() {
        return "[D]" + super.getTaskLine() + " (by: " + by + ")";
    }
}
