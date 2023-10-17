package com.userservice.repository;

import java.util.Optional;

import com.userservice.models.ERole;
import com.userservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByRoleName(ERole roleName);

//  Optional<Role> findByRoleName(String roleName);
	Role findByRoleName(String roleName);
}
