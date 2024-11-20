package org.example.medialibrary.controller.temp;

import org.example.medialibrary.configuration.AppPropertiesConfiguration;
import org.example.medialibrary.entity.Pack;
import org.example.medialibrary.entity.dto.FilmDTO;
import org.example.medialibrary.repository.PackRepository;
import org.example.medialibrary.service.FilmService;
import org.example.medialibrary.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/pack")
public class PackController {

    @Autowired
    private PackRepository packRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AppPropertiesConfiguration prop;

    @GetMapping("/create")
    public String showCreatePackPage() {
        return "create-pack";
    }

    @PostMapping("/create")
    public String createPack(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
//            @RequestParam("rating") double rating,
            @RequestParam(value = "accessibleToAll", defaultValue = "false") boolean accessibleToAll,
            @RequestParam(value = "accessibleToFriends", defaultValue = "false") boolean accessibleToFriends,
            @RequestParam("image") MultipartFile imageFile,
            Model model) {

        Pack pack = new Pack(name, description, accessibleToAll, accessibleToFriends);
        pack.setImage(imageService.getImageBytes(imageFile, prop.getNoImage()));
        packRepository.save(pack);

        //model.addAttribute("message", "Pack created successfully!");
        return "create-pack";
    }


    @Autowired
    private FilmService filmService;

    @GetMapping("/test/{id}")
    public String showTestPage(@PathVariable long id, Model model, @RequestParam(defaultValue = "5") int rows) {
        List<FilmDTO> films = filmService.getAllFilmsDto();
        int filmsPerPage = rows * 4;  // Четыре фильма в каждом ряду
        films = films.subList(0, Math.min(filmsPerPage, films.size())); // Ограничиваем количество фильмов
        model.addAttribute("films", films);
        model.addAttribute("rows", rows);

        model.addAttribute("galleryTitle", "Моя первая Колекция фильмов");
        model.addAttribute("averageRating", "6.7");
        model.addAttribute("description", "Существуют две основные трактовки понятия «текст»: имманентная (расширенная, философски нагруженная) и репрезентативная (более частная). Имманентный подход подразумевает отношение к тексту как к автономной реальности, нацеленность на выявление его внутренней структуры. Репрезентативный — рассмотрение текста как особой формы представления информации о внешней тексту действительности.\n" +
                "\n" +
                "В лингвистике термин «текст» используется в широком значении, включая и образцы устной речи. Восприятие текста изучается в рамках лингвистики текста и психолингвистики. Так, например, И. Р. Гальперин определяет текст следующим образом: «Это письменное сообщение, объективированное в виде письменного документа, состоящее из ряда высказываний, объединённых разными типами лексической, грамматической и логической связи, имеющее определённый модальный характер, прагматическую установку и соответственно литературно обработанное»[1].");
        model.addAttribute("backUrl", "/anotherPage");

        model.addAttribute("packId", id);

        return "pack-view";
    }

    @PostMapping("/like")
    @ResponseBody
    public ResponseEntity<Void> likePack(@RequestParam long id) {
        System.out.println("-> лайк для pack ID: " + id + " uuuuuuuuuuuuuuuuuu");
        // Логика для обработки лайка по `id`
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dislike")
    @ResponseBody
    public ResponseEntity<Void> dislikePack(@RequestParam long id) {
        System.out.println("-> дизлайк для pack ID: " + id + " uuuuuuuuuuuuuuuuuu");
        // Логика для обработки дизлайка по `id`
        return ResponseEntity.ok().build();
    }

}
