package org.example.medialibrary.controller;

import org.example.medialibrary.configuration.AppPropertiesConfiguration;
import org.example.medialibrary.entity.AppUser;
import org.example.medialibrary.entity.Film;
import org.example.medialibrary.entity.Pack;
import org.example.medialibrary.entity.dto.CreateFilmDto;
import org.example.medialibrary.entity.dto.mapper.FilmMapper;
import org.example.medialibrary.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/user/film")
public class AppUserFilmController {

    @Autowired
    private AppPropertiesConfiguration propertiesConfiguration;

    @Autowired
    private PackService packService;

    @Autowired
    private AppUserAuthService appUserAuthService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private FilmMapper filmMapper;

    @Autowired
    private FilmService filmService;

    @GetMapping("/create")
    public String create(Model model) {

        appUserAuthService.generatePublicHeaderData(model);

        model.addAttribute("film", new Film());
        model.addAttribute("countries", countryService.findAllCountryNames());
        model.addAttribute("genres", genreService.findAllGenres());

        return "create-film";
    }

    @PostMapping("/create")
    public String saveFilm(@ModelAttribute CreateFilmDto dto, @RequestParam("image") MultipartFile imageFile) {
        Film film = filmMapper.toEntity(dto);

        try {
            film.setImage(imageFile.getBytes());
        } catch (IOException e) {
            film.setImage(new byte[0]);
        }

        Film saveFilm = filmService.createFilm(film);
        Optional<AppUser> userOptional = appUserAuthService.getAppUser();
        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();

            Optional<Pack> packOptional =
                    packService.findPackByNameAndUser(propertiesConfiguration.getAllFilmPackName(), user);

            if (packOptional.isPresent()) {
                Pack pack = packOptional.get();
                packService.addFilmToPack(pack.getId(), saveFilm);
            }
        }

        return "redirect:/user/pack";
    }

    @GetMapping("/{id}")
    public String film(@PathVariable long id) {
        return "film-details";
    }


    @GetMapping("/edit/{id}")
    public String filmEdit(@PathVariable long id) {
        return "edit-film";
    }
}
