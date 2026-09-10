/**
 * Represents an exception specific to the Nate chatbot.
 * Thrown when user input cannot be processed correctly.
 */
public class NateException extends Exception {

    /** Creates a NateException with the given error message. */
    public NateException(String errorMessage) {
        super(errorMessage);
    }
}
