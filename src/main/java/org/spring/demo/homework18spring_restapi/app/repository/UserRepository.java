package org.spring.demo.homework18spring_restapi.app.repository;

import org.spring.demo.homework18spring_restapi.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select distinct u from User u left join fetch u.roles where u.username = ?1")
    Optional<User> findByUsernameFetchRoles(String username);

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
