package kg.org.glovoweb.Exceptions;

public class AuthenticationFailedEx extends RuntimeException {
    public AuthenticationFailedEx(String message) {
        super(message);
    }
}
