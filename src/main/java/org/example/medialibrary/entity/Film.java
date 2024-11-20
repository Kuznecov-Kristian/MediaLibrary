package org.example.medialibrary.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Entity
@Table(name = "films")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private byte[] image;

    @Lob
    private String description;

    private String country;

    private int releaseYear;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "film_genre",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @ManyToMany(mappedBy = "films", fetch = FetchType.LAZY) // optional bi-directional
    private List<Pack> packs = new ArrayList<>();

    public Film(String title, byte[] image, String description, String country, int releaseYear, List<Genre> genres) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.country = country;
        this.releaseYear = releaseYear;
        this.genres = genres;
    }
}
