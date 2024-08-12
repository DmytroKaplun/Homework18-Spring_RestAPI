package org.spring.demo.homework18spring_restapi.app.controller;

import lombok.RequiredArgsConstructor;
import org.spring.demo.homework18spring_restapi.app.model.dto.AuthRequest;
import org.spring.demo.homework18spring_restapi.app.model.dto.SignupRequest;
import org.spring.demo.homework18spring_restapi.app.service.UserService;
import org.spring.demo.homework18spring_restapi.app.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private  final JwtUtil jwtUtil;

    @GetMapping()
    public String hello() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        return "hello " + auth.getName();
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest signupRequest) {
        return userService.createUser(signupRequest);
    }

    @PostMapping("/auth")
    public String createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        return jwtUtil.generateToken(userDetails);
    }
}
