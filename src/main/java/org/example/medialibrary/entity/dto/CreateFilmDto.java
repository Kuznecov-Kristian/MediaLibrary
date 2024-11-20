package org.example.medialibrary.entity.dto;

import jakarta.persistence.*;
import lombok.*;
import org.example.medialibrary.entity.Genre;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreateFilmDto {

    private String title;
    private String description;
    private String country;
    private int releaseYear;
    private List<Genre> genres;

}
