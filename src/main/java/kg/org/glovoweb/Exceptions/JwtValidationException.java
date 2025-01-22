package kg.org.glovoweb.Exceptions;


public class JwtValidationException extends RuntimeException {
    public JwtValidationException(String message, Exception e) {
        super(message);
    }
}
