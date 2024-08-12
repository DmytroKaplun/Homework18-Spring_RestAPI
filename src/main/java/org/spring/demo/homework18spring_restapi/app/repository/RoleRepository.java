package org.spring.demo.homework18spring_restapi.app.repository;

import org.spring.demo.homework18spring_restapi.app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
