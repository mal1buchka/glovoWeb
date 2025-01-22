//package kg.org.glovoweb;
//
//import kg.org.glovoweb.Models.Role;
//import kg.org.glovoweb.Repositories.RoleRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataInitTest implements CommandLineRunner {
//    private final RoleRepository roleRepository;
//
//    public DataInitTest(RoleRepository roleRepository) {
//        this.roleRepository = roleRepository;
//    }
//    @Override
//    public void run(String... args) {
//
//        if (roleRepository.findByRoleName("ROLE_USER").isEmpty()) {
//            Role userRole = new Role();
//            userRole.setRoleName("ROLE_USER");
//            roleRepository.save(userRole);
//        }
//
//        if (roleRepository.findByRoleName("ROLE_ADMIN").isEmpty()) {
//            Role adminRole = new Role();
//            adminRole.setRoleName("ROLE_ADMIN");
//            roleRepository.save(adminRole);
//        }
//    }
//}
