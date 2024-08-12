package org.spring.demo.homework18spring_restapi.app.service;

import lombok.RequiredArgsConstructor;
import org.spring.demo.homework18spring_restapi.app.model.User;
import org.spring.demo.homework18spring_restapi.app.model.dto.SignupRequest;
import org.spring.demo.homework18spring_restapi.app.repository.RoleRepository;
import org.spring.demo.homework18spring_restapi.app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String createUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return "User already exists: " + signupRequest.getUsername();
        }
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEnabled(Boolean.TRUE);
        roleRepository.findByName("ROLE_USER").ifPresent(user::addRole);
        userRepository.save(user);
        return "User created: " + user.getUsername();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsernameFetchRoles(username).orElseThrow();
    }
}
