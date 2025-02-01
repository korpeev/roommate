package org.broxton.user.repository;

import org.broxton.user.entity.Role;
import org.broxton.user.models.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(UserRoles name);
}
