package kg.org.glovoweb.Services.Impl;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.org.glovoweb.Models.User;
import kg.org.glovoweb.Repositories.UserRepository;
import kg.org.glovoweb.Requests.AuthLoginRequest;
import kg.org.glovoweb.Requests.AuthRegisterRequest;
import kg.org.glovoweb.Requests.LogoutRequest;
import kg.org.glovoweb.Requests.RefreshAccessTokenRequest;
import kg.org.glovoweb.Responses.AuthResponse;
import kg.org.glovoweb.Security.JwtCore;
import kg.org.glovoweb.Security.UserDetailImpl;
import kg.org.glovoweb.Services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Validated
@Service
public class AuthServiceImpl {


    private final JwtCore jwtCore;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserService userService;

    private final Map<String, String> refreshTokenStorage = new HashMap<>();

    public AuthServiceImpl(JwtCore jwtCore, AuthenticationManager authenticationManager, UserRepository userRepository, UserService userService) {
        this.jwtCore = jwtCore;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public ResponseEntity<?> login(@Valid AuthLoginRequest loginRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));
            UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
            String accessToken = jwtCore.generateAccessToken(userDetail);
            String refreshToken = jwtCore.generateRefreshToken(userDetail);
            refreshTokenStorage.put(userDetail.getUsername(), refreshToken); // getUsername returns email of the user
            log.info("User: {} has successfully logged into our system", userDetail.getUsername());
            AuthResponse authResponse = new AuthResponse(accessToken, refreshToken);
            return ResponseEntity.ok(authResponse);
        }catch (BadCredentialsException e) {
            log.warn("Invalid email or password for user: {}", loginRequestDTO.getEmail());
            return ResponseEntity.status(401).body("Invalid credentials");
        }catch (JwtException e) {
            log.error("Invalid token for user: {}", loginRequestDTO.getEmail());
            return ResponseEntity.status(401).body("Jwt error has occurred: " + e.getMessage());
        }catch (Exception e) {
            log.error("Unexpected error: {}, while trying to login user: {}", e.getMessage(), loginRequestDTO.getEmail());
            return ResponseEntity.status(500).body("Something went wrong on our system: " + e.getMessage());
        }
    }
    public ResponseEntity<?> refreshAccessToken(@Valid RefreshAccessTokenRequest request) {

        try{
            String email = jwtCore.getEmailFromRefreshToken(request.getRefreshToken());
            if(!email.equals(request.getEmail())){
                log.warn("Mismatch email while equalizing emails from request and from accessToken for user: {}", email);
                return ResponseEntity.status(401).body("Invalid email for user: " + email);
            }
            if(!refreshTokenStorage.containsKey(request.getEmail())) {
                log.warn("Such refresh token for user doesn't exist in our hashmap while stage1: {}", request.getEmail());
                return ResponseEntity.status(401).body("Refresh token does not exist for user: " + request.getEmail());
            }
            String refreshToken = refreshTokenStorage.get(email);
            if(refreshToken == null || !jwtCore.validateRefreshToken(refreshToken)) {
                log.warn("Refresh token for user can't be validated: {}", request.getEmail());
                return ResponseEntity.status(401).body("Refresh token cannot be validated: " + request.getEmail());
            }
            if(!refreshToken.equals(refreshTokenStorage.get(email))) {
                log.warn("Refresh token for user doesn't exist in our hashmap while stage2: {}", request.getEmail());
                return ResponseEntity.status(401).body("Refresh token does not exist for user: " + request.getEmail());
            }

            User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
            UserDetailImpl userDetail = new UserDetailImpl(user);
            String refreshedAccessToken = jwtCore.generateAccessToken(userDetail);
            log.info("Access token for user: {}", request.getEmail());
            return ResponseEntity.ok(new AuthResponse(refreshedAccessToken, refreshToken));
        }catch (JwtException e) {
            log.error("Invalid refresh token for user: {}", request.getEmail());
            return ResponseEntity.status(401).body("Jwt error has occurred: " + e.getMessage());
        }catch (Exception e) {
            log.error("Unexpected error: {}, while trying to refresh the access token: {}", e.getMessage(), request.getEmail());
            return ResponseEntity.status(500).body("Something went wrong on our system: " + e.getMessage());
        }
    }
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(400).body("Authorization header is missing or invalid");}
            String accessToken = authHeader.substring(7); // Убираем "Bearer "
            String email = jwtCore.getEmailFromAccessToken(accessToken);
            refreshTokenStorage.remove(email);
            log.info("User {} successfully logged out.", email);
            return ResponseEntity.ok("Logged out successfully");

        } catch (JwtException e) {
            log.error("JWT error during logout: {}", e.getMessage());
            return ResponseEntity.status(401).body("JWT error: " + e.getMessage());

        } catch (Exception e) {
            log.error("Unexpected error during logout: {}", e.getMessage());
            return ResponseEntity.status(500).body("Something went wrong: " + e.getMessage());
        }
    }
    public ResponseEntity<?> register(@Valid AuthRegisterRequest registerRequest) {
        try {
            if (userRepository.findByEmail(registerRequest.getEmail()).isEmpty()) {
                User newUser = new User();
                newUser.setUsername(registerRequest.getUsername());
                newUser.setEmail(registerRequest.getEmail());
                newUser.setPassword(registerRequest.getPassword());

                User savedUser = userService.saveUser(newUser);

                return ResponseEntity.ok("User registered successfully with ID: " + savedUser.getId());
            }
            return ResponseEntity.status(401).body("User with such email already has an acc");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }
}
