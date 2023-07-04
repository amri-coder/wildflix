package com.wildflix.wildflix.repository;

import com.wildflix.wildflix.enums.RoleName;
import com.wildflix.wildflix.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role save(Role role);
  Optional<Role> findByName(RoleName name);
}
