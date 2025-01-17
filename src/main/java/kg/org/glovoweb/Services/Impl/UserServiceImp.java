package kg.org.glovoweb.Services.Impl;

import kg.org.glovoweb.Models.Role;
import kg.org.glovoweb.Models.User;
import kg.org.glovoweb.Repositories.RoleRepository;
import kg.org.glovoweb.Repositories.UserRepository;
import kg.org.glovoweb.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class UserServiceImp implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImp(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String typicalRoleName = "ROLE_USER";
        Role userRole = roleRepository.findByRoleName(typicalRoleName)
                .orElseThrow(() -> new RuntimeException("Role USER hasn't found"));
        user.setRoles(List.of(userRole));


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication !=null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            if (user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN"))) {
                String adminRoleName = "ROLE_ADMIN";
                Role adminRole = roleRepository.findByRoleName(adminRoleName)
                        .orElseThrow(() -> new RuntimeException("Role ADMIN hasn't found"));
                user.setRoles(List.of(adminRole));
            }
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
