package kg.org.glovoweb.DTOs;

import kg.org.glovoweb.Models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class userWithRoleDTO {
    private String id;
    private String username;
    private String email;
    private List<Role> roles;
}
