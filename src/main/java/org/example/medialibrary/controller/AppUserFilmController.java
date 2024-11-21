package org.example.medialibrary.controller;

import org.example.medialibrary.configuration.AppPropertiesConfiguration;
import org.example.medialibrary.entity.AppUser;
import org.example.medialibrary.entity.Film;
import org.example.medialibrary.entity.Genre;
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
import java.util.Base64;
import java.util.List;
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
    private ImageService imageService;

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
    public String showFilm(@PathVariable("id") Long id, @RequestParam(value = "packId", required = false) Long packId, Model model)  {

        appUserAuthService.generatePublicHeaderData(model);
        Optional<AppUser> userOptional = appUserAuthService.getAppUser();
        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();

            if (packId != null) {
                if(!packService.isPackOwnedByUser(packId, user.getId())){
                    return "error/403";
                }
                model.addAttribute("returnUrl", "/user/pack/" + packId);
            } else {
                model.addAttribute("returnUrl", "/user/pack");
            }


            Optional<Film> oFilm = filmService.getFilmById(id);
            if (oFilm.isEmpty()) {
                return "error/404";
            }
            Film film = oFilm.get();

            if(!filmService.isFilmOwnedByUser(film.getId(), user.getId())) {
                return "error/403";
            }

            if (film.getImage() != null && film.getImage().length > 0) {
                byte[] imageBytes = film.getImage();

                String mimeType = imageService.getImageType(imageBytes);


                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                model.addAttribute("image", base64Image);
                model.addAttribute("mimeType", mimeType);
            }



            model.addAttribute("film", film);
            return "user-film-details";
        }

        return "error/403";
    }


    @GetMapping("/edit/{id}")
    public String editFilmPage(@PathVariable Long id, Model model) {

        Optional<AppUser> userOptional = appUserAuthService.getAppUser();
        if (userOptional.isPresent()) {
            AppUser user = userOptional.get();

            Optional<Film> oFilm = filmService.getFilmById(id);
            if (oFilm.isEmpty()) {
                return "error/404";
            }
            Film film = oFilm.get();

            if(!filmService.isFilmOwnedByUser(film.getId(), user.getId())) {
                return "error/403";
            }

            appUserAuthService.generatePublicHeaderData(model);


            model.addAttribute("filmId", film.getId());
            model.addAttribute("oldTitle", film.getTitle());
            model.addAttribute("oldDescription", film.getDescription());
            model.addAttribute("oldCountry", film.getCountry());
            model.addAttribute("oldReleaseYear", film.getReleaseYear());
            model.addAttribute("countries", countryService.findAllCountryNames());
            model.addAttribute("genres", genreService.findAllGenres());
            return "edit-film";
        }

        return "error/something-went-wrong";

    }


    @PostMapping("/edit/{id}")
    public String editFilm(@PathVariable Long id,
                           @RequestParam("newTitle") String title,
                           @RequestParam("newDescription") String description,
                           @RequestParam("newCountry") String country,
                           @RequestParam("newReleaseYear") int releaseYear,
                           @RequestParam("newGenres") List<Genre> genres,
                           @RequestParam(value = "newImage", required = false) MultipartFile imageFile) {

        Optional<Film> optionalFilm = filmService.getFilmById(id);
        if (optionalFilm.isPresent()) {
            Film film = optionalFilm.get();
            film.setTitle(title);
            film.setDescription(description);
            film.setCountry(country);
            film.setReleaseYear(releaseYear);
            film.setGenres(genres);

            if (!imageFile.isEmpty()) {
                try {
                    film.setImage(imageFile.getBytes());
                } catch (IOException e) {
                    film.setImage(new byte[0]);
                }
            }

            filmService.updateFilm(id, film);
        }
        return "redirect:/user/pack";

    }
}
