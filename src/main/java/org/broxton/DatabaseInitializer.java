package org.broxton;

import lombok.RequiredArgsConstructor;
import org.broxton.user.entity.Role;
import org.broxton.user.models.UserRoles;
import org.broxton.user.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

  private final RoleRepository roleRepository;


  @Override
  public void run(String... args) throws Exception {
    UserRoles userRole =  UserRoles.valueOf("USER");
    UserRoles adminRole =  UserRoles.valueOf("ADMIN");
    initializeRole(userRole);
    initializeRole(adminRole);
  }

  private void initializeRole(UserRoles roleName) {

    Optional<Role> role = roleRepository.findByName(roleName);

    if (role.isEmpty()) {
      Role newRole = new Role();
      newRole.setName(roleName);
      roleRepository.save(newRole);
      System.out.println("Created role: " + roleName);
    } else {
      System.out.println("Role already exists: " + roleName);
    }
  }
}
