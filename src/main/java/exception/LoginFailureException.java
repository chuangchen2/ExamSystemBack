package exception;

public class LoginFailureException extends Exception {

    public LoginFailureException() {
        super();
    }
    public LoginFailureException(String message) {
        super(message);
    }
}
