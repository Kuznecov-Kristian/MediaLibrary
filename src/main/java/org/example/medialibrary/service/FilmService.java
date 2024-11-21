package org.example.medialibrary.service;

import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.example.medialibrary.entity.Film;
import org.example.medialibrary.entity.Genre;
import org.example.medialibrary.entity.dto.FilmDTO;
import org.example.medialibrary.repository.FilmRepository;
import org.example.medialibrary.repository.PackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private ImageService imageService;

    // Получение всех фильмов с добавлением закодированного изображения и MIME-типа

    public List<FilmDTO> getAllFilmsDto() {
        return filmRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Конвертация Film в FilmDTO с кодировкой изображения
    public FilmDTO convertToDTO(Film film)  {
        FilmDTO filmDTO = new FilmDTO();
        filmDTO.setId(film.getId());
        filmDTO.setTitle(film.getTitle());
        filmDTO.setReleaseYear(film.getReleaseYear());

        if (film.getImage() != null && film.getImage().length > 0) {
            byte[] imageBytes = film.getImage();

            String mimeType = imageService.getImageType(imageBytes);

            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            filmDTO.setImageBase64(base64Image);
            filmDTO.setMimeType(mimeType);
        }

        return filmDTO;
    }

    // Получить все фильмы
    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    // Получить фильм по ID
    public Optional<Film> getFilmById(Long id) {
        return filmRepository.findById(id);
    }

    // Создать новый фильм
    public Film createFilm(Film film) {
        return filmRepository.save(film);
    }

    @Transactional
    public boolean isFilmOwnedByUser(Long filmId, Long userId) {
        return packRepository.existsByUserIdAndFilmsId(userId, filmId);
    }

    // Обновить существующий фильм
    @Transactional
    public Film updateFilm(Long id, Film updatedFilm) {
        return filmRepository.findById(id)
                .map(film -> {
                    film.setTitle(updatedFilm.getTitle());
                    film.setDescription(updatedFilm.getDescription());
                    film.setCountry(updatedFilm.getCountry());
                    film.setReleaseYear(updatedFilm.getReleaseYear());
                    film.setImage(updatedFilm.getImage());
                    film.setGenres(updatedFilm.getGenres());
                    return filmRepository.save(film);
                })
                .orElseThrow(() -> new RuntimeException("Film not found with id " + id));
    }

    // Удалить фильм по ID
    public void deleteFilm(Long id) {
        filmRepository.deleteById(id);
    }

    // Добавить жанр к фильму
    @Transactional
    public void addGenreToFilm(Long filmId, Genre genre) {
        filmRepository.findById(filmId).ifPresent(film -> {
            film.getGenres().add(genre);
            filmRepository.save(film);
        });
    }

    // Удалить жанр из фильма
    @Transactional
    public void removeGenreFromFilm(Long filmId, Genre genre) {
        filmRepository.findById(filmId).ifPresent(film -> {
            film.getGenres().remove(genre);
            filmRepository.save(film);
        });
    }
}
