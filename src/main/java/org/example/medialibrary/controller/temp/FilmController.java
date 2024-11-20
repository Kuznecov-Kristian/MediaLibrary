package org.example.medialibrary.controller.temp;

import jakarta.transaction.Transactional;
import org.example.medialibrary.entity.Film;
import org.example.medialibrary.entity.Genre;
import org.example.medialibrary.entity.dto.CreateFilmDto;
import org.example.medialibrary.entity.dto.FilmDTO;
import org.example.medialibrary.entity.dto.mapper.FilmMapper;
import org.example.medialibrary.repository.FilmRepository;
import org.example.medialibrary.repository.GenreRepository;
import org.example.medialibrary.service.FilmService;
import org.example.medialibrary.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/films")
public class FilmController {

//    @GetMapping
//    public String showFilms() {
//        return "not-found";
//    }

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private FilmMapper filmMapper;

    @Autowired
    private FilmService filmService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/create")
    public String showCreateFilmForm(Model model) {
        model.addAttribute("film", new Film());
        model.addAttribute("countries", getCountryList());
        model.addAttribute("genres", getAllGenres());
        return "create-film";
    }

    @PostMapping("/save")
    public String saveFilm(@ModelAttribute CreateFilmDto dto, @RequestParam("image") MultipartFile imageFile) {
        Film film = filmMapper.toEntity(dto);

        try {
            film.setImage(imageFile.getBytes());
        } catch (IOException e) {
            film.setImage(new byte[0]);
        }

        filmRepository.save(film);

        return "redirect:/films/create";
    }

    private List<String> getCountryList() {
        return Arrays.asList("USA", "France", "Germany", "UK", "Japan", "South Korea");
    }

    private List<Genre> getAllGenres() {
        // Получите список жанров из базы данных
        return genreRepository.findAll();
    }


    @Transactional
    @GetMapping("/{id}")
    public String getFilmDetails(@PathVariable Long id,
                                 @RequestParam(required = false, defaultValue = "/films") String returnUrl,
                                 Model model) {
        System.out.println("-> 1");

        Optional<Film> oFilm = filmRepository.findFilmById(id);

        System.out.println("-> 2");

        if (oFilm.isPresent()) {
            Film film = oFilm.get();

            // Определение MIME-типа изображения и его кодировка в Base64
            if (film.getImage() != null && film.getImage().length > 0) {
                byte[] imageBytes = film.getImage();

                String mimeType = imageService.getImageType(imageBytes);

                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                model.addAttribute("image", base64Image);
                model.addAttribute("mimeType", mimeType);
            }

            model.addAttribute("film", film);

            System.out.println(returnUrl);

            model.addAttribute("returnUrl", returnUrl); // Добавляем returnUrl в модель

            return "film-details"; // имя вашего Thymeleaf-шаблона
        }
        return "redirect:/films/films";
    }


    @GetMapping("/films/{id}")
    public String showFilm(@PathVariable("id") Long id, Model model) throws IOException {

        Optional<Film> oFilm = filmRepository.findById(id);

        if (oFilm.isEmpty()) {
            return "not-found";
        }

        Film film = oFilm.get();

        // Определение MIME-типа изображения и его кодировка в Base64
        if (film.getImage() != null && film.getImage().length > 0) {
            byte[] imageBytes = film.getImage();

            String mimeType = imageService.getImageType(imageBytes);


            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            model.addAttribute("image", base64Image);
            model.addAttribute("mimeType", mimeType);
        }

        model.addAttribute("film", film);
        return "film-details";
    }

    @GetMapping("/films")
    public String showFilms(Model model, @RequestParam(defaultValue = "5") int rows) {
        List<FilmDTO> films = filmService.getAllFilmsDto();
        int filmsPerPage = rows * 4;  // Четыре фильма в каждом ряду
        films = films.subList(0, Math.min(filmsPerPage, films.size())); // Ограничиваем количество фильмов
        model.addAttribute("films", films);
        model.addAttribute("rows", rows);
        return "film-list";
    }


}
