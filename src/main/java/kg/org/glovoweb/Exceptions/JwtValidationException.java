package kg.org.glovoweb.Exceptions;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtValidationException extends RuntimeException {
    public JwtValidationException(String message, Exception e) {
        super(message);
    }
}
