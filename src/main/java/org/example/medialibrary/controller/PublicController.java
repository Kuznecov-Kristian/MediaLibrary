package org.example.medialibrary.controller;

import org.example.medialibrary.configuration.AppPropertiesConfiguration;
import org.example.medialibrary.entity.Film;
import org.example.medialibrary.entity.Genre;
import org.example.medialibrary.entity.Pack;
import org.example.medialibrary.entity.dto.PackDto;
import org.example.medialibrary.repository.FilmRepository;
import org.example.medialibrary.repository.GenreRepository;
import org.example.medialibrary.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class PublicController {

    @Autowired
    private AppPropertiesConfiguration appPropertiesConfiguration;

    @Autowired
    private PackService packService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AppUserAuthService userAuthService;

    @GetMapping("/")
    public String showPacks(Model model, @RequestParam(defaultValue = "5") int rows) {

        userAuthService.generatePublicHeaderData(model);

        List<PackDto> packDtoList = packService.getLimitedAccessiblePacksDto(rows * 4);
        model.addAttribute("isHome", false);
        model.addAttribute("isPackCreate", false);
        model.addAttribute("packTitle", appPropertiesConfiguration.getHomePackFilmListTitle());
        model.addAttribute("packs", packDtoList);
        model.addAttribute("rows", rows);
        return "pack-list";
    }

    @GetMapping("/pack/{id}")
    public String getPublicPackById(@PathVariable Long id, Model model) {

        userAuthService.generatePublicHeaderData(model);

        Optional<Pack> pack = packService.getPackById(id);
        if (pack.isPresent()) {

            Pack packObj = pack.get();

            if (!packObj.isAccessibleToAll()) {
                return "error/403";
            }

            model.addAttribute("galleryTitle", packObj.getName());
            model.addAttribute("description", packObj.getDescription());
            model.addAttribute("packId", packObj.getId());
            model.addAttribute("averageRating", ratingService.getFilmPackRating(packObj.getLike(), packObj.getDislike()));
            model.addAttribute("backUrl", "/");
            model.addAttribute("films", packService.getFilmsFromPack(packObj));

            return "pack-view";
        }

        return "error/404";
    }

    @GetMapping("/film/{id}")
    public String showFilm(@PathVariable("id") Long id, @RequestParam(value = "packId", required = false) Long packId, Model model) {

        userAuthService.generatePublicHeaderData(model);

        Optional<Film> oFilm = filmService.getFilmById(id);
        if (oFilm.isEmpty()) {
            return "error/404";
        }

        Film film = oFilm.get();

        if (film.getImage() != null && film.getImage().length > 0) {
            byte[] imageBytes = film.getImage();

            String mimeType = imageService.getImageType(imageBytes);


            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            model.addAttribute("image", base64Image);
            model.addAttribute("mimeType", mimeType);
        }

        if (packId != null) {
            model.addAttribute("returnUrl", "/pack/" + packId);
        } else {
            model.addAttribute("returnUrl", "/");
        }

        model.addAttribute("film", film);
        return "film-details";
    }

    @GetMapping("/zag")
    public String showZag(Model model) {
        return "temp/zaglychka";
    }


    @GetMapping("/like/{id}")
    public String like(@PathVariable("id") Long id) {
        packService.incrementLikes(id);
        return "redirect:/pack/" + id;
    }

    @GetMapping("/dislike/{id}")
    public String dislike(@PathVariable("id") Long id) {
        packService.incrementDislikes(id);
        return "redirect:/pack/" + id;
    }






}
