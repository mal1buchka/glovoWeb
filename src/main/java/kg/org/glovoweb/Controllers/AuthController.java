package kg.org.glovoweb.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.org.glovoweb.Requests.AuthLoginRequest;
import kg.org.glovoweb.Requests.AuthRegisterRequest;
import kg.org.glovoweb.Requests.RefreshAccessTokenRequest;
import kg.org.glovoweb.Services.Impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthServiceImpl authService;


    @Autowired
    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthLoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid AuthRegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshAccessTokenRequest refreshRequest) {
        return authService.refreshAccessToken(refreshRequest);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        return authService.logout(request);
    }

}
