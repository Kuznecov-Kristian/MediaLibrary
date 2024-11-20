package org.example.medialibrary.entity.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PackDto {
    private Long id;
    private String title;
    private double rating;
    private String mimeType;
    private String imageBase64;
}
