package org.spring.demo.homework18spring_restapi.app.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoteResponse {
    private String title;
    private String content;
}
