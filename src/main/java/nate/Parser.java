package nate;

/**
 * Deals with making sense of user input: splitting a command line into
 * its command word and arguments.
 */
public class Parser {

    /**
     * Returns the command word (the first token) of the given input.
     *
     * @param input Full line typed by the user.
     * @return The command word, e.g. "todo" from "todo borrow book".
     */
    public static String getCommandWord(String input) {
        return input.split(" ", 2)[0];
    }

    /**
     * Returns the text after the given command prefix, or an empty string
     * if the input is not long enough to contain any arguments.
     *
     * @param input Full line typed by the user.
     * @param commandPrefix Prefix to strip, e.g. "todo ".
     * @return The remaining text after the prefix.
     */
    public static String extractArguments(String input, String commandPrefix) {
        return input.length() > commandPrefix.length() ? input.substring(commandPrefix.length()) : "";
    }

    /**
     * Parses the zero-based task index out of an input like "mark 2".
     * Accepts a missing or non-numeric argument gracefully by throwing
     * a NateException rather than letting a runtime exception escape.
     *
     * @param input Full line typed by the user.
     * @param commandPrefix Prefix to strip, e.g. "mark ".
     * @return Zero-based task index.
     * @throws NateException If no task number is given, or it isn't a valid number.
     */
    public static int parseIndex(String input, String commandPrefix) throws NateException {
        String argument = extractArguments(input, commandPrefix).trim();

        if (argument.isBlank()) {
            throw new NateException("Which task number did you mean?");
        }

        try {
            return Integer.parseInt(argument) - 1;
        } catch (NumberFormatException e) {
            throw new NateException("That doesn't look like a task number: " + argument);
        }
    }

    /**
     * Splits text on the first occurrence of the given separator.
     *
     * @param text Text to split.
     * @param separator Separator to split on, e.g. "/by ".
     * @return Array of at most two parts: text before and after the separator.
     */
    public static String[] splitOnce(String text, String separator) {
        return text.split(separator, 2);
    }
}