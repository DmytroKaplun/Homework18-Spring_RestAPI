package org.spring.demo.homework18spring_restapi.app.model.dto;

import lombok.Data;

@Data
public class NoteRequest {
    private String title;
    private String content;
}
