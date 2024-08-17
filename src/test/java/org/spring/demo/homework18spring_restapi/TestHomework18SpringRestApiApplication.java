package org.spring.demo.homework18spring_restapi;

import org.springframework.boot.SpringApplication;

public class TestHomework18SpringRestApiApplication {

    public static void main(String[] args) {
        SpringApplication
                .from(Homework18SpringRestApiApplication::main)
//                .with(TestcontainersConfiguration.class)
                .run(args);
    }

}
