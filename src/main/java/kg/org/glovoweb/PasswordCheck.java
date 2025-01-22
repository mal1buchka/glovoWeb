package kg.org.glovoweb;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordCheck {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword = "adminGlovoWeb";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Хеш пароля: " + encodedPassword);
    }
}
