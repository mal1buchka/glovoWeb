package kg.org.glovoweb.Requests;

import lombok.Data;

@Data
public class AuthLoginRequest {
    private String email;
    private String password;
}
