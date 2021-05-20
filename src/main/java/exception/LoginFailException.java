package exception;

public class LoginFailException extends Exception {

    public LoginFailException() {
        super();
    }
    public LoginFailException(String message) {
        super(message);
    }
}
