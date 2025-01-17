package kg.org.glovoweb.Requests;

import lombok.Data;

@Data
public class AuthRegisterRequest {
    private String username;
    private String email;
    private String password;
}
