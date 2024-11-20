package org.example.medialibrary.entity.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FilmDTO {
    private Long id;
    private String title;
    private String imageBase64;
    private String mimeType;
    private int releaseYear;
}
