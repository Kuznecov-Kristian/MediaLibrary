package org.example.medialibrary.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "genres", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Film> films;

    public Genre(String name) {
        this.name = name;
    }
}
