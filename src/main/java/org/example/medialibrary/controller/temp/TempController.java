package org.example.medialibrary.controller.temp;

import org.example.medialibrary.entity.Film;
import org.example.medialibrary.entity.Pack;
import org.example.medialibrary.service.FilmService;
import org.example.medialibrary.service.PackService;
import org.example.medialibrary.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class TempController {

    @Autowired
    private PackService packService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private RatingService ratingService;

    @GetMapping("/load")
    public String load() {
        Pack pack = packService.createPack(new Pack("Первая колекция", "Что-то там тра ля ля....", true, true));


        Optional<Film> film1 = filmService.getFilmById(1L);
        Optional<Film> film2 = filmService.getFilmById(2L);
        Optional<Film> film3 = filmService.getFilmById(3L);
        Optional<Film> film4 = filmService.getFilmById(4L);
        Optional<Film> film5 = filmService.getFilmById(5L);
        Optional<Film> film6 = filmService.getFilmById(6L);

        packService.addFilmToPack(pack.getId(), film1.get());
        packService.addFilmToPack(pack.getId(), film2.get());
        packService.addFilmToPack(pack.getId(), film3.get());
        packService.addFilmToPack(pack.getId(), film4.get());
        packService.addFilmToPack(pack.getId(), film5.get());
        packService.addFilmToPack(pack.getId(), film6.get());


        return "index";
    }



}
