/**
 * Represents a task that starts and ends at specific date/times.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates an Event with the given description, start time, and end time.
     *
     * @param description Description of the event.
     * @param from Start date/time of the event.
     * @param to End date/time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a formatted line representing the event, including its type icon,
     * status icon, and start/end date/time.
     *
     * @return Formatted line in the form "[E][X] description (from: ... to: ...)".
     */
    @Override
    public String getTaskLine() {
        return "[E]" + super.getTaskLine() + " (from: " + from + " to: " + to + ")";
    }
}
