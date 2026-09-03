public class Todo extends Task {
    public Todo(String description) {
            super(description);
    }

    @Override
    public String getTaskLine() {
        return "[T]" + super.getTaskLine();
    }
}
