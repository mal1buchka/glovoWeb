package kg.org.glovoweb.Services;

import kg.org.glovoweb.DTOs.userWithRoleDTO;
import kg.org.glovoweb.Models.User;

import java.util.List;

public interface UserService {

    User findById(String id);
    User saveUser(User user);
    void deleteUserById(String id);
    List<User> findAllUsers();
    User findUserByEmail(String email);
    List<userWithRoleDTO> getAllUsersWithRoles();

}
