# Nate User Guide

Nate is a **chatbot for managing your task list, optimized for use through a Command Line Interface (CLI)**. If you type quickly, Nate can help you track your todos, deadlines, and events faster than a traditional app.

- [Quick start](#quick-start)
- [Features](#features)
    * [Adding a todo: `todo`](#adding-a-todo-todo)
    * [Adding a deadline: `deadline`](#adding-a-deadline-deadline)
    * [Adding an event: `event`](#adding-an-event-event)
    * [Listing all tasks: `list`](#listing-all-tasks-list)
    * [Marking a task as done: `mark`](#marking-a-task-as-done-mark)
    * [Marking a task as not done: `unmark`](#marking-a-task-as-not-done-unmark)
    * [Deleting a task: `delete`](#deleting-a-task-delete)
    * [Finding tasks by keyword: `find`](#finding-tasks-by-keyword-find)
    * [Exiting the program: `bye`](#exiting-the-program-bye)
    * [Saving the data](#saving-the-data)
    * [Editing the data file](#editing-the-data-file)
- [Command summary](#command-summary)

---

## Quick start

1. Ensure you have Java `25` or later installed on your computer.
2. Download the latest `nate.jar` from [here](https://github.com/nathanng-boop/ip/releases).
3. Copy the file to the folder you want to use as the *home folder* for Nate.
4. Open a terminal, `cd` to the folder containing the JAR file, and run `java -jar nate.jar`.
5. Type a command and press Enter to execute it. Some example commands you can try:
    - `list` : Lists all tasks.
    - `todo borrow book` : Adds a todo task named `borrow book`.
    - `delete 1` : Deletes the 1st task shown in the current list.
    - `bye` : Exits Nate.
6. Refer to the [Features](#features) section below for details of each command.

---

## Features

**Notes about the command format:**

- Words in `UPPER_CASE` are parameters to be supplied by you.
  For example, in `todo DESCRIPTION`, replace `DESCRIPTION` with a value such as `borrow book`.
- Task numbers used in `mark`, `unmark`, and `delete` refer to the index number shown in the most recent `list` (or `find`) output.
- The task number **must be a positive integer** 1, 2, 3, …

### Adding a todo: `todo`

Adds a task with no date or time attached.

Format: `todo DESCRIPTION`

Example: `todo borrow book`

### Adding a deadline: `deadline`

Adds a task that needs to be done by a specific date or time.

Format: `deadline DESCRIPTION /by DATE_OR_TIME`

Example: `deadline return book /by Sunday`

### Adding an event: `event`

Adds a task that starts and ends at a specific date/time.

Format: `event DESCRIPTION /from START /to END`

Example: `event project meeting /from Mon 2pm /to 4pm`

### Listing all tasks: `list`

Shows a list of all tasks currently saved.

Format: `list`

### Marking a task as done: `mark`

Marks the specified task as done.

Format: `mark TASK_NUMBER`

- The task number refers to the index number shown in the most recent `list` (or `find`) output.

Example: `mark 2` marks the 2nd task in the list as done.

### Marking a task as not done: `unmark`

Marks the specified task as not done.

Format: `unmark TASK_NUMBER`

Example: `unmark 2` marks the 2nd task in the list as not done.

### Deleting a task: `delete`

Deletes the specified task from the list.

Format: `delete TASK_NUMBER`

Example: `delete 1` deletes the 1st task in the list.

### Finding tasks by keyword: `find`

Finds tasks whose description contains the given keyword.

Format: `find KEYWORD`

- The search matches keywords found anywhere within a task's description.

Example: `find book` returns tasks like `read book` and `return book`.

### Exiting the program: `bye`

Exits Nate.

Format: `bye`

### Saving the data

Nate automatically saves your task list to disk after every command that changes it. You do not need to save manually.

### Editing the data file

Nate's data is saved automatically as a text file at `[JAR file location]/data/nate.txt`. Advanced users are welcome to update data directly by editing that data file.

**Caution:** If your changes make the data file invalid, Nate will skip the affected lines at the next run rather than loading them. Therefore, edit the data file only if you are confident that you can update it correctly.

---

## Command summary

| Action | Format, Examples |
|---|---|
| **Todo** | `todo DESCRIPTION` e.g., `todo borrow book` |
| **Deadline** | `deadline DESCRIPTION /by DATE_OR_TIME` e.g., `deadline return book /by Sunday` |
| **Event** | `event DESCRIPTION /from START /to END` e.g., `event meeting /from Mon 2pm /to 4pm` |
| **List** | `list` |
| **Mark** | `mark TASK_NUMBER` e.g., `mark 2` |
| **Unmark** | `unmark TASK_NUMBER` e.g., `unmark 2` |
| **Delete** | `delete TASK_NUMBER` e.g., `delete 1` |
| **Find** | `find KEYWORD` e.g., `find book` |
| **Bye** | `bye` |