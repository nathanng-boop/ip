/**
 * Represents a task with no date/time attached to it.
 */
public class Todo extends Task {
    /** Creates a Todo with the given description. */
    public Todo(String description) {
            super(description);
    }

    /**
     * Returns a formatted line representing the todo, including its type and status icons.
     *
     * @return Formatted line in the form "[T][X] description".
     */
    @Override
    public String getTaskLine() {
        return "[T]" + super.getTaskLine();
    }
}
