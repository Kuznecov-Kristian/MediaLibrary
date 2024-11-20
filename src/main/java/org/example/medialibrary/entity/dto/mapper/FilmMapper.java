package org.example.medialibrary.entity.dto.mapper;

import org.example.medialibrary.entity.Country;
import org.example.medialibrary.entity.Film;
import org.example.medialibrary.entity.dto.CreateFilmDto;
import org.springframework.stereotype.Component;

@Component
public class FilmMapper {
    public Film toEntity(CreateFilmDto dto) {
        if (dto == null) {
            return new Film();
        }

        Film film = new Film();

        film.setTitle(dto.getTitle());
        film.setDescription(dto.getDescription());
        film.setCountry(dto.getCountry());
        film.setReleaseYear(dto.getReleaseYear());
        film.setGenres(dto.getGenres());

        return film;
    }
}
