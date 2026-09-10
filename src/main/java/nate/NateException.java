package nate;

/**
 * Represents an exception specific to the nate.Nate chatbot.
 * Thrown when user input cannot be processed correctly.
 */
public class NateException extends Exception {

    /** Creates a nate.NateException with the given error message. */
    public NateException(String errorMessage) {
        super(errorMessage);
    }
}
