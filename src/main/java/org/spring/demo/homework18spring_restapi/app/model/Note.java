package org.spring.demo.homework18spring_restapi.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;


@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity()
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "note_seq")
    @SequenceGenerator(name = "note_seq", sequenceName = "seq_note_id", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Content is mandatory")
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note note)) return false;
        return Objects.equals(id, note.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content);
    }
}
