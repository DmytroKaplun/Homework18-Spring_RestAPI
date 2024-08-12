package org.spring.demo.homework18spring_restapi.app.model.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
}
